package com.nakasato.ghstore.core.command.impl;

import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.impl.Facade;

public class CommandSave extends Command{

	@Override
	public Result execute() {
		facade = new Facade();
		return facade.save(entity);
	}
	

}
