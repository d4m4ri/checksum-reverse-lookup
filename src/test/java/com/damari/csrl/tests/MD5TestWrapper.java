package com.damari.csrl.tests;

import java.nio.charset.Charset;

import com.damari.csrl.util.MD5Checksum;

public class MD5TestWrapper extends MD5Checksum {

	private final Charset iso88591charset = Charset.forName("ISO-8859-1");

	public String asHex(String string) {
		this.init();
		this.update(string.getBytes(iso88591charset));
		return this.asHex();
	}

}
