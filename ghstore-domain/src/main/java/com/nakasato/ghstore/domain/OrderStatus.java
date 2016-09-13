package com.nakasato.ghstore.domain;

public class OrderStatus extends AbstractDomainEntity {
	private String statusCode;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}
