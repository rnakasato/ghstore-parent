package com.nakasato.ghstore.domain.product;

import java.util.Date;
import java.util.List;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public class Promotion extends AbstractDomainEntity {
	private List < Product > productList;

	private Double discountPercentage;
	private Date startDate;
	private Date endDate;
	private Boolean active;

	// Utilizado somente para
	// identificar se a promoção será desativada
	// é uma informação não persistida
	private Boolean disable;
	private Boolean cancel;

	public String getActiveDescription() {
		String desc = null;
		if( active != null ) {
			if( active ) {
				desc = "Ativo";
			} else {
				desc = "Inativo";
			}
		}
		return desc;
	}

	public List < Product > getProductList() {
		return productList;
	}

	public void setProductList( List < Product > productList ) {
		this.productList = productList;
	}

	public Double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage( Double discountPercentage ) {
		this.discountPercentage = discountPercentage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate( Date startDate ) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate( Date endDate ) {
		this.endDate = endDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive( Boolean active ) {
		this.active = active;
	}

	public Boolean getDisable() {
		return disable;
	}

	public void setDisable( Boolean disable ) {
		this.disable = disable;
	}

	public Boolean getCancel() {
		return cancel;
	}

	public void setCancel( Boolean cancel ) {
		this.cancel = cancel;
	}

}
