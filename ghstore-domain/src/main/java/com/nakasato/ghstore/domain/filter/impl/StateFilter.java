package com.nakasato.ghstore.domain.filter.impl;

import com.nakasato.ghstore.domain.filter.Filter;

/**
 * 
 * @author rafae Descrição:
 */
public class StateFilter extends Filter {

	private String acronym;
	private String name;

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym( String acronym ) {
		this.acronym = acronym;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

}
