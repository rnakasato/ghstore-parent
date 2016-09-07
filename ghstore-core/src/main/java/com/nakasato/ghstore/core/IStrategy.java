package com.nakasato.ghstore.core;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public interface IStrategy<T extends AbstractDomainEntity> {
	public String process(T entity);
}
