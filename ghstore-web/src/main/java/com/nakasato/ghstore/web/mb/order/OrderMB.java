package com.nakasato.ghstore.web.mb.order;

import java.util.List;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.domain.filter.impl.OrderFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderStatus;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;

public abstract class OrderMB extends BaseMB {
	protected OrderFilter filter;
	protected List < Order > orderResults;
	protected List < OrderStatus > orderStatusList;
	protected Order selectedOrder;

	public abstract void listOrders();

	public void viewDetails( Order order ) {
		selectedOrder = order;

	}

	protected void initOrderStatus() {
		try {
			ICommand commandFindAll = FactoryCommand.build( new OrderStatus(), EOperation.FINDALL );
			orderStatusList = commandFindAll.execute().getEntityList();
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	protected boolean validateDates() {
		boolean hasError = false;
		if( ( filter.getStartDate() != null && filter.getEndDate() == null )
				|| ( filter.getStartDate() == null && filter.getEndDate() != null ) ) {
			addMessage( "Para usar o filtro de data as datas inicial e final devem estar preenchidas!" );
			hasError = true;
		} else if( filter.getStartDate() != null && filter.getEndDate() != null
				&& filter.getEndDate().before( filter.getStartDate() ) ) {
			addMessage( "A data final deve ser inferior que a data inicial!" );
			hasError = true;
		}
		return hasError;
	}

	public Double getDiscount( Order order ) {
		Double discount = 0D;
		if( order != null && order.getDiscountValue() != null ) {
			discount = order.getDiscountValue();
		}
		return discount;
	}

	public OrderFilter getFilter() {
		return filter;
	}

	public void setFilter( OrderFilter filter ) {
		this.filter = filter;
	}

	public List < Order > getOrderResults() {
		return orderResults;
	}

	public void setOrderResults( List < Order > orderResults ) {
		this.orderResults = orderResults;
	}

	public List < OrderStatus > getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList( List < OrderStatus > orderStatusList ) {
		this.orderStatusList = orderStatusList;
	}

	public Order getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder( Order selectedOrder ) {
		this.selectedOrder = selectedOrder;
	}

}
