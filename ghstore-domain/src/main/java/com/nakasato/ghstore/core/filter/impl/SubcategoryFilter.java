package com.nakasato.ghstore.core.filter.impl;

import com.nakasato.ghstore.core.filter.Filter;
import com.nakasato.ghstore.domain.StoreCategory;

public class SubcategoryFilter extends Filter {
	private StoreCategory storeCategory;

	public StoreCategory getStoreCategory() {
		return storeCategory;
	}

	public void setStoreCategory(StoreCategory storeCategory) {
		this.storeCategory = storeCategory;
	}

}
