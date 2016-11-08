package com.nakasato.ghstore.core.application;

import java.util.List;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public class Result < T extends AbstractDomainEntity > {

	private String msg;
	private List < T > entityList;

	/**
	 * Método de recuperação do campo msg
	 *
	 * @return valor do campo msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Valor de msg atribuído a msg
	 *
	 * @param msg
	 *            Atributo da Classe
	 */
	public void setMsg( String msg ) {
		this.msg = msg;
	}

	/**
	 * Método de recuperação do campo entityList
	 *
	 * @return valor do campo entityList
	 */
	public List < T > getEntityList() {
		return entityList;
	}

	/**
	 * Valor de entidades atribuído a entidades
	 *
	 * @param entidades
	 *            Atributo da Classe
	 */
	public void setEntityList( List < T > entityList ) {
		this.entityList = entityList;

	}
}