package com.nakasato.ghstore.core.util;

import java.util.List;

public class ListUtils {

	/**
	 * Verifica se a lista está nula ou vazia
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmpty( List list ) {
		boolean result = false;
		if( list == null || list.isEmpty() ) {
			result = true;
		}
		return result;
	}
	
	
	/**
	 * Verifica se a lista está nula ou vazia
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty( List list ) {
		return !isEmpty( list );
	}
}
