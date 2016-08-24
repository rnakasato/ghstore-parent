package com.nakasato.ghstore.core.util;

public class StringUtils {
	
	public static boolean isEmptyOrNull(String string){
		boolean isEmptyOrNull = false;
		if(string == null || string.equals("")){
			isEmptyOrNull = true;
		}
		return isEmptyOrNull;
	}
}
