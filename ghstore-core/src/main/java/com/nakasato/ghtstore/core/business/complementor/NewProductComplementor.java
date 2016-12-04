package com.nakasato.ghtstore.core.business.complementor;

import java.util.List;

import com.nakasato.ghstore.core.dao.impl.ProductDAO;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.domain.carrier.HomeProductsCarrier;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghtstore.core.business.Complementor;

public class NewProductComplementor extends Complementor < HomeProductsCarrier > {

	@Override
	public String complement( HomeProductsCarrier carrier ) {
		msg = null;
		try {
			ProductFilter filter = new ProductFilter();
			filter.setNewProducts( true );
			filter.setMaxResults( HomeProductsCarrier.MAX_RESULTS );

			ProductDAO dao = new ProductDAO();
			dao.setSession( SessionThreadLocal.getSession() );

			List < Product > productList = dao.find( filter );
			carrier.setNewProducts( productList );
		} catch( Exception e ) {
			msg = "Erro inesperado";
			e.printStackTrace();
		}

		return msg;
	}

}
