package com.nakasato.ghtstore.core.business;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public abstract class Filler extends AbstractStrategy{
	
	@Override
	public String process(AbstractDomainEntity entity) {
		return fill(entity);
	}
	
	public abstract String fill(AbstractDomainEntity entity);
}
