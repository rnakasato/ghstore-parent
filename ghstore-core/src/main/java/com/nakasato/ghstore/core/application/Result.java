package com.nakasato.ghstore.core.application;

import java.util.List;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public class Result {
	

	private String msg;
	private List<AbstractDomainEntity> entityList;
	/**
	 * M�todo de recupera��o do campo msg
	 *
	 * @return valor do campo msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * Valor de msg atribu�do a msg
	 *
	 * @param msg Atributo da Classe
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * M�todo de recupera��o do campo entityList
	 *
	 * @return valor do campo entityList
	 */
	public List<AbstractDomainEntity> getEntityList() {
		return entityList;
	}
	/**
	 * Valor de entidades atribu�do a entidades
	 *
	 * @param entidades Atributo da Classe
	 */
	public void setEntityList(List<AbstractDomainEntity> entityList) {
		this.entityList = entityList;
	
	}
}