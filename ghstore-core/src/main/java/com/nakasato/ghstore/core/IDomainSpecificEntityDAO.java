package com.nakasato.ghstore.core;

import com.nakasato.ghstore.domain.DomainSpecificEntity;

public interface IDomainSpecificEntityDAO < T extends DomainSpecificEntity > extends IDAO < T > {
	public T findByCode( T entity );
}
