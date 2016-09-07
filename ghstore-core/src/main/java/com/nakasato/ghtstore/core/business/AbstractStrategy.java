package com.nakasato.ghtstore.core.business;

import com.nakasato.ghstore.core.IStrategy;
import com.nakasato.ghstore.domain.AbstractDomainEntity;

public abstract class AbstractStrategy<T extends AbstractDomainEntity> implements IStrategy<T>{
	protected String msg;
}
