package com.nakasato.ghstore.core.command.impl;

import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.impl.Facade;
import com.nakasato.ghstore.domain.AbstractDomainEntity;

public class CommandDoRule<T extends AbstractDomainEntity> extends Command<T>{
			
	@Override
	public Result<T> execute() {
		Result<T> result = null;
		if(parameter != null){
			facade = new Facade<T>();
			result = facade.doRules(entity, parameter);
		}
		return result;
	}
	
}
