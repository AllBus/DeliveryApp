package com.tytosoft.badgesapp.model.utils;

/**
 * Created by Kos on 13.07.2016.
 */
public class ModeLUtils {
	public static String getDateText(long date) {
		return String.format("%1$te %1$tB %1$tY", date);
	}

	public static String getDateDayMonthText(long date) {
		return java.lang.String.format("%1$te %1$tB",date);
	}
}
