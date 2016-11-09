package com.nakasato.ghstore.domain.performance.graphic;

import java.util.Map;

public class PerformanceGraphicData implements Comparable < PerformanceGraphicData >{
	
	private String description;
	private Number axisTotal;
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

	public Number getAxisTotal() {
		return axisTotal;
	}

	public void setAxisTotal( Number axisTotal ) {
		this.axisTotal = axisTotal;
	}

	@Override
	public int compareTo( PerformanceGraphicData o ) {
		int compared = 0;
		if(this.axisTotal instanceof Double){
			Double thisValue = this.axisTotal.doubleValue();
			Double thatValue = o.getAxisTotal().doubleValue();
			compared = thisValue.compareTo( thatValue );		
		}else{
			Integer thisValue = this.axisTotal.intValue();
			Integer thatValue = o.getAxisTotal().intValue();
			compared = thisValue.compareTo( thatValue );
		}
		
		return compared;
	}

}
