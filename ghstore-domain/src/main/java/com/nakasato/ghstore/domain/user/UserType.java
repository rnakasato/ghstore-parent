package com.nakasato.ghstore.domain.user;

import com.nakasato.ghstore.domain.DomainSpecificEntity;

public class UserType extends DomainSpecificEntity {
	public static final String COD_CUSTOMER = "CST";
	public static final String COD_OPERATOR = "OPR";
	public static final String COD_ADMINISTRATOR = "ADM";

	private String name;

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

}
