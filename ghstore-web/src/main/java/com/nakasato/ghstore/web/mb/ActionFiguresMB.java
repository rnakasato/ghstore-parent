package com.nakasato.ghstore.web.mb;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.nakasato.ghstore.core.filter.impl.ProductFilter;
import com.nakasato.ghstore.core.table.enums.EStoreCategory;
import com.nakasato.ghstore.domain.StoreCategory;

@ManagedBean(name = "actionFiguresMB")
@ViewScoped
public class ActionFiguresMB extends ProductMB {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
//		setPageUrl("/clientuser/actionFigures.xhtml");
		setBaseFilter(new ProductFilter());
		filter = (ProductFilter) getBaseFilter();
		filter.getCategory().setDescription(StoreCategory.ACTION_FIGURE);
		super.listProducts();
		StoreCategory storeCategory = new StoreCategory();
		storeCategory.setDescription(StoreCategory.ACTION_FIGURE);
		super.fillSubcategoryByCategory(storeCategory);
	}

	@Override
	public void listProducts() {
		filter.getCategory().setDescription(StoreCategory.ACTION_FIGURE);
		super.listProducts();
	}
	
	public void clearFilters(){
		filter = new ProductFilter();
		filter.getCategory().setDescription(StoreCategory.ACTION_FIGURE);
		super.listProducts();
	}
}
