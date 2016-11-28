package com.nakasato.ghstore.web.mb.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.nakasato.core.util.enums.EComparator;
import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.core.util.OrderByType;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

@ManagedBean( name = "adminProductMB" )
@ViewScoped
public class AdminProductMB extends ProductMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		setBaseFilter( new ProductFilter() );
		filter = ( ProductFilter ) getBaseFilter();

		product = ( Product ) FacesContext.getCurrentInstance().getExternalContext().getFlash().get( "product" );
		if( product != null ) {
			name = product.getName();
			category = product.getStoreCategory().getDescription();
			subcategory = product.getSubcategory().getDescription();
			price = product.getPrice();
			image = product.getImage();
			description = product.getDescription();
			status = product.getStatus();
			tagList = product.getTagList();
		}
		
		super.initStoreCategory();
		super.initProductOrderType();
	}

	@Override
	public void fillPriceRange() {
		// TODO Auto-generated method stub

	}

}
