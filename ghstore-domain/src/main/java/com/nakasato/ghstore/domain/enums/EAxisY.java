package com.nakasato.ghstore.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum EAxisY {
	SOLD_AMOUNT( "PMV", "Quantidade de produtos vendidos" ), TOTAL_VALUE( "VTA", "Valor total arrecadado" );

	private String code;
	private String description;

	private static Map < String, EAxisY > axisYMap;

	private EAxisY( String code, String description ) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode( String code ) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public static EAxisY getValue( String code ) {
		if( axisYMap == null ) {
			axisYMap = new HashMap<>();
			axisYMap.put( EAxisY.SOLD_AMOUNT.getCode(), EAxisY.SOLD_AMOUNT );
			axisYMap.put( EAxisY.TOTAL_VALUE.getCode(), EAxisY.TOTAL_VALUE );

		}
		return axisYMap.get( code );
	}

}
