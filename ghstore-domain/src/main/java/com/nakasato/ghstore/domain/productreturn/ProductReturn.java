package com.nakasato.ghstore.domain.productreturn;

import java.util.Date;
import java.util.List;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.order.Order;

public class ProductReturn extends AbstractDomainEntity {
	private Order order;
	private List < ReturnedItem > returnedItems;
	private String reason;
	private Double totalValue;
	private ReturnStatus status;

	private Date deliverDate;
	private Date returnedDate;

	public Order getOrder() {
		return order;
	}

	public void setOrder( Order order ) {
		this.order = order;
	}

	public List < ReturnedItem > getReturnedItems() {
		return returnedItems;
	}

	public void setReturnedItems( List < ReturnedItem > returnedItems ) {
		this.returnedItems = returnedItems;
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

	public ReturnStatus getStatus() {
		return status;
	}

	public void setStatus( ReturnStatus status ) {
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
