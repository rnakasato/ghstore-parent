package com.nakasato.ghstore.domain.filter.impl;

import java.util.List;

import com.nakasato.ghstore.domain.filter.Filter;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.domain.product.Subcategory;
import com.nakasato.ghstore.domain.product.Tag;
import com.nakasato.ghstore.domain.user.Customer;

public class WishlistFilter extends Filter {

	private String productName;
	private List < Tag > tagList;
	private Customer customer;
	private List < StoreCategory > storeCategoryList;
	private List < Subcategory > subcategoryList;

	public String getProductName() {
		return productName;
	}

	public void setProductName( String productName ) {
		this.productName = productName;
	}

	public List < Tag > getTagList() {
		return tagList;
	}

	public void setTagList( List < Tag > tagList ) {
		this.tagList = tagList;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer( Customer customer ) {
		this.customer = customer;
	}

	public List < StoreCategory > getStoreCategoryList() {
		return storeCategoryList;
	}

	public void setStoreCategoryList( List < StoreCategory > storeCategoryList ) {
		this.storeCategoryList = storeCategoryList;
	}

	public List < Subcategory > getSubcategoryList() {
		return subcategoryList;
	}

	public void setSubcategoryList( List < Subcategory > subcategoryList ) {
		this.subcategoryList = subcategoryList;
	}

}
