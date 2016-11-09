package com.nakasato.ghstore.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum EAxisX {
	DAYS( "DAY", "Dias", "%d-%m-%Y", "86400000" ), MONTHS( "MON", "Meses", "%m-%Y", "2592000000" ), YEARS( "YRS",
			"Anos", "%Y", "31104000000" );

	private String code;
	private String description;
	private String tickFormat;
	private String tickInterval;

	private static Map < String, EAxisX > axisXMap;

	private EAxisX( String code, String description, String tickFormat, String tickInterval ) {
		this.code = code;
		this.description = description;
		this.tickFormat = tickFormat;
		this.tickInterval = tickInterval;
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

	public String getTickInterval() {
		return tickInterval;
	}

	public void setTickInterval( String tickInterval ) {
		this.tickInterval = tickInterval;
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
