package com.nakasato.ghtstore.core.business.validator;

import java.util.List;

import com.nakasato.ghstore.core.dao.impl.OrderDAO;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.filter.impl.OrderFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghtstore.core.business.Validator;

public class TransactionCodeValidator extends Validator < Order > {

	@Override
	public String validate( Order order ) {
		boolean hasError = false;
		msg = null;
		try {
			OrderDAO dao = new OrderDAO();
			dao.setSession( SessionThreadLocal.getSession() );
			OrderFilter filter = new OrderFilter();
			filter.setTransactionCode( order.getTransactionCode() );
			List < Order > orderList = dao.find( filter );
			if( ! ListUtils.isEmpty( orderList ) ) {
				hasError = true;
			}
		} catch( Exception e ) {
			hasError = true;
			e.printStackTrace();
		}

		if( hasError ) {
			msg = "Já existe um pedido para o código de transação";
		}

		return msg;
	}

}
