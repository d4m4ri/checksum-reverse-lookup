/*
 * http://www.mscs.dal.ca/~selinger/md5collision/
 */
package com.damari.csrl.tests.md5.checksum;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import com.damari.csrl.tests.MD5TestWrapper;

public class TestCollisions {

	private final MD5TestWrapper md5;

	public TestCollisions() {
		md5 = new MD5TestWrapper();
	}

	@Test
	public void givenDifferentInputsThenExpectChecksumCollision() throws DecoderException {
		//                                                                                      x
		String input1 = "4dc968ff0ee35c209572d4777b721587d36fa7b21bdc56b74a3dc0783e7b9518afbfa200a8284bf36e8e4b55b35f427593d849676da0d1555d8360fb5f07fea2";
		String input2 = "4dc968ff0ee35c209572d4777b721587d36fa7b21bdc56b74a3dc0783e7b9518afbfa202a8284bf36e8e4b55b35f427593d849676da0d1d55d8360fb5f07fea2";
		assertFalse("Expected inputs to be different", input1.equalsIgnoreCase(input2));

		md5.Init();
		md5.Update(Hex.decodeHex(input1.toCharArray()));
		String checksum1 = md5.asHex();

		md5.Init();
		md5.Update(Hex.decodeHex(input2.toCharArray()));
		String checksum2 = md5.asHex();

		assertTrue("Expected input checksums to collide", checksum1.equalsIgnoreCase(checksum2));
	}

	@Test
	public void givenDifferentInputsThenExpectChecksumCollision2() throws DecoderException {
		String input1 = "d131dd02c5e6eec4693d9a0698aff95c2fcab58712467eab4004583eb8fb7f89" +
						"55ad340609f4b30283e488832571415a085125e8f7cdc99fd91dbdf280373c5b" +
						"d8823e3156348f5bae6dacd436c919c6dd53e2b487da03fd02396306d248cda0" +
						"e99f33420f577ee8ce54b67080a80d1ec69821bcb6a8839396f9652b6ff72a70";
		String input2 = "d131dd02c5e6eec4693d9a0698aff95c2fcab50712467eab4004583eb8fb7f89" +
						"55ad340609f4b30283e4888325f1415a085125e8f7cdc99fd91dbd7280373c5b" +
						"d8823e3156348f5bae6dacd436c919c6dd53e23487da03fd02396306d248cda0" +
						"e99f33420f577ee8ce54b67080280d1ec69821bcb6a8839396f965ab6ff72a70";
		assertFalse("Expected inputs to be different", input1.equalsIgnoreCase(input2));

		md5.Init();
		md5.Update(Hex.decodeHex(input1.toCharArray()));
		String checksum1 = md5.asHex();

		md5.Init();
		md5.Update(Hex.decodeHex(input2.toCharArray()));
		String checksum2 = md5.asHex();

		assertTrue("Expected input checksums to collide", checksum1.equalsIgnoreCase(checksum2));
	}

}
