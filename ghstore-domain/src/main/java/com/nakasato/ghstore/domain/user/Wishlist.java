package com.nakasato.ghstore.domain.user;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.product.Product;

public class Wishlist extends AbstractDomainEntity {
	private Product product;
	private Customer customer;

	public Product getProduct() {
		return product;
	}

	public void setProduct( Product product ) {
		this.product = product;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer( Customer customer ) {
		this.customer = customer;
	}

}
