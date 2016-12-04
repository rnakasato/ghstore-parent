package com.nakasato.ghstore.domain.filter.impl;

import java.util.List;

import com.nakasato.ghstore.domain.filter.Filter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.domain.user.Customer;

public class MostSoldProductFilter extends Filter {
	private Customer customer;
	private List < StoreCategory > categoryList;
	private List < Product > notInList;
	private Integer maxResults;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer( Customer customer ) {
		this.customer = customer;
	}

	public List < StoreCategory > getCategoryList() {
		return categoryList;
	}

	public void setCategoryList( List < StoreCategory > categoryList ) {
		this.categoryList = categoryList;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults( Integer maxResults ) {
		this.maxResults = maxResults;
	}

	public List < Product > getNotInList() {
		return notInList;
	}

	public void setNotInList( List < Product > notInList ) {
		this.notInList = notInList;
	}

}
