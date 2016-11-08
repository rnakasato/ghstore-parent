package com.nakasato.ghstore.domain.carrier;

import java.util.List;

import com.nakasato.ghstore.domain.EntityCarrier;
import com.nakasato.ghstore.domain.filter.impl.PerformanceGraphicFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.performance.graphic.PerformanceGraphicData;

public class PerformanceGraphicCarrier extends EntityCarrier {
	private PerformanceGraphicFilter filter;
	private List < PerformanceGraphicData > dataList;

	private List < Order > orderList;

	public PerformanceGraphicFilter getFilter() {
		return filter;
	}

	public void setFilter( PerformanceGraphicFilter filter ) {
		this.filter = filter;
	}

	public List < PerformanceGraphicData > getDataList() {
		return dataList;
	}

	public void setDataList( List < PerformanceGraphicData > dataList ) {
		this.dataList = dataList;
	}

	public List < Order > getOrderList() {
		return orderList;
	}

	public void setOrderList( List < Order > orderList ) {
		this.orderList = orderList;
	}

}
