package com.nakasato.ghstore.web.mb.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.nakasato.ghstore.domain.enums.EAxisX;
import com.nakasato.ghstore.domain.enums.EAxisY;
import com.nakasato.ghstore.domain.enums.ELines;
import com.nakasato.ghstore.domain.enums.ESex;

@ManagedBean( name = "enumMB" )
@ViewScoped
public class EnumMB {
	private ESex sex;
	private EAxisY axisY;
	private EAxisX axisX;
	private ELines line;

	public ESex getSex() {
		return sex;
	}

	public void setSex( ESex sex ) {
		this.sex = sex;
	}

	public EAxisY getAxisY() {
		return axisY;
	}

	public void setAxisY( EAxisY axisY ) {
		this.axisY = axisY;
	}

	public EAxisX getAxisX() {
		return axisX;
	}

	public void setAxisX( EAxisX axisX ) {
		this.axisX = axisX;
	}

	public ELines getLine() {
		return line;
	}

	public void setLine( ELines line ) {
		this.line = line;
	}

}
