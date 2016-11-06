package com.nakasato.ghstore.web.mb.product.graphic;

import java.util.List;

import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.domain.product.Subcategory;
import com.nakasato.ghstore.domain.user.City;
import com.nakasato.ghstore.domain.user.State;
import com.nakasato.ghstore.web.mb.BaseMB;

/**
 * 
 * @author rafae Descrição:
 */
public class ProductGraphicMB extends BaseMB {

	protected List < StoreCategory > categoryList;
	protected List < Product > productList;
	private List < State > stateList;
	private List < City > cityList;

	@ Override
	public void clearFilter () {

	}

	public List < StoreCategory > getCategoryList () {
		return categoryList;
	}

	public void setCategoryList ( List < StoreCategory > categoryList ) {
		this.categoryList =categoryList;
	}

	public List < Product > getProductList () {
		return productList;
	}

	public void setProductList ( List < Product > productList ) {
		this.productList =productList;
	}

	public List < State > getStateList () {
		return stateList;
	}

	public void setStateList ( List < State > stateList ) {
		this.stateList =stateList;
	}

	public List < City > getCityList () {
		return cityList;
	}

	public void setCityList ( List < City > cityList ) {
		this.cityList =cityList;
	}

}
