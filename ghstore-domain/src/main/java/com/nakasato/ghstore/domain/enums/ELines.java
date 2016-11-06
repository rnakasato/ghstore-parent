package com.nakasato.ghstore.domain.enums;

public enum ELines {
	GENERAL_SUMMARY( "GEN", "Índice geral" ), 
	TOP_TEN_CITIES( "TOP", "10 cidades com maior número de vendas" ), 
	SEX( "SEX", "Sexo" ), 
	CATEGORY( "CAT", "Categoria" );

	private String code;
	private String description;

	private ELines( String code, String description ) {
		this.code =code;
		this.description =description;
	}

	public String getCode() {
		return code;
	}

	public void setCode( String code ) {
		this.code =code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description =description;
	}

}
