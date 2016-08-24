package com.nakasato.ghstore.domain;

import java.util.List;

/**
 * 
 * @author rafae Descrição:
 */
public class Tag extends AbstractDomainEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;
	private List<Product> productList;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

}
