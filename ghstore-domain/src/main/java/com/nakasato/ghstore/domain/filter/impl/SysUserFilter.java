package com.nakasato.ghstore.domain.filter.impl;

import com.nakasato.ghstore.domain.filter.Filter;

public class SysUserFilter extends Filter{
	
	private Boolean loadAddress;
	private String userName;
	private Boolean active;

	public Boolean getLoadAddress() {
		return loadAddress;
	}

	public void setLoadAddress( Boolean loadAddress ) {
		this.loadAddress = loadAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName( String userName ) {
		this.userName = userName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive( Boolean active ) {
		this.active = active;
	}

}
