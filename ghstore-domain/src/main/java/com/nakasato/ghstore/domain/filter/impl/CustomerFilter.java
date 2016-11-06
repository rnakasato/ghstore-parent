package com.nakasato.ghstore.domain.filter.impl;

import com.nakasato.ghstore.domain.filter.Filter;

public class CustomerFilter extends Filter {
	private Boolean loadAddress;
	private Boolean loadOrder;
	private String userName;

	public Boolean getLoadAddress() {
		return loadAddress;
	}

	public void setLoadAddress( Boolean loadAddress ) {
		this.loadAddress =loadAddress;
	}

	public Boolean getLoadOrder() {
		return loadOrder;
	}

	public void setLoadOrder( Boolean loadOrder ) {
		this.loadOrder =loadOrder;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName( String userName ) {
		this.userName =userName;
	}

}
