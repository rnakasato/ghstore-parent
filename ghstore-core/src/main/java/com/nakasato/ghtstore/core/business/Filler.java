package com.nakasato.ghtstore.core.business;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public abstract class Filler < T extends AbstractDomainEntity > extends AbstractStrategy < T > {

	@ Override
	public String process( T entity ) {
		return fill( entity );
	}

	public abstract String fill( T entity );
}
