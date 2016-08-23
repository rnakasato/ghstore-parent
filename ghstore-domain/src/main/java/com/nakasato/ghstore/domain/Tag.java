package com.nakasato.ghstore.domain;

/**
 * 
 * @author rafae
 * Descrição:
 */
public class Tag extends AbstractDomainEntity {
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
