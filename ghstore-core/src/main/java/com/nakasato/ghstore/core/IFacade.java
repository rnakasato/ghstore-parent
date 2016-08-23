package com.nakasato.ghstore.core;

import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.AbstractDomainEntity;

public interface IFacade {
	public Result save(AbstractDomainEntity entity);
	public Result update(AbstractDomainEntity entity);
	public Result delete(AbstractDomainEntity entity);
	public Result find(AbstractDomainEntity entity);
	public Result findAll(AbstractDomainEntity entity);
	public Result view(AbstractDomainEntity entity);
	public Result doRules(AbstractDomainEntity entity, Integer parameter);
}
