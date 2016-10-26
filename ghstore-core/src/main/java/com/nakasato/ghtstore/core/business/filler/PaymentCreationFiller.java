package com.nakasato.ghtstore.core.business.filler;

import com.nakasato.ghstore.core.payment.utils.PaymentUtil;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.carrier.PaymentCreationCarrier;
import com.nakasato.ghtstore.core.business.Filler;

public class PaymentCreationFiller extends Filler {

	@Override
	public String fill(AbstractDomainEntity entity) {
		PaymentCreationCarrier carrier = (PaymentCreationCarrier) entity;
		String paymentUrl = PaymentUtil.createPayment(carrier.getCart(),carrier.getLoggedUser());
		carrier.setPaymentAddress(paymentUrl);
		
		return null;
	}

}
