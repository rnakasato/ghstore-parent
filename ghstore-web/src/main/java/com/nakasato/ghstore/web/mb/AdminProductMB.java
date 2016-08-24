package com.nakasato.ghstore.web.mb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.nakasato.core.util.enums.EComparator;
import com.nakasato.ghstore.core.IFacade;
import com.nakasato.ghstore.core.filter.impl.ProductFilter;
import com.nakasato.ghstore.core.impl.Facade;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.core.util.OrderByType;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.StoreCategory;

@ManagedBean (name="adminProductMB")
public class AdminProductMB extends ProductMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PostConstruct
	public void init() {
		setBaseFilter(new ProductFilter());
		filter = (ProductFilter) getBaseFilter();
		IFacade f = new Facade();
		product = (Product) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("product");
		if (product != null) {
			name = product.getName();
			category = product.getStoreCategory().getDescription();
			subcategory = product.getSubcategory().getDescription();
			price = product.getPrice();
			image = product.getImage();
			description = product.getDescription();
			status = product.getStatus();
			tagList = product.getTagList();
		}
		StoreCategory storeCategory = new StoreCategory();
		List<AbstractDomainEntity> ctList = f.findAll(storeCategory).getEntityList();
		if (!ListUtils.isListEmpty(ctList)) {
			categoryList = new ArrayList<>();
			for (AbstractDomainEntity e : ctList) {
				StoreCategory s = (StoreCategory) e;
				categoryList.add(s);
			}
		}
		orderTypeList = new ArrayList<>();
		orderTypeList.add(new OrderByType(EComparator.PRODUCT_NAME, "Nome"));
		orderTypeList.add(new OrderByType(EComparator.PRODUCT_CATEGORY, "Categoria"));
		orderTypeList.add(new OrderByType(EComparator.PRODUCT_PRICE, "Preço"));
		orderTypeList.add(new OrderByType(EComparator.PRODUCT_STATUS, "Status"));
		orderTypeList.add(new OrderByType(EComparator.PRODUCT_STOCK, "Estoque"));
		// orderTypeList.add(new OrderByType(EComparator.PRODUCT_SELL, "Nome"));
		listProducts();
		
	}

}
