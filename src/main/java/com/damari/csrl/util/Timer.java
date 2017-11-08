package com.damari.csrl.util;

public class Timer {

	private static final long MILLIS_PER_MINUTE = (1000 * 60);
	private static final long MILLIS_PER_HOUR = (1000 * 60 * 60);

	private long start;

	private long stop;

	public Timer() {
		start();
	}

	public void start() {
		start = System.currentTimeMillis();
	}

	public void stop() {
		stop = System.currentTimeMillis();
	}

	public long getSecs() {
		return (stop - start) / 1000L;
	}

	public long getMins() {
		return (stop - start) / MILLIS_PER_MINUTE;
	}

	public long getMillis() {
		return getMillis(false);
	}
	public long getMillis(boolean useOngoingTimer) {
		long millis = (useOngoingTimer ? System.currentTimeMillis() : stop) - start;
		return millis;
	}

	/**
	 * Get minutes and seconds. By default using .stop() time, but may be overridden.
	 * @return String with minutes and seconds.
	 */
	public String getMinutesAndSeconds() {
		return getMinutesAndSeconds(false);
	}
	public String getMinutesAndSeconds(boolean useOngoingTimer) {
		long millis = (useOngoingTimer ? System.currentTimeMillis() : stop) - start;
		long seconds = millis / 1000L;
		long minutes = millis / MILLIS_PER_MINUTE;
		return minutes + "m" + seconds + "s";
	}

	/**
	 * Get hours and minutes. By default using .stop() time, but may be overridden.
	 * @return String with hours and minutes.
	 */
	public String getHoursAndMinutes() {
		return getHoursAndMinutes(false);
	}
	public String getHoursAndMinutes(boolean useOngoingTimer) {
		long millis = (useOngoingTimer ? System.currentTimeMillis() : stop) - start;
		long hours = millis / MILLIS_PER_HOUR;
		long minutes = millis / MILLIS_PER_MINUTE - hours * 60L;
		return hours + "h" + minutes + "m";
	}

	@Override
	public String toString() {
		return String.valueOf(stop - start) + "ms";
	}

}
