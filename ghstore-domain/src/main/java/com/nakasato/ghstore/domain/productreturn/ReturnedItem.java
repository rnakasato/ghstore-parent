package com.nakasato.ghstore.domain.productreturn;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.product.Product;

public class ReturnedItem extends AbstractDomainEntity {
	private Product product;
	private ProductReturn productReturn;
	private Integer amount;
	private Integer originalAmount;
	private Double totalValue;

	public Product getProduct() {
		return product;
	}

	public void setProduct( Product product ) {
		this.product = product;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount( Integer amount ) {
		this.amount = amount;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue( Double totalValue ) {
		this.totalValue = totalValue;
	}

	public Integer getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount( Integer originalAmount ) {
		this.originalAmount = originalAmount;
	}

	public ProductReturn getProductReturn() {
		return productReturn;
	}

	public void setProductReturn( ProductReturn productReturn ) {
		this.productReturn = productReturn;
	}

}
