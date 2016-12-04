package com.nakasato.ghstore.domain.product.group;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.product.Product;

public class MostSoldProduct extends AbstractDomainEntity {
	private Product product;
	private Long amount;

	public MostSoldProduct() {
	}

	public MostSoldProduct( Product product, Long amount ) {
		this.product = product;
		this.amount = amount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct( Product product ) {
		this.product = product;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount( Long amount ) {
		this.amount = amount;
	}

}
