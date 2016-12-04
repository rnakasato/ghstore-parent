package com.nakasato.ghtstore.core.business.validator;

import java.util.Date;

import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghtstore.core.business.Validator;

public class ProductUpdateValidator extends Validator < Promotion > {

	@Override
	public String validate( Promotion promotion ) {
		msg = null;
		if( ( ( promotion.getCancel() == null || ! promotion.getCancel() )
				&& ( promotion.getDisable() == null || ! promotion.getDisable() ) )
				&& promotion.getEndDate().before( new Date() ) ) {
			msg = "Uma promoção encerrada não pode ser alterada";
		}
		return msg;
	}

}
