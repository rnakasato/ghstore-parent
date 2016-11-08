package com.nakasato.ghstore.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nakasato.ghstore.domain.DomainSpecificEntity;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.Customer;

public class Order extends DomainSpecificEntity {
	private List < OrderItem > orderItemList;
	private List < ProductReturn > productReturnList;
	private Double totalValue;
	private OrderStatus orderStatus;
	private Date deliverDate;
	private Customer customer;
	private String transactionCode;
	private Address deliverAddress;
	private boolean discount;

	public List < OrderItem > getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList( List < OrderItem > orderItemList ) {
		this.orderItemList = orderItemList;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue( Double totalValue ) {
		this.totalValue = totalValue;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus( OrderStatus orderStatus ) {
		this.orderStatus = orderStatus;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate( Date deliverDate ) {
		this.deliverDate = deliverDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer( Customer customer ) {
		this.customer = customer;
	}

	public List < ProductReturn > getProductReturnList() {
		return productReturnList;
	}

	public void setProductReturnList( List < ProductReturn > productReturnList ) {
		this.productReturnList = productReturnList;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode( String transactionCode ) {
		this.transactionCode = transactionCode;
	}

	public Address getDeliverAddress() {
		return deliverAddress;
	}

	public void setDeliverAddress( Address deliverAddress ) {
		this.deliverAddress = deliverAddress;
	}

	public void addItem( Product product, Integer amount ) {
		OrderItem item = new OrderItem();
		boolean exists = false;
		if( orderItemList == null ) {
			orderItemList = new ArrayList();
		}
		for( OrderItem oItem: orderItemList ) {
			if( oItem.getProduct().getId() == product.getId() ) {
				oItem.setAmount( oItem.getAmount() + amount );
				exists = true;
			}
		}
		if( ! exists ) {
			item.setProduct( product );
			item.setAmount( amount );
		}
	}

	public void removeItem( Product product, Integer amount ) {
		boolean exists = false;
		Integer removedIndex = null;
		if( orderItemList != null && ! orderItemList.isEmpty() ) {
			int index = 0;
			for( OrderItem oItem: orderItemList ) {
				if( oItem.getProduct().getId() == product.getId() ) {
					exists = true;
					removedIndex = index;
					break;
				}
				index ++ ;
			}
			if( exists && removedIndex != null ) {
				orderItemList.remove( removedIndex );
			}
		}
	}

	public boolean isDiscount() {
		return discount;
	}

	public void setDiscount( boolean discount ) {
		this.discount = discount;
	}

}
