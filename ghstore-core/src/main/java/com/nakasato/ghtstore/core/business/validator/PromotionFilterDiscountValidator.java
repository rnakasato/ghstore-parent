package com.nakasato.ghtstore.core.business.validator;

import com.nakasato.ghstore.domain.filter.impl.PromotionFilter;
import com.nakasato.ghtstore.core.business.Validator;

/**
 * 
 * @author rafae Descrição: Valida se o início e fim do filtro de desconto estão
 *         preenchidos <br>
 *         Valida se o valor final é superior ao valor inicial do desconto <br>
 *         Valida se o valor do do desconto é positivo e inferior a 100  <br>
 */
public class PromotionFilterDiscountValidator extends Validator < PromotionFilter > {

	@Override
	public String validate( PromotionFilter filter ) {
		msg = null;
		if( ( filter.getStartDiscount() != null && filter.getEndDiscount() == null )
				|| ( filter.getEndDiscount() != null && filter.getStartDiscount() == null ) ) {
			msg = "Para utilizar o filtro de intervalo de desconto deve ser definido o valor inicial e final ";
		} else if( filter.getStartDiscount() != null && filter.getStartDiscount() != null
				&& filter.getEndDiscount() > filter.getStartDiscount() ) {
			msg = "O valor inicial do desconto deve ser inferior ao valor final";
		} else if( ( filter.getStartDiscount() != null
				&& ( filter.getStartDiscount() <= 0D || filter.getStartDiscount() >= 100 ) )
				|| filter.getEndDiscount() != null
						&& ( filter.getEndDiscount() <= 0D || filter.getEndDiscount() >= 100 ) ) {
			msg = "O valor do desconto deve ser acima de 1 e inferior a 100";
		}

		return msg;
	}

}
