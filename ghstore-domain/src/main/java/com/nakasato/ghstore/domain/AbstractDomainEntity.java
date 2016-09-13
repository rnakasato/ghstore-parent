package com.nakasato.ghstore.domain;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractDomainEntity implements IEntity, Serializable {

	private Integer id;
	private Date insertDate;
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEmpty() {
		boolean result = false;
		if (id == null) {
			result = true;
		}
		return result;
	}

}
