package com.nakasato.ghstore.core.filter.impl;

import com.nakasato.ghstore.core.filter.Filter;

public class CustomerFilter extends Filter{
	private Boolean loadAddress;
	private Boolean loadOrder;

	public Boolean getLoadAddress() {
		return loadAddress;
	}

	public void setLoadAddress(Boolean loadAddress) {
		this.loadAddress = loadAddress;
	}

	public Boolean getLoadOrder() {
		return loadOrder;
	}

	public void setLoadOrder(Boolean loadOrder) {
		this.loadOrder = loadOrder;
	}

}
