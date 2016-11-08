package com.nakasato.ghstore.domain.performance.graphic;

import java.util.Map;

public class PerformanceGraphicData {
	private String description;
	Map < String, AxisData > axisData;

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public Map < String, AxisData > getAxisData() {
		return axisData;
	}

	public void setAxisData( Map < String, AxisData > axisData ) {
		this.axisData = axisData;
	}

}
