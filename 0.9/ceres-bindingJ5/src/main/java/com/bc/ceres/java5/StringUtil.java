package com.bc.ceres.java5;

/**
 * Compatibility replacement for String.isEmpty (as of Java6)
 * which is not available in Java5
 * @author olaf
 *
 */
public class StringUtil {
	/**
	 * determine if value is empty. Backport replacement for 
	 * java.lang.String.isEmpty() in Java6
	 * @param value 
	 * @return true if String is nonnull and empty
	 * @throws NullpointerException if value is null (as it mimics Java6 behaviour)
	 */
	public static boolean isEmpty(String value) {
		return value.equals("");
	}
}
