package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;
import java.util.List;

import com.nakasato.ghstore.core.dao.impl.ProductDAO;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghstore.domain.productreturn.ReturnStatus;
import com.nakasato.ghstore.domain.productreturn.ReturnedItem;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementProductReturnUpdate extends Complementor < ProductReturn > {

	@Override
	public String complement( ProductReturn productReturn ) {
		if( productReturn.getStatus() != null
				&& productReturn.getStatus().getCode().equals( ReturnStatus.COD_ACEITO ) ) {
			try {
				ProductDAO dao = new ProductDAO();
				dao.setSession( SessionThreadLocal.getSession() );
				List < ReturnedItem > returnList = productReturn.getReturnedItems();
				for( ReturnedItem returnedItem: returnList ) {
					Product p = returnedItem.getProduct();
					Integer stock = p.getStock();
					stock += returnedItem.getAmount();
					dao.update( p );
				}

			} catch( Exception e ) {
				e.printStackTrace();
			}
		} else if( productReturn.getStatus() != null
				&& productReturn.getStatus().getCode().equals( ReturnStatus.COD_RECEBIDO ) ) {
			productReturn.setDeliverDate( new Date() );
		} else if( productReturn.getStatus() != null
				&& productReturn.getStatus().getCode().equals( ReturnStatus.COD_RETORNADO ) ) {
			productReturn.setReturnedDate( new Date() );
		}

		return null;
	}

}
