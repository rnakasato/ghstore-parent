package com.nakasato.ghstore.domain.enums;

public enum ESex {
	M( "M", "M" ),
	F( "S", "S" );

	private String code;
	private String description;

	private ESex( String code, String description ) {
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
