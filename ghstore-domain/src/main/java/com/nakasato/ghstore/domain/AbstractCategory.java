package com.nakasato.ghstore.domain;

public abstract class AbstractCategory extends AbstractDomainEntity{
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
