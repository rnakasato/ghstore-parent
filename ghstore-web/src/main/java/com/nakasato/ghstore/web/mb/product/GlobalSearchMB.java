package com.nakasato.ghstore.web.mb.product;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

@ManagedBean( name = "globalSearchMB" )
@ViewScoped
public class GlobalSearchMB extends ProductMB {

	private String name;

	@PostConstruct
	public void init() {
		filter = new ProductFilter();
		filter.setCategory( null);
		filter.setSubcategory( null );

		super.initStoreCategory();
		super.initProductOrderType();
		isAscendant = true;

		String productName = ( String ) FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.get( "productName" );

		try {
			if( ! StringUtils.isEmpty( productName ) ) {
				ProductFilter filter = new ProductFilter();
				filter.setName( productName );
				ICommand commandFind;
				commandFind = FactoryCommand.build( filter, EOperation.FIND );
				// TODO Auto-generated catch block
				productList = commandFind.execute().getEntityList();

				FacesContext.getCurrentInstance().getExternalContext().getFlash().put( "productName", null );
			}
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}

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
				filter.setFinalValue( 150D );
				break;
			case 3:
				filter.setInitialValue( 150D );
				filter.setFinalValue( 200D );
				break;
			case 4:
				filter.setInitialValue( 200D );
				filter.setFinalValue( 250D );
				break;
			case 5:
				break;
			default:
				filter.setInitialValue( null );
				filter.setFinalValue( null );
				break;
		}
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

}
