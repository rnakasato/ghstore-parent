package com.nakasato.ghstore.core.command.impl;

import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.impl.Facade;

public class CommandDoRule extends Command{
			
	@Override
	public Result execute() {
		Result result = null;
		if(parameter != null){
			facade = new Facade();
			result = facade.doRules(entity, parameter);
		}
		return result;
	}
	
}
