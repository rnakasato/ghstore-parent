package com.nakasato.ghstore.web.mb.product;

import javax.faces.bean.ManagedBean;

import com.nakasato.ghstore.core.payment.utils.FreteUtil;
import com.nakasato.ghstore.core.payment.utils.PaymentUtil;
import com.nakasato.ghstore.domain.shopping.cart.ShoppingCart;

@ManagedBean( name = "paymentMB" )
public class PaymentMB {
	public String getShippingCost( ShoppingCart shoppingCart ) {
		String shippingCost = null;
		try{
			if( shoppingCart.getAddress() != null ) {
				shippingCost = FreteUtil.getShippingCost( shoppingCart ).toString();
			}
		}catch(Exception e){
			shippingCost = "0.00";
			// Para propósitos de teste caso o WS não esteja disponível o Frete é igual a 0
		}
		

		return shippingCost;
	}

	public String getTotalPayment( ShoppingCart shoppingCart ) {
		String totalPayment = null;
		try{
			if( shoppingCart.getAddress() != null ) {
				totalPayment = PaymentUtil.getTotalPayment( shoppingCart ).toString();
			}
		}catch(Exception e){
			totalPayment = "0.00";
			// Para propósitos de teste caso o WS não esteja disponível o Frete é igual a 0
		}

		return totalPayment;
	}
}
