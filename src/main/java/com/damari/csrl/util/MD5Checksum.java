package com.damari.csrl.util;

import com.twmacinta.util.MD5;

/**
 * Basically Fast MD5 implementation with some tweak.
 */
public class MD5Checksum {

	private MD5 md5;

	private char byteToHexBuffer[];

	private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		 'a', 'b', 'c', 'd', 'e', 'f'};

	public MD5Checksum() {
		byteToHexBuffer = new char[16 * 2];
		md5 = new MD5();
	}

	public void init() {
		md5.Init();
	}

	public void update(byte[] buffer) {
		md5.Update(buffer);
	}
	public void update(byte[] buffer, int length) {
		md5.Update(buffer, length);
	}

	/**
	 * Modified fastmd5 asHex() without new byte as it's memory intensive without cost.
	 * Should boost performance when threading.
	 * @return String with MD5 checksum.
	 */
	public String asHex() {
		byte hash[] = md5.Final();
		for (int i = 0, x = 0; i < 16; i++) {
			byteToHexBuffer[x++] = HEX_CHARS[(hash[i] >>> 4) & 0xf];
			byteToHexBuffer[x++] = HEX_CHARS[hash[i] & 0xf];
		}
		return new String(byteToHexBuffer);
	}

}
