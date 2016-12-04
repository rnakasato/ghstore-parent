package com.nakasato.ghstore.domain.productexchange;

import java.util.Date;
import java.util.List;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.order.Order;

public class ProductExchange extends AbstractDomainEntity {
	private Order order;
	private List < ExchangeItem > exchangeItems;
	private String reason;
	private Double totalValue;
	private ExchangeStatus status;

	private Date deliverDate;
	private Date returnedDate;

	public Order getOrder() {
		return order;
	}

	public void setOrder( Order order ) {
		this.order = order;
	}

	public List < ExchangeItem > getExchangeItems() {
		return exchangeItems;
	}

	public void setExchangeItems( List < ExchangeItem > exchangeItems ) {
		this.exchangeItems = exchangeItems;
	}

	public String getReason() {
		return reason;
	}

	public void setReason( String reason ) {
		this.reason = reason;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue( Double totalValue ) {
		this.totalValue = totalValue;
	}

	public ExchangeStatus getStatus() {
		return status;
	}

	public void setStatus( ExchangeStatus status ) {
		this.status = status;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate( Date deliverDate ) {
		this.deliverDate = deliverDate;
	}

	public Date getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate( Date returnedDate ) {
		this.returnedDate = returnedDate;
	}

}
