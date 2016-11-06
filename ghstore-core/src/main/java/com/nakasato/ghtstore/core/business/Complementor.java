package com.nakasato.ghtstore.core.business;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public abstract class Complementor < T extends AbstractDomainEntity > extends AbstractStrategy < T > {

	@ Override
	public String process( T entity ) {
		return complement( entity );
	}

	public abstract String complement( T entity );

}
