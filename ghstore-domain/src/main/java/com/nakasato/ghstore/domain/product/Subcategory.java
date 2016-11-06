package com.nakasato.ghstore.domain.product;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class Subcategory extends AbstractCategory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8119304615902369918L;
	private StoreCategory storeCategory;

	public StoreCategory getStoreCategory() {
		return storeCategory;
	}

	public void setStoreCategory( StoreCategory storeCategory ) {
		this.storeCategory =storeCategory;
	}

	@ Override
	public boolean isEmpty() {
		boolean isEmpty =false;
		if( this.getId() ==null &&StringUtils.isEmpty( this.getDescription() ) &&this.getStoreCategory().isEmpty() ) {
			isEmpty =true;
		}
		return isEmpty;
	}

}
