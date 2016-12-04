package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;
import java.util.List;

import com.nakasato.ghstore.core.dao.impl.ProductDAO;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.productexchange.ExchangeItem;
import com.nakasato.ghstore.domain.productexchange.ExchangeStatus;
import com.nakasato.ghstore.domain.productexchange.ProductExchange;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementProductExchangeUpdate extends Complementor < ProductExchange > {

	@Override
	public String complement( ProductExchange productExchange ) {

		if( productExchange.getStatus() != null
				&& productExchange.getStatus().getCode().equals( ExchangeStatus.COD_ACEITO ) ) {
			try {
				ProductDAO dao = new ProductDAO();
				dao.setSession( SessionThreadLocal.getSession() );
				List < ExchangeItem > exchangeList = productExchange.getExchangeItems();
				for( ExchangeItem ex: exchangeList ) {
					Product p = ex.getProduct();
					Integer stock = p.getStock();
					stock -= ex.getAmount();
					dao.update( p );
				}

			} catch( Exception e ) {
				e.printStackTrace();
			}

		} else if( productExchange.getStatus() != null
				&& productExchange.getStatus().getCode().equals( ExchangeStatus.COD_RECEBIDO ) ) {
			productExchange.setDeliverDate( new Date() );
		} else if( productExchange.getStatus() != null
				&& productExchange.getStatus().getCode().equals( ExchangeStatus.COD_ENTREGUE ) ) {
			productExchange.setReturnedDate( new Date() );
		}

		return null;
	}

}
