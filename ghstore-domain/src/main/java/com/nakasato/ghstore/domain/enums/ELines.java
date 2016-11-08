package com.nakasato.ghstore.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum ELines {
	GENERAL_SUMMARY( "GEN", "Índice geral" ), TOP_TEN_CITIES( "TOP",
			"10 cidades com maior número de vendas" ), SEX( "SEX", "Sexo" ), CATEGORY( "CAT", "Categoria" );

	private String code;
	private String description;

	private static Map < String, ELines > lineMap;

	private ELines( String code, String description ) {
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

	public static ELines getValue( String code ) {
		if( lineMap == null ) {
			lineMap = new HashMap<>();
			lineMap.put( ELines.GENERAL_SUMMARY.getCode(), ELines.GENERAL_SUMMARY );
			lineMap.put( ELines.TOP_TEN_CITIES.getCode(), ELines.TOP_TEN_CITIES );
			lineMap.put( ELines.SEX.getCode(), ELines.SEX );
			lineMap.put( ELines.CATEGORY.getCode(), ELines.CATEGORY );
		}
		return lineMap.get( code );
	}

}
