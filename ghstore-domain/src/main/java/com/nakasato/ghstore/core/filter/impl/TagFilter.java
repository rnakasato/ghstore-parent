package com.nakasato.ghstore.core.filter.impl;

import com.nakasato.ghstore.core.filter.Filter;
import com.nakasato.ghstore.domain.Product;

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
