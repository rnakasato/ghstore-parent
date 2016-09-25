package com.nakasato.ghstore.web.mb;

import javax.faces.bean.ManagedBean;

import com.nakasato.ghstore.core.payment.utils.FreteUtil;
import com.nakasato.ghstore.core.payment.utils.PaymentUtil;
import com.nakasato.ghstore.domain.ShoppingCart;

@ManagedBean(name="paymentMB")
public class PaymentMB {
	public String getShippingCost(ShoppingCart shoppingCart){
		return FreteUtil.getShippingCost(shoppingCart).toString();
	}
	
	public String getTotalPayment(ShoppingCart shoppingCart){
		return PaymentUtil.getTotalPayment(shoppingCart).toString();
	}
}
