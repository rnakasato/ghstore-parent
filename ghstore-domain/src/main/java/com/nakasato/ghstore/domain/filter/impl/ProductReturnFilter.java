package com.nakasato.ghstore.domain.filter.impl;

import java.util.List;

import com.nakasato.ghstore.domain.productreturn.ReturnStatus;

public class ProductReturnFilter extends ReturnableFilter {

	private List < ReturnStatus > statusList;

	public List < ReturnStatus > getStatusList() {
		return statusList;
	}

	public void setStatusList( List < ReturnStatus > statusList ) {
		this.statusList = statusList;
	}

}
