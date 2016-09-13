package com.nakasato.ghstore.core.command.impl;

import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.impl.Facade;
import com.nakasato.ghstore.domain.AbstractDomainEntity;

public class CommandFindAll<T extends AbstractDomainEntity> extends Command<T>{

	@Override
	public Result<T> execute() {
		facade = new Facade<T>();
		return facade.findAll(entity);
	}

}
