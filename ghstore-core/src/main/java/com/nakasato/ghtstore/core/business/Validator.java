package com.nakasato.ghtstore.core.business;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public abstract class Validator<T extends AbstractDomainEntity> extends AbstractStrategy<T>{
	
	@Override
	public String process(T entity) {
		return validate(entity);
	}
	
	public abstract String validate(T entity);
}
