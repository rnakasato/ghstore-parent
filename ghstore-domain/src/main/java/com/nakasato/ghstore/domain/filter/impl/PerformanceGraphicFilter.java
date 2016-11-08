package com.nakasato.ghstore.domain.filter.impl;

import java.util.Date;
import java.util.List;

import com.nakasato.ghstore.domain.filter.Filter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.domain.user.City;
import com.nakasato.ghstore.domain.user.State;

/**
 * 
 * @author rafae Descrição:
 */
public class PerformanceGraphicFilter extends Filter {

	private static final long serialVersionUID = 1L;

	private List < Product > productList;
	private List < StoreCategory > categoryList;
	private List < City > cityList;
	private State state;
	private List < String > sex;
	private String axisX;
	private String axisY;
	private String lines;

	private Date startDate;
	private Date endDate;

	public List < Product > getProductList() {
		return productList;
	}

	public void setProductList( List < Product > productList ) {
		this.productList = productList;
	}

	public List < StoreCategory > getCategoryList() {
		return categoryList;
	}

	public void setCategoryList( List < StoreCategory > categoryList ) {
		this.categoryList = categoryList;
	}

	public List < City > getCityList() {
		return cityList;
	}

	public void setCityList( List < City > cityList ) {
		this.cityList = cityList;
	}

	public State getState() {
		return state;
	}

	public void setState( State state ) {
		this.state = state;
	}

	public List < String > getSex() {
		return sex;
	}

	public void setSex( List < String > sex ) {
		this.sex = sex;
	}

	public String getAxisX() {
		return axisX;
	}

	public void setAxisX( String axisX ) {
		this.axisX = axisX;
	}

	public String getAxisY() {
		return axisY;
	}

	public void setAxisY( String axisY ) {
		this.axisY = axisY;
	}

	public String getLines() {
		return lines;
	}

	public void setLines( String lines ) {
		this.lines = lines;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate( Date startDate ) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate( Date endDate ) {
		this.endDate = endDate;
	}

}
