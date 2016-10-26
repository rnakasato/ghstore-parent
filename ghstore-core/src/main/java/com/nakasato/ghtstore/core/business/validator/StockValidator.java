package com.nakasato.ghtstore.core.business.validator;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghtstore.core.business.Validator;

/**
 * Respons�vel por validar se o estoque � menor do que zero
 * @author Rafael
 *
 */
public class StockValidator extends Validator{

	@Override
	public String validate(AbstractDomainEntity entity) {
		Product p = (Product) entity;
		msg = null;
		if(p.getStock() < 0){
			msg = "N�o � permitido estoque com valor negativo";
		}		
		return msg;
	}

}
