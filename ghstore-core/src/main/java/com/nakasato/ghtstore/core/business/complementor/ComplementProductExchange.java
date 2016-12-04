package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;

import com.nakasato.ghstore.core.dao.impl.ExchangeStatusDAO;
import com.nakasato.ghstore.domain.filter.impl.ExchangeStatusFilter;
import com.nakasato.ghstore.domain.productexchange.ExchangeItem;
import com.nakasato.ghstore.domain.productexchange.ExchangeStatus;
import com.nakasato.ghstore.domain.productexchange.ProductExchange;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementProductExchange extends Complementor < ProductExchange > {

	@Override
	public String complement( ProductExchange productExchange ) {
		String msg = null;
		try {
			productExchange.setInsertDate( new Date() );
			Double totalValue = 0D;
			for( ExchangeItem ex: productExchange.getExchangeItems() ) {
				ex.setProductExchange( productExchange );
				ex.setInsertDate( new Date() );
				ex.setTotalValue( ex.getProduct().getPrice() * ex.getAmount() );
				totalValue += ex.getTotalValue();
			}
			productExchange.setTotalValue( totalValue );

			ExchangeStatusFilter statusFilter = new ExchangeStatusFilter();
			statusFilter.setCode( ExchangeStatus.COD_AGUARDANDO_RECEBIMENTO );

			ExchangeStatusDAO dao = new ExchangeStatusDAO();
			ExchangeStatus status;
			status = dao.find( statusFilter ).get( 0 );
			productExchange.setStatus( status );

		} catch( Exception e ) {
			msg = "Ocorreu um erro inesperado ao registrar troca!";
			e.printStackTrace();
		}

		return msg;
	}

}
