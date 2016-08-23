package com.nakasato.ghstore.relation;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public abstract class Relation extends AbstractDomainEntity{
	private Integer idFirstTable;
	private Integer idSecondTable;

	public Integer getIdFirstTable() {
		return idFirstTable;
	}

	public void setIdFirstTable(Integer idFirstTable) {
		this.idFirstTable = idFirstTable;
	}

	public Integer getIdSecondTable() {
		return idSecondTable;
	}

	public void setIdSecondTable(Integer idSecondTable) {
		this.idSecondTable = idSecondTable;
	}

}
