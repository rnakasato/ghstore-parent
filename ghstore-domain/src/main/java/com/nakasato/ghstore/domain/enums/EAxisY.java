package com.nakasato.ghstore.domain.enums;

public enum EAxisY {
	SOLD_AMOUNT( "PMV", "Quantidade de produtos vendidos" ), 
	TOTAL_VALUE( "VTA", "Valor total arrecadado" );

	private String code;
	private String description;

	private EAxisY( String code, String description ) {
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
