package com.nakasato.ghstore.domain;

import java.util.List;

/**
 * 
 * @author rafae Descri��o:
 */
public class Tag extends AbstractDomainEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Product> productList;

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

}
