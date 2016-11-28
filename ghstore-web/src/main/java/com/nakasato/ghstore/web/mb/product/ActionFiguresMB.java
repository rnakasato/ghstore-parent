package com.nakasato.ghstore.web.mb.product;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.product.StoreCategory;

@ManagedBean( name = "actionFiguresMB" )
@ViewScoped
public class ActionFiguresMB extends ProductMB {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		// setPageUrl("/clientuser/actionFigures.xhtml");
		setBaseFilter( new ProductFilter() );
		filter = ( ProductFilter ) getBaseFilter();
		filter.getCategory().setDescription( StoreCategory.ACTION_FIGURE );
		super.listProducts();
		StoreCategory storeCategory = new StoreCategory();
		storeCategory.setDescription( StoreCategory.ACTION_FIGURE );
		super.fillSubcategoryByCategory( storeCategory );
	}

	@Override
	public void listProducts() {
		filter.getCategory().setDescription( StoreCategory.ACTION_FIGURE );
		super.listProducts();
	}

	public void clearFilters() {
		filter = new ProductFilter();
		filter.getCategory().setDescription( StoreCategory.ACTION_FIGURE );
		super.listProducts();
	}

	@Override
	public void fillPriceRange() {
		switch( priceRangeOption ) {
			case 1:
				filter.setInitialValue( 10D );
				filter.setFinalValue( 100D );
				break;
			case 2:
				filter.setInitialValue( 100D );
				filter.setFinalValue( 200D );
				break;
			case 3:				
				filter.setInitialValue( 200D );
				filter.setFinalValue( 300D );
				break;	
			case 4:				
				filter.setInitialValue( 300D );
				filter.setFinalValue( 400D );
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
