package com.digital.telco.utility.common;

import java.util.Calendar;
import java.util.TimeZone;

public class Utils {

	public static Long getCurrentTimeInMiliSec() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		long time = cal.getTimeInMillis();
		return time;
	}
	
}
