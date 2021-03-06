package com.wlyy.bcwlw.netty.utils;

public class CRC16Util {
	public static int crc16(final byte[] buffer) {
		int crc = 0xFFFF;
		for (int j = 0; j < buffer.length; j++) {
			crc = ((crc >>> 8) | (crc << 8)) & 0xffff;
			crc ^= (buffer[j] & 0xff);// byte to int, trunc sign
			crc ^= ((crc & 0xff) >> 4);
			crc ^= (crc << 12) & 0xffff;
			crc ^= ((crc & 0xFF) << 5) & 0xffff;
		}
		crc &= 0xffff;
		return crc;
	}

	
	public static byte[] crc16Bytes(byte[] bytes){
		return ByteUtil.shortToBytes((short)crc16(bytes));
	}

}
