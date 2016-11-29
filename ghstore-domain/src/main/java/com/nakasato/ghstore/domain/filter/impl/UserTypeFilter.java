package com.nakasato.ghstore.domain.filter.impl;

import java.util.List;

import com.nakasato.ghstore.domain.filter.Filter;

public class UserTypeFilter extends Filter {
	private List < String > codeList;

	public List < String > getCodeList() {
		return codeList;
	}

	public void setCodeList( List < String > codeList ) {
		this.codeList = codeList;
	}

}
