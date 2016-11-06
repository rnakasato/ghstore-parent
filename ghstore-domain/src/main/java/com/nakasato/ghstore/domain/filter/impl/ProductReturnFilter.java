package com.nakasato.ghstore.domain.filter.impl;

import java.util.Date;

import com.nakasato.ghstore.domain.filter.Filter;

public class ProductReturnFilter extends Filter {
	private String codOrder;
	private Date startDate;
	private Date endDate;

	public String getCodOrder() {
		return codOrder;
	}

	public void setCodOrder( String codOrder ) {
		this.codOrder =codOrder;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate( Date startDate ) {
		this.startDate =startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate( Date endDate ) {
		this.endDate =endDate;
	}

}
