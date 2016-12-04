package com.nakasato.ghstore.domain.filter.impl;

import java.util.Date;
import java.util.List;

import com.nakasato.ghstore.domain.filter.Filter;
import com.nakasato.ghstore.domain.productreturn.ReturnStatus;
import com.nakasato.ghstore.domain.user.Customer;

public class ProductReturnFilter extends Filter {
	private String codOrder;
	private Date startDate;
	private Date endDate;
	private String username;
	private Customer customer;

	private List < ReturnStatus > statusList;

	public String getCodOrder() {
		return codOrder;
	}

	public void setCodOrder( String codOrder ) {
		this.codOrder = codOrder;
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

	public List < ReturnStatus > getStatusList() {
		return statusList;
	}

	public void setStatusList( List < ReturnStatus > statusList ) {
		this.statusList = statusList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername( String username ) {
		this.username = username;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer( Customer customer ) {
		this.customer = customer;
	}

}
