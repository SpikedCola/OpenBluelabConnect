#!/usr/bin/env python3

# i'm not typically a python programmer, please don't look too closely!

from digi.xbee.devices import XBeeDevice, RemoteXBeeDevice
from digi.xbee.packets.common import RemoteATCommandPacket
from digi.xbee.models.options import DiscoveryOptions
from digi.xbee.util import utils
import struct
import sys
import time
import libscrc
import random
import logging
import binascii
import pprint

# connect stick setup. this could be automatically detected like the connect software does.
PORT = '/dev/ttyUSB0'
BAUD = 115200

# processing is different depending on device.
#DEVICE_TYPE = 'PRO_CONTROLLER'
DEVICE_TYPE = 'GUARDIAN'
GUARDIAN_ID = bytes([9, 9])

# these are for the pro controller.
BLUELAB_XOR_KEY = [0x6e, 0x67, 0xc5, 0x79, 0xdd, 0x61, 0x02, 0x1a]
COMMAND_WHO_ARE_YOU = 0
COMMAND_DATA_1 = 1 # GET_DEVICE_INFO
COMMAND_DATA_2 = 2 # GET_DEVICE_SETTINGS
COMMAND_DATA_3 = 3 # GET_DEVICE_CONTROL_ACTIVITY
COMMAND_GET_DEVICE_POD_SETUP = 4

def base36encode(number, alphabet='0123456789abcdefghijklmnopqrstuvwxyz'):
    """
	Converts an integer to a base36 string. 
	https://gist.github.com/marcg1968/5886274913bac202a192
	Note, adjusted alphabet to be lowercase
	"""
    if not isinstance(number, int):
        raise TypeError('number must be an integer')

    base36 = ''
    sign = ''

    if number < 0:
        sign = '-'
        number = -number

    if 0 <= number < len(alphabet):
        return sign + alphabet[number]

    while number != 0:
        number, i = divmod(number, len(alphabet))
        base36 = alphabet[i] + base36

    return sign + base36
	
def deobfuscate_packet_data(packet):
	# first byte of packet is an xor key
	secretByte = packet[0]
	obfuscatedData = packet[1:]

	# xor bluelab key with byte from packet
	local_key = []
	for i in BLUELAB_XOR_KEY: 
		local_key.append(i ^ secretByte)
	local_key_len = len(local_key)

	# now use local_key to decode rest of packet
	decoded_data = []
	count = 0
	for i in obfuscatedData:
		decoded_data.append(i ^ local_key[count % local_key_len])
		count += 1

	return decoded_data

def decode_packet(packet):
	packetLen = len(packet)
	header = packet[0] 
	dataLen = int.from_bytes(packet[1:1+2], byteorder='big')
	command = packet[3]
	data = packet[4:4+dataLen-1]
	#print('packet length: {}'.format(packetLen))
	#print('command: {0:x}'.format(command))
	#print('data length: {}'.format(dataLen))
	#print('inner data: {}'.format(binascii.hexlify(data)))

	deobf = deobfuscate_packet_data(data)

	return {
		'command': command,
		'data': deobf,
		'data_bytearray': bytearray(deobf)
	}

def build_packet(command, randomByte):
	header = bytearray([
		0x1B # sync packet
	])

	data = bytearray([
		0x00, 
		0x02, # byteArrayLenPlusOne # shit, had 1 here for a long time, should be 2 (command + randomByte) # should actually be len(cmd+random+data) but we dont support data :)
		command, # command (who are you)
		randomByte, # randombyte
	])
	
	# bluelab uses xmodem-style crc16 (0x1021 poly)
	# header doesn't get crc'd
	crc = libscrc.xmodem(bytes(data))
	crcBytes = crc.to_bytes(2, byteorder='big')
	
	# build packet
	return header + data + crcBytes	

