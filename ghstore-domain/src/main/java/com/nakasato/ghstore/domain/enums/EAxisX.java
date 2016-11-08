package com.nakasato.ghstore.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum EAxisX {
	DAYS( "DAY", "Dias", "%d-%m-%Y" ), MONTHS( "MON", "Meses", "%m-%Y" ), YEARS( "YRS", "Anos", "%Y" );

	private String code;
	private String description;
	private String tickFormat;

	private static Map < String, EAxisX > axisXMap;

	private EAxisX( String code, String description, String tickFormat ) {
		this.code = code;
		this.description = description;
		this.tickFormat = tickFormat;
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

	public String getTickFormat() {
		return tickFormat;
	}

	public void setTickFormat( String tickFormat ) {
		this.tickFormat = tickFormat;
	}

	public static EAxisX getValue( String code ) {
		if( axisXMap == null ) {
			axisXMap = new HashMap<>();
			axisXMap.put( EAxisX.DAYS.getCode(), EAxisX.DAYS );
			axisXMap.put( EAxisX.MONTHS.getCode(), EAxisX.MONTHS );
			axisXMap.put( EAxisX.YEARS.getCode(), EAxisX.YEARS );

		}
		return axisXMap.get( code );
	}

}
