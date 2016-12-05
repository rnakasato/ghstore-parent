package com.nakasato.ghstore.web.mb.product;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.product.Product;

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
		setAscendant( true );

		super.initStoreCategory();
		super.initProductOrderType();

		product = ( Product ) FacesContext.getCurrentInstance().getExternalContext().getFlash().get( "product" );
		if( product != null ) {
			subcategory = product.getSubcategory().getDescription();
		} else {
			product = new Product();
		}

	}

	@Override
	public void fillPriceRange() {
		// TODO Auto-generated method stub

	}

}
