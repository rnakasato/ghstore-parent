
package com.nakasato.ghstore.core.util;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class FormatUtils {
	public static String formatToCurrency( Double value ) {
		String symbol = "R$ ";
		StringBuilder formatted = new StringBuilder();
		formatted.append( symbol );
		formatted.append( String.format( "%.2f", value ) );
		return formatted.toString();
	}

	public static String formatToCurrencyNoSymbol( Double value ) {
		String formatted = String.format( "%.2f", value );
		formatted = formatted.replace( ",", "." );
		return formatted;
	}

	public static String getFlatCPF( String cpf ) {
		String flatCPF = cpf;
		flatCPF = flatCPF.replace( ".", "" );
		flatCPF = flatCPF.replace( "-", "" );
		return flatCPF;
	}

	public static String removeHifen( String value ) {
		String formatted = value;
		formatted = formatted.replace( "-", "" );
		return formatted;
	}

	public static String formatString( String value, String pattern ) {
		MaskFormatter mf;
		try {
			mf = new MaskFormatter( pattern );
			mf.setValueContainsLiteralCharacters( false );
			return mf.valueToString( value );
		} catch( ParseException ex ) {
			return value;
		}
	}
}
