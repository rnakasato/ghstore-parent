package com.nakasato.ghstore.domain.filter.impl;

import com.nakasato.ghstore.domain.filter.Filter;

/**
 * 
 * @author rafae Descrição:
 */
public class TagFilter extends Filter {
	private static final long serialVersionUID = 1L;

	private Integer productId;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}
