package com.nakasato.ghstore.core;

import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.AbstractDomainEntity;

public interface ICommand<T extends AbstractDomainEntity> {
	public Result<T> execute();
}
