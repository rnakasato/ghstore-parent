package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;

import com.nakasato.ghstore.domain.productexchange.ExchangeItem;
import com.nakasato.ghstore.domain.productexchange.ProductExchange;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementProductExchange extends Complementor < ProductExchange > {

	@ Override
	public String complement( ProductExchange productExchange ) {
		productExchange.setInsertDate( new Date() );
		Double totalValue =0D;
		for( ExchangeItem ex: productExchange.getExchangeItems() ) {
			ex.setProductExchange( productExchange );
			ex.setInsertDate( new Date() );
			ex.setTotalValue( ex.getProduct().getPrice() *ex.getAmount() );
			totalValue +=ex.getTotalValue();
		}
		productExchange.setTotalValue( totalValue );

		return null;
	}

}
