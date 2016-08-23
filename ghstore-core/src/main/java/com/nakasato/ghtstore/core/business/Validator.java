package com.nakasato.ghtstore.core.business;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public abstract class Validator extends AbstractStrategy{
	
	@Override
	public String process(AbstractDomainEntity entity) {
		return validate(entity);
	}
	
	public abstract String validate(AbstractDomainEntity entity);
}
