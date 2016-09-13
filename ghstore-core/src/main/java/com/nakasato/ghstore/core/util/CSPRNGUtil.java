package com.nakasato.ghstore.core.util;

import java.security.SecureRandom;

public class CSPRNGUtil {
	public static final Integer BYTE_06 = 6;
	
	
	public static String generateHex(Integer size){
		String randomCode = null;
		SecureRandom sr = new SecureRandom();
		byte[] bytes = new byte[size];
		sr.nextBytes(bytes);
		
		StringBuilder sb = new StringBuilder();
		for (byte bt : bytes) {
			sb.append(String.format("%02X", bt));
		}
		randomCode = sb.toString();
		
		return randomCode;
	}
}

