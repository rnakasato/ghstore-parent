package com.nakasato.ghstore.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum ESex {
	M( "M", "M" ), F( "F", "F" );

	private String code;
	private String description;

	private static Map < String, ESex > sexMap;

	private ESex( String code, String description ) {
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

	public static ESex getValue( String code ) {
		if( sexMap == null ) {
			sexMap = new HashMap<>();
			sexMap.put( ESex.M.getCode(), ESex.M );
			sexMap.put( ESex.F.getCode(), ESex.F );

		}
		return sexMap.get( code );
	}

}
