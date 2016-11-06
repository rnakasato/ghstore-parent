package com.nakasato.ghstore.domain.productexchange;

import java.util.List;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.order.Order;

public class ProductExchange extends AbstractDomainEntity {
	private Order order;
	private List < ExchangeItem > exchangeItems;
	private String reason;
	private Double totalValue;

	public Order getOrder() {
		return order;
	}

	public void setOrder( Order order ) {
		this.order =order;
	}

	public List < ExchangeItem > getExchangeItems() {
		return exchangeItems;
	}

	public void setExchangeItems( List < ExchangeItem > exchangeItems ) {
		this.exchangeItems =exchangeItems;
	}

	public String getReason() {
		return reason;
	}

	public void setReason( String reason ) {
		this.reason =reason;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue( Double totalValue ) {
		this.totalValue =totalValue;
	}

}