def keyCodeFrom64BitAddress(address):
	# extract last 3 bytes of 64bit address
	last3 = address.address[-3:]
	
	# convert to integer, big endian
	combined = int.from_bytes(last3, 'big')
	
	# split combined int into 5 nibbles
	nibbles = [0, 0, 0, 0, 0]
	nibbles[0] = (combined &     0xF) >> 0
	nibbles[1] = (combined &    0xF0) >> 4
	nibbles[2] = (combined &   0xF00) >> 8
	nibbles[3] = (combined &  0xF000) >> 12
	nibbles[4] = (combined & 0xF0000) >> 16
	
	# rearrange nibbles 0,1,2,3,4 -> 2,0,4,1,3
	rearranged = (nibbles[2] << 16) + (nibbles[0] << 12) + (nibbles[4] << 8) + (nibbles[1] << 4) + (nibbles[3] << 0)
	
	# finally base36 the rearranged value
	base36 = base36encode(rearranged).replace(' ', '0')
	
	return base36

# discovery callback just prints
def discovery_callback(remote_device):
	print('> Discovered a device! {}'.format(remote_device))

# we can receive packets and data. packets are lower level, was hoping to find DD packet here for identification of guardian
# but it didnt turn out to be necessary.
def packet_receive_callback(packet):
	packet_type = type(packet).__name__	
	# currently only care about AT response packets.
	if 'ATCommResponsePacket' != packet_type:
		return
		
	command_value = 'no data'
	if packet.command_value:
		command_value = binascii.hexlify(packet.command_value)
	print(
		'<- Received {} packet [{}] [{}] ({}): {}'.format(
			packet_type, 
			packet.command,
			packet.status,
			command_value,
			binascii.hexlify(packet.output())
		)
	)
    
# this is the main data received callback
def data_receive_callback(xbee_message):
	print('<- Received data from "{}": {}'.format( 
		xbee_message.remote_device, 
		binascii.hexlify(xbee_message.data) 
	))
	# packets are different depending on device.
	# if we supported multiple devices, we could pass the address to parser so we know which device the message was for.
	# address = xbee_message.remote_device.get_64bit_addr()
	if 'PRO_CONTROLLER' == DEVICE_TYPE:
		process_pro_controller_packet(xbee_message.data)
	else:
		process_guardian_packet(xbee_message.data)

# packet processing/device specific stuff
def process_pro_controller_packet(packet):
	decoded = decode_packet(packet)
	process_decoded(decoded)

def read_consume_string_from_buf(buf, maxlen):
	# read until we find a null byte (or reach maxlen)
	ret = ''
	
	count = 0
	for i in buf:
		count += 1 # count will include the null byte
		if count == maxlen:
			break
		if not i:
			break
		ret += chr(i)
	
	# remove up to & including the null byte from the buffer, so its ready for next pass.
	# cant do this in previous loop? idk. sure theres a better way to do all this
	for i in range(0, count):
		del buf[0]
	
	return ret

CONTROL_TYPES = ['CONDUCTIVITY', 'TEMPERATURE', 'PH'] # TIME should be in here too, no?
PERIPOD_TYPES = ['Test peripod', 'Peripod M3', 'Peripod L3', 'Peripod L4', 'Peripod M4']
MODES = ['MONITOR', 'CONTROL', 'CALIBRATION', 'SETTINGS']
DIRECTIONS = ['OFF', 'UP', 'DOWN']
PUMP_TYPES = ['OFF', 'PH', 'EC']

def parse_ctl_type_controller(buf):
	# uchar, ushort, ushort x2
	u = struct.unpack('<BHHBHH', buf)
	return {
		'type': CONTROL_TYPES[u[0]],
		'delta1': u[1],
		'delta2': u[2],
		'direction': DIRECTIONS[u[3]],
		'status': u[4],
		'off_time': u[5]
	}


def parse_type_controller2(buf):
	# uchar, ushort, ushort
	u = struct.unpack('<BHH', buf)
	return {
		'type': CONTROL_TYPES[u[0]],
		'max_val': u[1],
		'min_val': u[2]
	}

