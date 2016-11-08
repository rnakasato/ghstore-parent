package com.nakasato.ghstore.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {

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
}
