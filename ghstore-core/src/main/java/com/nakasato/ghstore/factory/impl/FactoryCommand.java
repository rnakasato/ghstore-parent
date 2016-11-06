package com.nakasato.ghstore.factory.impl;

import java.util.HashMap;
import java.util.Map;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.command.impl.Command;
import com.nakasato.ghstore.core.command.impl.CommandDelete;
import com.nakasato.ghstore.core.command.impl.CommandFind;
import com.nakasato.ghstore.core.command.impl.CommandFindAll;
import com.nakasato.ghstore.core.command.impl.CommandSave;
import com.nakasato.ghstore.core.command.impl.CommandUpdate;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.Filter;

public class FactoryCommand {
	private static Map < String, Command > commandMap;

	public static Command build( AbstractDomainEntity entity, String operation ) throws ClassNotFoundException {
		if( commandMap ==null ) {
			initCommands();
		}
		Command command =commandMap.get( operation );
		if( command !=null ) {
			command.setEntity( entity );
		} else {
			throw new ClassNotFoundException();
		}
		return command;
	}

	public static Command build( AbstractDomainEntity entity, String operation, int parameter )
			throws ClassNotFoundException {
		if( commandMap ==null ) {
			initCommands();
		}
		Command command =commandMap.get( operation );
		if( command !=null ) {
			if( operation.equals( EOperation.FIND ) ) {
				if( entity instanceof Filter ) {
					Filter filter =( Filter ) entity;
					command.setFilter( filter );
				} else {
					throw new ClassNotFoundException();
				}

			} else {
				command.setEntity( entity );
			}

			command.setParameter( parameter );
		} else {
			throw new ClassNotFoundException();
		}
		return command;
	}

	private static void initCommands() {
		commandMap =new HashMap<>();
		commandMap.put( EOperation.SAVE, new CommandSave() );
		commandMap.put( EOperation.UPDATE, new CommandUpdate() );
		commandMap.put( EOperation.DELETE, new CommandDelete() );
		commandMap.put( EOperation.FIND, new CommandFind() );
		commandMap.put( EOperation.FINDALL, new CommandFindAll() );
	}
}
