package com.nakasato.ghtstore.core.business.validator;

import com.nakasato.ghstore.domain.filter.impl.PromotionFilter;
import com.nakasato.ghtstore.core.business.Validator;

public class PromotionFilterEndDateValidator extends Validator < PromotionFilter > {

	@Override
	public String validate( PromotionFilter filter ) {
		msg = null;
		if( ( filter.getEndDateBegin() != null && filter.getEndDateEnd() == null )
				|| ( filter.getEndDateEnd() != null && filter.getEndDateBegin() == null ) ) {
			msg = "Para utilizar o filtro de intervalo de data de fim da promoção deve ser definido o valor inicial e final ";
		} else if(  filter.getEndDateBegin() != null && filter.getEndDateEnd() != null 
				&& filter.getEndDateEnd().after( filter.getEndDateBegin() )  ) {
			msg = "O valor inicial do da data de fim da promoção deve ser inferior ao valor final";
		}

		return msg;
	}

}
