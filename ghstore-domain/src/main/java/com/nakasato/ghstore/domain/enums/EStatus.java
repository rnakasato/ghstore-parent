package com.nakasato.ghstore.domain.enums;

public enum EStatus {
	S();

	public static String getStatusDesc( int id ) {
		String status =null;
		if( id ==1 ) {
			status ="Disponivel";
		} else if( id ==2 ) {
			status ="Indisponivel";
		}
		return status;
	}

}
