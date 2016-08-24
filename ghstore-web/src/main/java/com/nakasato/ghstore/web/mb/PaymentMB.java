package com.nakasato.ghstore.web.mb;

import javax.faces.bean.ManagedBean;

import com.nakasato.ghstore.core.payment.Payment;
import com.nakasato.ghstore.domain.ShoppingCart;

@ManagedBean(name="paymentMB")
public class PaymentMB {
	public String getShippingCost(ShoppingCart shoppingCart){
		return Payment.getShippingCost(shoppingCart).toString();
	}
	
	public String getTotalPayment(ShoppingCart shoppingCart){
		return Payment.getTotalPayment(shoppingCart).toString();
	}
}
