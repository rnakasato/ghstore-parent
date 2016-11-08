package com.nakasato.ghstore.domain.filter.impl;

import java.util.Date;

import com.nakasato.ghstore.domain.filter.Filter;

public class OrderFilter extends Filter {
	private String username;
	private String statusCode;
	private Date startDate;
	private Date endDate;
	private String orderCode;
	private String transactionCode;

	public String getUsername() {
		return username;
	}

	public void setUsername( String username ) {
		this.username = username;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode( String statusCode ) {
		this.statusCode = statusCode;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode( String orderCode ) {
		this.orderCode = orderCode;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode( String transactionCode ) {
		this.transactionCode = transactionCode;
	}

}
