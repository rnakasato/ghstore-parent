package com.nakasato.ghstore.domain;

import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Promotion;

public abstract class StoreItem extends AbstractDomainEntity {
	private Product product;
	private Integer amount;
	private Long totalWeigth;
	private Double totalValue;
	private Promotion promotion;
	private Double itemValue;

	public Double getPromotionUnitValue() {
		Double value = 0D;
		if( promotion != null && product != null ) {
			Double price = product.getPrice();
			Double discount = price * ( ( promotion.getDiscountPercentage() ) / 100 );
			value = price - discount;
		}

		return value;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount( Integer amount ) {
		this.amount = amount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct( Product product ) {
		this.product = product;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue( Double totalValue ) {
		this.totalValue = totalValue;
	}

	public Long getTotalWeigth() {
		return totalWeigth;
	}

	public void setTotalWeigth( Long totalWeigth ) {
		this.totalWeigth = totalWeigth;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion( Promotion promotion ) {
		this.promotion = promotion;
	}

	public Double getItemValue() {
		return itemValue;
	}

	public void setItemValue( Double itemValue ) {
		this.itemValue = itemValue;
	}

}
