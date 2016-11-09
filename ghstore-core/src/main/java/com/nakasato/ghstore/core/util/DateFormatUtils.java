package com.nakasato.ghstore.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {

	public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat( "yyyy-MM-dd" );
	public static SimpleDateFormat yyyyMM = new SimpleDateFormat( "yyyy-MM" );
	
	
	public static SimpleDateFormat ddMMyyyy = new SimpleDateFormat( "dd-MM-yyyy" );
	public static SimpleDateFormat MMyyyy = new SimpleDateFormat( "MM-yyyy" );
	public static SimpleDateFormat yyyy = new SimpleDateFormat( "yyyy" );

	public static String formatDateToDay( Date date ) {
		return ddMMyyyy.format( date );
	}

	public static String formatDateToMonth( Date date ) {
		return MMyyyy.format( date );
	}

	public static String formatDateToYear( Date date ) {
		return yyyy.format( date );
	}
	
	public static String formatDateToReverseDay( Date date ) {
		return yyyyMMdd.format( date );
	}
	
	public static String formatDateToReverseMonth( Date date ) {
		return yyyyMM.format( date );
	}
	
	
}
