package com.nakasato.ghstore.core;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public interface IStrategy {
	public String process(AbstractDomainEntity entity);
}
