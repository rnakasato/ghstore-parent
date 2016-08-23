package com.nakasato.ghstore.domain;

import java.util.Date;

public abstract class AbstractDomainEntity implements IEntity{
	
	private Integer id;
	private Date insertdate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInsertdate() {
		return insertdate;
	}

	public void setInsertDate(Date insertdate) {
		this.insertdate = insertdate;
	}
	
	public boolean isEmpty(){
		boolean result = false;
		if(id == null){
			result = true;
		}
		return result;
	}

}
