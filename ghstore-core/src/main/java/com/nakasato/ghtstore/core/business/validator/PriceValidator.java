package com.nakasato.ghtstore.core.business.validator;

import com.nakasato.ghstore.core.IStrategy;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghtstore.core.business.Validator;

/**
 * Responsável por validar o valor do produto
 * @author Rafael
 *
 */
public class PriceValidator extends Validator{

	@Override
	public String validate(AbstractDomainEntity entity) {
		Product p = (Product) entity;
		msg = null;
		if(p.getPrice() < 0){
			msg = "O preço de venda deve ser maior do que 0";
		}
		return msg;
	}
	
}
