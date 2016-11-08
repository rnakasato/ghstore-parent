package com.nakasato.ghstore.core.util;

import java.io.Serializable;

public class OrderByType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = - 8166856040663113474L;
	private Integer id;
	private String description;

	public OrderByType( Integer id, String description ) {
		this.id = id;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId( Integer id ) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

}
