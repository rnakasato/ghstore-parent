package com.nakasato.ghstore.domain.productreturn;

import java.util.List;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.order.Order;

public class ProductReturn extends AbstractDomainEntity {
	private Order order;
	private List<ReturnedItem> returnedItems;
	private String reason;
	private Double totalValue;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<ReturnedItem> getReturnedItems() {
		return returnedItems;
	}

	public void setReturnedItems(List<ReturnedItem> returnedItems) {
		this.returnedItems = returnedItems;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

}