def parse_pod_setup(buf):
	# uchar x4 no sense unpacking
	# buf[0] appears unused
	return {
		'type': PERIPOD_TYPES[buf[1]],
		'status': buf[2],
		'num_pumps': buf[3]
	}

def parse_type_controller(buf):
	# uchar, ushort
	u = struct.unpack('<BH', buf)
	return {
		'type': CONTROL_TYPES[u[0]],
		'status': u[1]
	}

def parse_ctl_type(buf):
	# ph, ec, temp and their respective values
	# uchar, ushort x3
	u = struct.unpack('<BHBHBH', buf)
	return {
		'type': CONTROL_TYPES[u[0]],
		'value': u[1] * (10 ** -u[2]),
		'status': u[5]
	}

def process_decoded(decoded):
	buf = decoded['data_bytearray']
	cmd = decoded['command']

	if COMMAND_WHO_ARE_YOU == cmd:
		# try consuming bytes like in java
		# unless im missing something, i dont see a nice way in python
		ident1 = read_consume_string_from_buf(buf, 17) # max len 17
		ident2 = read_consume_string_from_buf(buf, 17) # max len 17
		print('WHO_ARE_YOU:')
		print(' ident1: {}'.format(ident1))
		print(' ident2: {}'.format(ident2))
	elif COMMAND_DATA_1 == cmd:
		print('GET_DEVICE_INFO:')
		
		# try offset instead of consuming bytes this time
		device_settings_change = buf[0] # Enum_ChangeType.DEVICE_SETTING
		pod_status_change = buf[1] # Enum_ChangeType.POD_STATUS
		
		# parse what ive called 'control type managers' - ph, ec, temp
		# and their respective values
		ctl_type_manager_count = buf[2]
		offset = 3
		ctl_type_manager_size = 9
		for i in range(0, ctl_type_manager_count):
			# grab chunk of data that isnt variable length any more
			chunk = buf[offset:offset+ctl_type_manager_size]
			parsed = parse_ctl_type(chunk)
			offset += ctl_type_manager_size
			print(' control type: {}'.format(parsed))
			
		# also parse type controllers 
		# i think these handle turning relays on/off depending on setpoints, not sure
		type_controller_count = buf[offset]
		offset += 1
		type_controller_size = 3
		for i in range(0, type_controller_count):
			# grab chunk of data that isnt variable length any more
			chunk = buf[offset:offset+type_controller_size]
			parsed = parse_type_controller(chunk)
			offset += type_controller_size 
			print(' type controller: {}'.format(parsed))
	elif COMMAND_DATA_2 == cmd:
		print('GET_DEVICE_SETTINGS:')
		# mostly pod related stuff.. 
		pod_mode = MODES[buf[0]]
		pod_status = buf[1]
		
		# looks like controltype is similar to before (data_1 has its real values, this has min/max/direction/off time)
		type_controller2_count = buf[2]
		offset = 3
		type_controller2_size = 5
		for i in range(0, type_controller2_count):
			# grab chunk of data that isnt variable length any more
			chunk = buf[offset:offset+type_controller2_size]
			parsed = parse_type_controller2(chunk)
			offset += type_controller2_size 
			print(' type controller2: {}'.format(parsed))
			
		# looks like controltype is similar to before (data_1 has its real values, this has min/max/direction/off time)
		ctl_type_controller_count = buf[offset]
		offset += 1
		ctl_type_controller_size = 10
		for i in range(0, ctl_type_controller_count):
			# grab chunk of data that isnt variable length any more
			chunk = buf[offset:offset+ctl_type_controller_size]
			parsed = parse_ctl_type_controller(chunk)
			offset += ctl_type_controller_size 
			print(' ctl type controller: {}'.format(parsed))
	elif COMMAND_DATA_3 == cmd:
		print('GET_DEVICE_CONTROL_ACTIVITY:')
		# not too sure about this one, original was the least-reversed.
		# not sure if any dosing/control has been tested
		message_timestamp = int.from_bytes(buf[0:3], byteorder='little') 
		dosing_count = buf[4]
		# normally would loop dosing count like others but dont have any data to test
		print(' timestamp: {}, dosing count: {}'.format(message_timestamp, dosing_count))
	elif COMMAND_GET_DEVICE_POD_SETUP == cmd:
		print('GET_DEVICE_POD_SETUP:')
		# peripods, each has a type, status int, and # of pumps
		peripod_count = buf[0]
		peripod_size = 4
		offset = 1
		for i in range(0, peripod_count):
			# grab chunk of data that isnt variable length any more
			chunk = buf[offset:offset+peripod_size]
			parsed = parse_pod_setup(chunk)
			offset += peripod_size 
			print(' peripod: {}'.format(parsed))
			
		normalized_pod_calibration_value = buf[offset]

	else:
		print('no handler for command {}'.format(decoded['command']))

