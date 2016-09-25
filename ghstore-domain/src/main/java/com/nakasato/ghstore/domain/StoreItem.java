package com.nakasato.ghstore.domain;

public abstract class StoreItem extends AbstractDomainEntity {
	private Product product;
	private Integer amount;
	private Long totalWeigth;
	private Double totalValue;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public Long getTotalWeigth() {
		return totalWeigth;
	}

	public void setTotalWeigth(Long totalWeigth) {
		this.totalWeigth = totalWeigth;
	}

}
