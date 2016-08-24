package com.nakasato.ghstore.core.command.impl;

import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.IFacade;
import com.nakasato.ghstore.domain.AbstractDomainEntity;

public abstract class Command<T extends AbstractDomainEntity> implements ICommand<T>{
	protected IFacade facade;
	protected AbstractDomainEntity entity;
	protected Integer parameter;

	public IFacade getFacade() {
		return facade;
	}

	public void setFacade(IFacade facade) {
		this.facade = facade;
	}

	public AbstractDomainEntity getEntity() {
		return entity;
	}

	public void setEntity(AbstractDomainEntity entity) {
		this.entity = entity;
	}

	public Integer getParameter() {
		return parameter;
	}

	public void setParameter(Integer parameter) {
		this.parameter = parameter;
	}
		
}
