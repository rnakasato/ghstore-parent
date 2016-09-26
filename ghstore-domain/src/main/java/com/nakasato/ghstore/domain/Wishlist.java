package com.nakasato.ghstore.domain;

import java.util.Set;

public class Wishlist extends AbstractDomainEntity {
	private Set<Product> productList;
	private Set<Product> newItems;
	private Set<Product> removedItems;

	public Set<Product> getProductList() {
		return productList;
	}

	public void setProductList(Set<Product> productList) {
		this.productList = productList;
	}

	public Set<Product> getNewItems() {
		return newItems;
	}

	public void setNewItems(Set<Product> newItems) {
		this.newItems = newItems;
	}

	public Set<Product> getRemovedItems() {
		return removedItems;
	}

	public void setRemovedItems(Set<Product> removedItems) {
		this.removedItems = removedItems;
	}

}
