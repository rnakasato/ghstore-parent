package com.nakasato.ghstore.web.mb;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.nakasato.ghstore.core.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.StoreCategory;

@ManagedBean(name = "boardGamesMB")
@ViewScoped
public class BoardGamesMB extends ProductMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PostConstruct
	public void init() {
		setBaseFilter(new ProductFilter());
		filter = (ProductFilter) getBaseFilter();
		filter.getCategory().setDescription(StoreCategory.BOARDGAME);
		super.listProducts();
		StoreCategory storeCategory = new StoreCategory();
		storeCategory.setDescription(StoreCategory.BOARDGAME);
		super.fillSubcategoryByCategory(storeCategory);
	}

	@Override
	public void listProducts() {
		filter.getCategory().setDescription(StoreCategory.BOARDGAME);
		super.listProducts();
	}
	
	public void clearFilters(){
		filter = new ProductFilter();
		filter.getCategory().setDescription(StoreCategory.BOARDGAME);
		super.listProducts();
	}

}
