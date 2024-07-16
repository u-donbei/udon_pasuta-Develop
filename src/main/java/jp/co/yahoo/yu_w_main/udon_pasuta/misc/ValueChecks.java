/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.misc;

public final class ValueChecks {
	// @formatter:off
	private ValueChecks() {}
	// @formatter:on
	
	public static void checkString(String s, String message) {
		if (s == null || (s.replaceAll(" ", "").equals("")) || (s.replaceAll("　", "").equals(""))) {
			if (s == null) {
				throw new IllegalArgumentException(message, new NullPointerException(""));
			}
			throw new IllegalArgumentException(message);
		}
	}

	public static void checkNotNegative(double i, String message) {
		if (i < 0) {
			throw new IllegalArgumentException(message);
		}
	}

}
