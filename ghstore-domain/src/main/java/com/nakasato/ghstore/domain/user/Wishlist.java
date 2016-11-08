package com.nakasato.ghstore.domain.user;

import java.util.List;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.product.Product;

public class Wishlist extends AbstractDomainEntity {
	private List < Product > productList;
	private List < Product > newItems;
	private List < Product > removedItems;

	public List < Product > getProductList() {
		return productList;
	}

	public void setProductList( List < Product > productList ) {
		this.productList = productList;
	}

	public List < Product > getNewItems() {
		return newItems;
	}

	public void setNewItems( List < Product > newItems ) {
		this.newItems = newItems;
	}

	public List < Product > getRemovedItems() {
		return removedItems;
	}

	public void setRemovedItems( List < Product > removedItems ) {
		this.removedItems = removedItems;
	}

}
