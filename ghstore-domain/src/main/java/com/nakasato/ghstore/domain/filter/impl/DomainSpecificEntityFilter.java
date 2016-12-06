package com.nakasato.ghstore.domain.filter.impl;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public class DomainSpecificEntityFilter extends AbstractDomainEntity {
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode( String code ) {
		this.code = code;
	}

}
