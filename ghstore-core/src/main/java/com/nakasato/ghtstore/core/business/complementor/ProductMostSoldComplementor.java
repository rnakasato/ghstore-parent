package com.nakasato.ghtstore.core.business.complementor;

import java.util.List;

import com.nakasato.ghstore.core.dao.impl.MostSoldProductDAO;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.domain.carrier.HomeProductsCarrier;
import com.nakasato.ghstore.domain.filter.impl.MostSoldProductFilter;
import com.nakasato.ghstore.domain.product.group.MostSoldProduct;
import com.nakasato.ghtstore.core.business.Complementor;

public class ProductMostSoldComplementor extends Complementor < HomeProductsCarrier > {

	@Override
	public String complement( HomeProductsCarrier carrier ) {
		msg = null;
		try {
			MostSoldProductFilter filter = new MostSoldProductFilter();
			filter.setMaxResults( HomeProductsCarrier.MAX_RESULTS );

			MostSoldProductDAO dao = new MostSoldProductDAO();
			dao.setSession( SessionThreadLocal.getSession() );
			List < MostSoldProduct > msList = dao.find( filter );
			carrier.setMostSold( msList );

		} catch( Exception e ) {
			msg = "Erro inesperado";
			e.printStackTrace();
		}

		return msg;
	}

}
