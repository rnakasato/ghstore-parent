package com.nakasato.ghstore.domain.user;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public class City extends AbstractDomainEntity {
	private String name;
	private State state;
	private String uf;

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name =name;
	}

	public State getState() {
		return state;
	}

	public void setState( State state ) {
		this.state =state;
	}

	public String getUf() {
		return uf;
	}

	public void setUf( String uf ) {
		this.uf =uf;
	}

	@ Override
	public boolean isEmpty() {
		boolean result =false;
		if( StringUtils.isEmpty( name ) &&super.isEmpty() ) {
			result =true;
		}
		return result;
	}
}
