package com.nakasato.ghtstore.core.business.complementor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nakasato.ghstore.core.dao.impl.MostSoldProductDAO;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.domain.carrier.HomeProductsCarrier;
import com.nakasato.ghstore.domain.filter.impl.MostSoldProductFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.domain.product.group.MostSoldProduct;
import com.nakasato.ghtstore.core.business.Complementor;

public class ProductRecommendComplementor extends Complementor < HomeProductsCarrier > {

	@Override
	public String complement( HomeProductsCarrier carrier ) {
		msg = null;
		try {
			if( carrier.getCustomer() != null ) {
				MostSoldProductFilter filter = new MostSoldProductFilter();
				filter.setMaxResults( HomeProductsCarrier.MAX_RESULTS );
				filter.setCustomer( carrier.getCustomer() );

				// Carrega produtos mais vendidos para o usuário
				MostSoldProductDAO dao = new MostSoldProductDAO();
				dao.setSession( SessionThreadLocal.getSession() );
				List < MostSoldProduct > msList = dao.find( filter );
				List < Product > ownedProductList = new ArrayList<>();

				// Verifica quais categorias o cliente mais comprou
				Set < StoreCategory > categorySet = new HashSet < StoreCategory >();
				for( MostSoldProduct mostSoldProduct: msList ) {
					categorySet.add( mostSoldProduct.getProduct().getStoreCategory() );
					ownedProductList.add( mostSoldProduct.getProduct() );
				}

				List < StoreCategory > categoryList = new ArrayList<>( categorySet );
				filter.setCategoryList( categoryList );
				filter.setNotInList( ownedProductList );

				// Carrega produtos mais vendidos nas categorias mais vendidas
				// para
				// o cliente que não seja um dos que já possui
				filter.setCustomer( null );

				List < MostSoldProduct > recommendedList = dao.find( filter );
				carrier.setRecommendedProducts( recommendedList );
			}

		} catch( Exception e ) {
			msg = "Erro inesperado";
			e.printStackTrace();
		}

		return msg;

	}

}
