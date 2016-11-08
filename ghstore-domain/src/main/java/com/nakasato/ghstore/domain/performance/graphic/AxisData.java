package com.nakasato.ghstore.domain.performance.graphic;

public class AxisData implements Comparable < AxisData > {
	private String axisX;
	private Number axisY;

	public String getAxisX() {
		return axisX;
	}

	public void setAxisX( String axisX ) {
		this.axisX = axisX;
	}

	public Number getAxisY() {
		return axisY;
	}

	public void setAxisY( Number axisY ) {
		this.axisY = axisY;
	}

	@Override
	public int compareTo( AxisData o ) {
		return this.getAxisX().compareTo( o.getAxisX() );
	}

}
