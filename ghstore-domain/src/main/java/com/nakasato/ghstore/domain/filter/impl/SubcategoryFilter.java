package com.nakasato.ghstore.domain.filter.impl;

import com.nakasato.ghstore.domain.filter.Filter;
import com.nakasato.ghstore.domain.product.StoreCategory;

public class SubcategoryFilter extends Filter {
	private StoreCategory storeCategory;

	public StoreCategory getStoreCategory() {
		return storeCategory;
	}

	public void setStoreCategory(StoreCategory storeCategory) {
		this.storeCategory = storeCategory;
	}

}
