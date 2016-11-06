package com.nakasato.ghstore.domain.filter.impl;

import java.util.List;

import com.nakasato.ghstore.domain.enums.EAxisX;
import com.nakasato.ghstore.domain.enums.EAxisY;
import com.nakasato.ghstore.domain.enums.ELines;
import com.nakasato.ghstore.domain.enums.ESex;
import com.nakasato.ghstore.domain.filter.Filter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.domain.user.State;

/**
 * 
 * @author rafae Descrição:
 */
public class ProductGraphicFilter extends Filter {

	private static final long serialVersionUID =1L;

	private List < Product > productList;
	private List < StoreCategory > categoryList;
	private List < State > stateList;
	private Boolean isTopTenCities;
	private ESex sex;
	private EAxisX axisX;
	private EAxisY axisY;
	private ELines lines;

	public List < Product > getProductList() {
		return productList;
	}

	public void setProductList( List < Product > productList ) {
		this.productList =productList;
	}

	public List < StoreCategory > getCategoryList() {
		return categoryList;
	}

	public void setCategoryList( List < StoreCategory > categoryList ) {
		this.categoryList =categoryList;
	}

	public List < State > getStateList() {
		return stateList;
	}

	public void setStateList( List < State > stateList ) {
		this.stateList =stateList;
	}

	public Boolean getIsTopTenCities() {
		return isTopTenCities;
	}

	public void setIsTopTenCities( Boolean isTopTenCities ) {
		this.isTopTenCities =isTopTenCities;
	}

	public ESex getSex() {
		return sex;
	}

	public void setSex( ESex sex ) {
		this.sex =sex;
	}

	public EAxisX getAxisX() {
		return axisX;
	}

	public void setAxisX( EAxisX axisX ) {
		this.axisX =axisX;
	}

	public EAxisY getAxisY() {
		return axisY;
	}

	public void setAxisY( EAxisY axisY ) {
		this.axisY =axisY;
	}

	public ELines getLines() {
		return lines;
	}

	public void setLines( ELines lines ) {
		this.lines =lines;
	}

}
