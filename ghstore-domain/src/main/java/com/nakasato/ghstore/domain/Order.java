package com.nakasato.ghstore.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Order extends DomainSpecificEntity {
	private List<OrderItem> orderItemList;
	private Double totalValue;
	private OrderStatus orderStatus;
	private Date deliverDate;
	private Customer customer;

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void addItem(Product product, Integer amount) {
		OrderItem item = new OrderItem();
		boolean exists = false;
		if (orderItemList == null) {
			orderItemList = new ArrayList();
		}
		for (OrderItem oItem : orderItemList) {
			if (oItem.getProduct().getId() == product.getId()) {
				oItem.setAmount(oItem.getAmount() + amount);
				exists = true;
			}
		}
		if (!exists) {
			item.setProduct(product);
			item.setAmount(amount);
		}
	}

	public void removeItem(Product product, Integer amount) {
		boolean exists = false;
		Integer removedIndex = null;
		if (orderItemList != null && !orderItemList.isEmpty()) {
			int index = 0;
			for (OrderItem oItem : orderItemList) {
				if (oItem.getProduct().getId() == product.getId()) {
					exists = true;
					removedIndex = index;
					break;
				}
				index++;
			}
			if (exists && removedIndex != null) {
				orderItemList.remove(removedIndex);
			}
		}
	}

}
