package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;

import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghtstore.core.business.Validator;

public class ComplementPromotionCancel extends Validator < Promotion > {

	@Override
	public String validate( Promotion promotion ) {
		if( promotion.getCancel() != null && promotion.getCancel() ) {
			promotion.setEndDate( new Date() );
		}
		return null;
	}

}
