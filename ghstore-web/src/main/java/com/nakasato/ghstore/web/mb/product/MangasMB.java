package com.nakasato.ghstore.web.mb.product;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.product.StoreCategory;

@ManagedBean(name = "mangasMB")
@ViewScoped
public class MangasMB extends ProductMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PostConstruct
	public void init() {
		setBaseFilter(new ProductFilter());
		filter = (ProductFilter) getBaseFilter();
		filter.getCategory().setDescription(StoreCategory.MANGA);
		super.listProducts();
		StoreCategory storeCategory = new StoreCategory();
		storeCategory.setDescription(StoreCategory.MANGA);
		super.fillSubcategoryByCategory(storeCategory);
	}

	@Override
	public void listProducts() {
		filter.getCategory().setDescription(StoreCategory.MANGA);
		super.listProducts();
	}
	
	public void clearFilters(){
		filter = new ProductFilter();
		filter.getCategory().setDescription(StoreCategory.MANGA);
		super.listProducts();
	}

}
