package com.nakasato.ghstore.domain.filter.impl;

import com.nakasato.ghstore.domain.filter.Filter;

public class DiscountCouponFilter extends Filter {
	private Integer customerId;
	private Boolean used;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId( Integer customerId ) {
		this.customerId = customerId;
	}

	public Boolean getUsed() {
		return used;
	}

	public void setUsed( Boolean used ) {
		this.used = used;
	}

}
