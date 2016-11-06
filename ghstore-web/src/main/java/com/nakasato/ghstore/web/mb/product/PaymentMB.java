package com.nakasato.ghstore.web.mb.product;

import javax.faces.bean.ManagedBean;

import com.nakasato.ghstore.core.payment.utils.FreteUtil;
import com.nakasato.ghstore.core.payment.utils.PaymentUtil;
import com.nakasato.ghstore.domain.shopping.cart.ShoppingCart;

@ ManagedBean( name ="paymentMB" )
public class PaymentMB {
	public String getShippingCost( ShoppingCart shoppingCart ) {
		String shippingCost ="0.00";
		if( shoppingCart.getAddress() !=null ) {
			shippingCost =FreteUtil.getShippingCost( shoppingCart ).toString();
		}

		return shippingCost;
	}

	public String getTotalPayment( ShoppingCart shoppingCart ) {
		String totalPayment ="0.00";
		if( shoppingCart.getAddress() !=null ) {
			totalPayment =PaymentUtil.getTotalPayment( shoppingCart ).toString();
		}

		return totalPayment;
	}
}
