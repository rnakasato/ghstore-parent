package com.nakasato.ghstore.web.mb.product;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.product.StoreCategory;

@ManagedBean( name = "accessoryMB" )
@ViewScoped
public class AccessoryMB extends ProductMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		setBaseFilter( new ProductFilter() );
		filter = ( ProductFilter ) getBaseFilter();
		filter.getCategory().setDescription( StoreCategory.ACCESSORY );
		super.listProducts();
		StoreCategory storeCategory = new StoreCategory();
		storeCategory.setDescription( StoreCategory.ACCESSORY );
		super.fillSubcategoryByCategory( storeCategory );

	}

	@Override
	public void listProducts() {
		filter.getCategory().setDescription( StoreCategory.ACCESSORY );
		super.listProducts();
	}

	public void clearFilters() {
		filter = new ProductFilter();
		filter.getCategory().setDescription( StoreCategory.ACCESSORY );
		super.listProducts();
	}

	@Override
	public void fillPriceRange() {
		switch( priceRangeOption ) {
			case 1:
				filter.setInitialValue( 10D );
				filter.setFinalValue( 20D );
				break;
			case 2:
				filter.setInitialValue( 20D );
				filter.setFinalValue( 30D );
				break;
			case 3:				
				filter.setInitialValue( 30D );
				filter.setFinalValue( 40D );
				break;	
			case 4:				
				filter.setInitialValue( 40D );
				filter.setFinalValue( 50D );
				break;	
			case 5:				
				break;
			default:
				filter.setInitialValue( null );
				filter.setFinalValue( null );
				break;
		}
	}
}