def guardian_alarm_status_to_string(status):
	# this is directly transcribed.
	status = status.strip().lower()
	if status.endswith('h'):
		return 'high'
	elif status.endswith('l'):
		return 'low'
	elif status.endswith('-'):
		return 'ok'
	else:
		return 'off'

def guardian_get_value_range(value, min_value, max_value, delta_value):
	# delta value specified in ControlType class but is not used for this part.
	# seems like there is some magic value of "52".
	# this is directly transcribed, except that we return None in case we are under/over range.
	try:
		value = float(value)
		if value < -100:
			range = 'error'
		elif value >= min_value and value != 52:
			if value > max_value:
				range = 'over_range'
			else:
				range = 'in_range'
		else:
			range = 'under_range'
	except ValueError:
		# not a float
		value = value.lower()
		if 'ur' == value:
			range = 'under_range'			
		elif 'or' == value:
			range = 'over_range'
		else:
			range = 'error'
		# we dont know what the value actually is. bluelab returns min/max in this case but null makes more sense imo.
		value = None
		
	return {
		'value': value,
		'range': range
	}
def process_guardian_packet(packet):
	# look for guardian ID bytes in packet
	pos = packet.find(GUARDIAN_ID)
	if pos < 0:
		# packet didnt contain id
		return
	offset = pos + len(GUARDIAN_ID)
	# consume ID
	parts = packet[offset:].decode().split("\t")
	# parse. note indexes 2, 4, 5 are not used.
	
	# min/max values are hardcoded into bluelab classes depending on parameter.
	conductivity = guardian_get_value_range(parts[0], 0, 5, 0.1)
	temperature = guardian_get_value_range(parts[1], 0, 50, 1.0)
	ph = guardian_get_value_range(parts[3], 0, 14, 0.1)
	
	data = {
		'conductivity': {
			'value': conductivity['value'],
			'range': conductivity['range'],
			'alarm': guardian_alarm_status_to_string(parts[7])
		},
		'temperature': {
			'value': temperature['value'],
			'range': temperature['range'],
			'alarm': guardian_alarm_status_to_string(parts[8])
		},
		'ph': {
			'value': ph['value'],
			'range': ph['range'],
			'alarm': guardian_alarm_status_to_string(parts[9]),
			'cal_due': ('due' == parts[6].lower())
		}
	}
	
	print('Received Guardian Data:')
	print('\tConductivity:')
	print('\t\tValue: {}'.format(data['conductivity']['value']))
	print('\t\tRange: {}'.format(data['conductivity']['range']))
	print('\t\tAlarm: {}'.format(data['conductivity']['alarm']))
	print('\tTemperature:')
	print('\t\tValue: {}'.format(data['temperature']['value']))
	print('\t\tRange: {}'.format(data['temperature']['range']))
	print('\t\tAlarm: {}'.format(data['temperature']['alarm']))
	print('\tpH:')
	print('\t\tValue: {}'.format(data['ph']['value']))
	print('\t\tRange: {}'.format(data['ph']['range']))
	print('\t\tAlarm: {}'.format(data['ph']['alarm']))
	print('\t\tCal Due: {}'.format(data['ph']['cal_due']))
	print()

