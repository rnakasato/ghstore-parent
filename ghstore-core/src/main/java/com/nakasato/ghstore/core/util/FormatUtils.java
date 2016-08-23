
package com.nakasato.ghstore.core.util;

public class FormatUtils {
	public static String formatToCurrency(Double value){
		String symbol = "R$ ";
		StringBuilder formatted = new StringBuilder();
		formatted.append(symbol);
		formatted.append(String.format("%.2f", value));
		return formatted.toString();
	}	
	
	public static String formatToCurrencyNoSymbol(Double value){
		String formatted = String.format("%.2f", value);
		formatted = formatted.replace(",", ".");
		return formatted;
	}	
}
