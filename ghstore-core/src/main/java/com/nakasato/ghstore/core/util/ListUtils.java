package com.nakasato.ghstore.core.util;

import java.util.List;

public class ListUtils {
	
	/**
	 * Verifica se a lista est� nula ou vazia
	 * @param list
	 * @return
	 */
	public static boolean isListEmpty(List list){
		boolean result = false;
		if(list == null || list.isEmpty()){
			result = true;
		}
		return result;
	}
}