local_device = None
try:
	# so we dont actually have to do discovery, unless we want keycode (or to find out which devices are around).
	# pro controller stuff is done by broadcast, and guardian broadcasts on its own without us doing anything.
	
	discovery_time = 10

	print('Starting on {} @ {}'.format(PORT, BAUD))

	# debug logging
	# utils.enable_logger("digi.xbee")

	# "local device" = connect stick
	local_device = XBeeDevice(PORT, BAUD)
	local_device.open()

	# set up the network
	net = local_device.get_network()
	net.set_discovery_timeout(discovery_time)
	# append_dd didnt result in us receiving a DD packet...
	#net.set_discovery_options({DiscoveryOptions.APPEND_DD}) 

	# attach callbacks
	#net.add_device_discovered_callback(discovery_callback)
	#local_device.add_packet_received_callback(packet_receive_callback)
	local_device.add_data_received_callback(data_receive_callback)

	# start discovery
	net.start_discovery_process()
	print('Started discovery - waiting {}s'.format(discovery_time))

	while net.is_discovery_running():
		time.sleep(0.1)
			
	print('Finished discovery')
	print()
	
	discovered_devices = net.get_devices()
	
	if len(discovered_devices):
		for remote_device in discovered_devices:
			print('Discovered "{}"'.format(remote_device))
			# dd and remoteDev appear to be the same thing
			#dd.read_device_info()
			addr64 = remote_device.get_64bit_addr()
			print(' addr64: "{}"'.format(addr64))
			print(' keycode: "{}"'.format(keyCodeFrom64BitAddress(addr64)))
			print('Sending DD')
			# needed to set a timeout, whatever the default is is too short.
			# could do this async too with packetreceived callback.
			remote_device.set_sync_ops_timeout(3)
			dd = remote_device.get_parameter('DD')
			print('Device identified as {}, not sure if this is meaningful'.format(binascii.hexlify(dd)))
			print()
			# not sure if this dd value is useful or not. guardian is returning 00030000.
			# it looks like we can look at the received data packet to determine ID.
			# maybe wait until the first packet arrives and then determine device?
			
		# guardian doesnt seem to respond to these commands. instead it just broadcasts 
		# its ID and data every few seconds.
		if 'PRO_CONTROLLER' == DEVICE_TYPE:
			who_are_you_packet = build_packet(COMMAND_WHO_ARE_YOU, random.randint(0, 255))
			d1_packet = build_packet(COMMAND_DATA_1, random.randint(0, 255))
			d2_packet = build_packet(COMMAND_DATA_2, random.randint(0, 255))
			d3_packet = build_packet(COMMAND_DATA_3, random.randint(0, 255))
			packet50 = build_packet(COMMAND_GET_DEVICE_POD_SETUP, 30)
			
			print('-> broadcast WHO_ARE_YOU')
			print('-> Send data: {}'.format(binascii.hexlify(who_are_you_packet)))
			local_device.send_data_broadcast(who_are_you_packet)
			print()
			print('-> broadcast GET_DEVICE_INFO')
			local_device.send_data_broadcast(d1_packet)
			print()
			print('-> broadcast GET_DEVICE_SETTINGS')
			local_device.send_data_broadcast(d2_packet)
			print()
			print('-> broadcast GET_DEVICE_CONTROL_ACTIVITY')
			local_device.send_data_broadcast(d3_packet)
			print()
			print('-> broadcast GET_DEVICE_POD_SETUP')
			local_device.send_data_broadcast(packet50)
			print()
		
		print('Listening for packets, press CTRL+C to stop')
		print()
		try:
			while True:
				time.sleep(0.1)
		except KeyboardInterrupt:
			print()
			print('Interrupted')
			print()
			
	print('Done!')
finally:
	if local_device is not None and local_device.is_open():
		print('Closing connect stick...')
		local_device.close()