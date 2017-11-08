 package com.damari.csrl.app;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

	private static final Logger log = LoggerFactory.getLogger(App.class.getName());

	public App() {
		log.info("Found {} processors", Runtime.getRuntime().availableProcessors());

		try {
			reverseLookupMD5Checksum("ecd0fe8dbd00fe983d85857261285327");
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException while reverse lookup of MD5", e);
			e.printStackTrace();
		}
	}

	/**
	 * Reverse lookup MD5 checksum.
	 * @param String with 32 char MD5 checksum.
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	private void reverseLookupMD5Checksum(String findChecksum) throws UnsupportedEncodingException {
		//final String pattern = "abcdefghijklmnopqrstuvwxyzåäö0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ!_-\"|\\@";
		//final String pattern = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		//final String pattern = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String pattern = "aeimnstR";
		ReverseLookupMD5 rlMD5 = new ReverseLookupMD5();
		List<String> matches = rlMD5.find(pattern, findChecksum, 1, findChecksum.length());
		for (String s : matches) {
			log.info("Matches: {}", s);
		}
	}

}
