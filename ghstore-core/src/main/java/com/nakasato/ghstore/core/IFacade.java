package com.nakasato.ghstore.core;

import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.AbstractDomainEntity;

public interface IFacade < T extends AbstractDomainEntity > {
	public Result < T > save( T entity );

	public Result < T > update( T entity );

	public Result < T > delete( T entity );

	public Result < T > find( T filter );

	public Result < T > findAll( T entity );

	public Result < T > view( T entity );

	public Result < T > doRules( T entity, String ruleName );
}
