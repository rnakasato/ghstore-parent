package com.nakasato.ghtstore.core.business.complementor;

import java.util.List;

import com.nakasato.ghstore.core.dao.impl.PromotionDAO;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementPromotionStatus extends Complementor < Promotion > {

	@Override
	public String complement( Promotion promotion ) {
		String msg = null;
		try {
			PromotionDAO dao = new PromotionDAO();
			dao.setSession( SessionThreadLocal.getSession() );
			if( promotion.getActive() != null && promotion.getActive() ) {
				List < Product > productList = promotion.getProductList();
				for( Product p: productList ) {
					Promotion activePromotion = p.getActivePromotion();
					if( activePromotion != null && activePromotion != promotion) {
						activePromotion.setActive( false );
						dao.update( activePromotion );
					}
				}
			}
		} catch( Exception e ) {
			msg = "Erro ao complementar dados da promoção";
			e.printStackTrace();
		}

		return msg;
	}

}
