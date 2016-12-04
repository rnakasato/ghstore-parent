package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;

import com.nakasato.ghstore.core.dao.impl.ReturnStatusDAO;
import com.nakasato.ghstore.domain.filter.impl.ReturnStatusFilter;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghstore.domain.productreturn.ReturnStatus;
import com.nakasato.ghstore.domain.productreturn.ReturnedItem;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementProductReturn extends Complementor < ProductReturn > {

	@Override
	public String complement( ProductReturn productReturn ) {
		String msg = null;
		try {
			productReturn.setInsertDate( new Date() );
			Double totalValue = 0D;
			for( ReturnedItem rt: productReturn.getReturnedItems() ) {
				rt.setProductReturn( productReturn );
				rt.setInsertDate( new Date() );
				rt.setTotalValue( rt.getProduct().getPrice() * rt.getAmount() );
				totalValue += rt.getTotalValue();
			}
			productReturn.setTotalValue( totalValue );

			ReturnStatusFilter statusFilter = new ReturnStatusFilter();
			statusFilter.setCode( ReturnStatus.COD_AGUARDANDO_RECEBIMENTO );

			ReturnStatusDAO dao = new ReturnStatusDAO();
			ReturnStatus status;
			status = dao.find( statusFilter ).get( 0 );
			productReturn.setStatus( status );

		} catch( Exception e ) {
			msg = "Erro inesperado ao registrar devolução!";
			e.printStackTrace();
		}

		return msg;
	}

}
