package com.nakasato.ghstore.domain;

import org.apache.commons.lang3.StringUtils;

public class State extends AbstractDomainEntity {
	private String name;
	private String acronym;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	@Override
	public boolean isEmpty() {
		boolean result = false;
		if (StringUtils.isEmpty(name) && super.isEmpty()) {
			result = true;
		}
		return false;
	}

}
