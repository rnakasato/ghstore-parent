package com.nakasato.ghstore.domain.filter.impl;

import java.util.Date;
import java.util.List;

import com.nakasato.ghstore.domain.filter.Filter;
import com.nakasato.ghstore.domain.productexchange.ExchangeStatus;
import com.nakasato.ghstore.domain.user.Customer;

public class ProductExchangeFilter extends ReturnableFilter {
	private List < ExchangeStatus > statusList;

	public List < ExchangeStatus > getStatusList() {
		return statusList;
	}

	public void setStatusList( List < ExchangeStatus > statusList ) {
		this.statusList = statusList;
	}

}
