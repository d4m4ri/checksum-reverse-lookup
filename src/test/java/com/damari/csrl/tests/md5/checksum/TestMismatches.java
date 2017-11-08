package com.damari.csrl.tests.md5.checksum;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.damari.csrl.app.ReverseLookupMD5;

public class TestMismatches {

	@Test
	public void givenSimpleChecksumWithNonCoveredSearchPatternThenExpectStringNotToBeFound() throws InterruptedException {
		String searchPattern = "bcdefghijklmnopqrstuvwxyzåäö0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ!_-\"|\\@"; // "a" excluded
		String md5checksum = "0cc175b9c0f1b6a831c399e269772661"; // MD5 of "a"
		int requiredMatches = 1;
		int combinations = searchPattern.length();

		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					ReverseLookupMD5 rlMD5 = new ReverseLookupMD5();
					rlMD5.find(searchPattern, md5checksum, requiredMatches, combinations);
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException();
				}
			}
		};
		thread.start();

		Thread.sleep(5000);
		assertTrue(thread.isAlive());
	}

}
