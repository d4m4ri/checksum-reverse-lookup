package com.damari.csrl.tests.md5.checksum;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import com.damari.csrl.app.ReverseLookupMD5;
import com.damari.csrl.tests.MD5TestWrapper;

public class TestMatches {

	private final MD5TestWrapper md5;

	public TestMatches() {
		md5 = new MD5TestWrapper();

		// Enable debug
		Properties properties = new Properties();
		properties.setProperty("log4j.logger.com.damari.csrl", "debug");
		PropertyConfigurator.configure(properties);
	}

	@Test
	public void givenShorterPatternThanExpectedStringThenExpectItToBeFound() throws UnsupportedEncodingException {
		String searchPattern = "abcdefghijklmnopqrstuvwxyzåäö0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ!_-\"|\\@";
		String md5string = "gE-QX";
		String md5checksum = md5.asHex(md5string);
		int requiredMatches = 1;
		int combinations = searchPattern.length(); // or: md5string.length()

		ReverseLookupMD5 rlMD5 = new ReverseLookupMD5();
		List<String> matches = rlMD5.find(searchPattern, md5checksum, requiredMatches, combinations);

		assertTrue("Expected to find " + md5string + " in results", matches.contains(md5string));
	}

	@Test
	public void givenEasyChecksumWithBroadSearchPatternThenExpectStringToBeFound() throws UnsupportedEncodingException {
		String searchPattern = "abcdefghijklmnopqrstuvwxyzåäö0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ!_-\"|\\@";
		String md5string = "town";
		String md5checksum = md5.asHex(md5string);
		int requiredMatches = 1;
		int combinations = searchPattern.length();

		ReverseLookupMD5 rlMD5 = new ReverseLookupMD5();
		List<String> matches = rlMD5.find(searchPattern, md5checksum, requiredMatches, combinations);

		assertTrue("Expected to find " + md5string + " in results", matches.contains(md5string));
	}

	@Test
	public void givenSimpleChecksumWithBroadSearchPatternThenExpectStringToBeFound() throws UnsupportedEncodingException {
		String searchPattern = "abcdefghijklmnopqrstuvwxyzåäö0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ!_-\"|\\@";
		String md5checksum = "0cc175b9c0f1b6a831c399e269772661"; // MD5 of "a"
		String md5string = "a";
		int requiredMatches = 1;
		int combinations = searchPattern.length();

		ReverseLookupMD5 rlMD5 = new ReverseLookupMD5();
		List<String> matches = rlMD5.find(searchPattern, md5checksum, requiredMatches, combinations);

		assertTrue("Expected to find " + md5string + " in results", matches.contains(md5string));
	}

	@Test
	public void givenSimpleChecksumWithNarrowSearchPatternThenExpectStringToBeFound() throws UnsupportedEncodingException {
		String searchPattern = "abc";
		String md5checksum = "0cc175b9c0f1b6a831c399e269772661"; // MD5 of "a"
		String md5string = "a";
		int requiredMatches = 1;
		int combinations = searchPattern.length();

		ReverseLookupMD5 rlMD5 = new ReverseLookupMD5();
		List<String> matches = rlMD5.find(searchPattern, md5checksum, requiredMatches, combinations);

		assertTrue("Expected to find " + md5string + " in results", matches.contains(md5string));
	}

	@Test
	public void givenSimpleUpperCaseChecksumWithBroadSearchPatternThenExpectStringToBeFound() throws UnsupportedEncodingException {
		String searchPattern = "abcdefghijklmnopqrstuvwxyzåäö0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ!_-\"|\\@";
		String md5checksum = "0CC175B9C0F1B6A831C399E269772661"; // MD5 of "a"
		String md5string = "a";
		int requiredMatches = 1;
		int combinations = searchPattern.length();

		ReverseLookupMD5 rlMD5 = new ReverseLookupMD5();
		List<String> matches = rlMD5.find(searchPattern, md5checksum, requiredMatches, combinations);

		assertTrue("Expected to find " + md5string + " in results", matches.contains(md5string));
	}

}
