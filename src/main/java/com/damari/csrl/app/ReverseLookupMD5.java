/*
 * This is much better and faster, supports CPU/GPU and many hash version:
 * https://github.com/hashcat/hashcat
 * Similar command line would be:
 * rm -f hashcat.potfile ; time ./hashcat --hash-type 0 --attack-mode 3 --increment --increment-min 1 --increment-max 10 5f26ab29e5cb6170dfdcbaf9f6ef44eb ?a?a?a?a?a?a?a?a?a?a ; cat ./hashcat.potfile
 * Using MacBook Air 17 GPU takes ~22sec
 * This code using CPU takes ~2.2min
 */
package com.damari.csrl.app;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.damari.csrl.util.MD5Checksum;
import com.damari.csrl.util.Timer;

public class ReverseLookupMD5 {

	private static final Logger log = LoggerFactory.getLogger(ReverseLookupMD5.class.getName());

	private final static float HUNDRED_DIV_ONEMIL_FACTOR = 100f / 1_000_000f;

	private final MD5Checksum md5;

	private final Charset iso88591charset = Charset.forName("ISO-8859-1");

	private String findChecksum;

	private long attempt;
	private long attemptPrev;

	private int lengthPrev;

	private Timer timerTotal;
	private Timer timerPerCombination;
	private Timer timerMhSec;

	public ReverseLookupMD5() {
		timerTotal = new Timer();
		timerPerCombination = new Timer();
		timerMhSec = new Timer();
		md5 = new MD5Checksum();
	}

	/**
	 * Find string out of MD5 using bruteforce.
	 * @param pattern char combinations.
	 * @param findChecksum checksum to reverse.
	 * @param matches number of matches required before considering it done.
	 * @param combinations integer with number of combinations.
	 * @throws UnsupportedEncodingException unsupported encoding.
	 * @return Array with matching strings.
	 */
	public List<String> find(String pattern, String findChecksum, int matches, int combinations) throws UnsupportedEncodingException {
		if (matches < 1) throw new RuntimeException("Number of matches has to be at least one.");
		if (combinations < pattern.length()) throw new RuntimeException("Combinations has to be same or greater than pattern length.");

		timerTotal.start();
		this.findChecksum = findChecksum;
		attempt = 0;
		attemptPrev = 0;
		lengthPrev = 0;
		timerMhSec.start();

		pattern = padLeft(pattern, combinations);
		char[] input = pattern.toCharArray();
		int length  = input.length;
		List<String> matchList = new ArrayList<>();

		char[] result = new char[length];
		int[] index = new int[length];

		// initialize arrays
		Arrays.fill(result, 0, result.length, input[0]);
		Arrays.fill(index,  0, index.length, 0);

		// loop over output lengths
		boolean exit = false;
		int i = 1;
		for (; i <= length; i++) {
			int updateIndex = 0;
			do {
				String matchString = element(result, 0, i);
				if (matchString != null) {
					matchList.add(matchString);
					if (--matches == 0) {
						exit = true;
						break;
					}
				}

				// update values that need to reset
				for (updateIndex = i - 1;
						updateIndex != -1 && ++index[updateIndex] == length;
						result[updateIndex] = input[0], index[updateIndex] = 0, updateIndex--);

				// update the character that is not resetting, if valid
				if (updateIndex != -1) result[updateIndex] = input[index[updateIndex]];
			} while (updateIndex != -1);
			if (exit) {
				break;
			}
		}

		// done (found or not found)
		log.debug("# Length {} took {} ({}ms)", i, timerPerCombination.getHoursAndMinutes(true),
				timerPerCombination.getMillis(true));
		timerTotal.stop();
		return matchList;
	}

	private String element(char[] result, int offset, int length) throws UnsupportedEncodingException {
		String reverseLookup = new String(result, offset, length);
		md5.Init();
		md5.Update(reverseLookup.getBytes(iso88591charset), length);
		String md5hex = md5.asHex();

		if (md5hex.equalsIgnoreCase(findChecksum)) {
			return reverseLookup;
		}

		if (lengthPrev != length) {
			log.debug("# Length {} took {} ({}ms)", lengthPrev, timerPerCombination.getHoursAndMinutes(true),
					timerPerCombination.getMillis(true));
		}

		if (timerMhSec.getMillis(true) >= 1000) {
			log.debug("{} Mh/s curLength={}; tTot={}; str={}; md5={}",
					Math.round((attempt - attemptPrev) * HUNDRED_DIV_ONEMIL_FACTOR) / 100f,
					reverseLookup.length(), timerTotal.getHoursAndMinutes(true), reverseLookup, md5hex);
			timerMhSec.start();
			attemptPrev = attempt;
		}

		lengthPrev = length;
		attempt++;
		return null;
	}

	public static String padLeft(String str, int length) {
		return String.format("%1$" + length + "s", str);
	}

}