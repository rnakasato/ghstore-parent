package com.nakasato.ghstore.core.filter.impl;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public class OrderStatusFilter extends AbstractDomainEntity{
	private String code;
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
