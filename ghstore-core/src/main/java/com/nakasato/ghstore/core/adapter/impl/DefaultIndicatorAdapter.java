package com.nakasato.ghstore.core.adapter.impl;

import com.nakasato.ghstore.core.util.DateFormatUtils;
import com.nakasato.ghstore.domain.enums.EAxisX;
import com.nakasato.ghstore.domain.enums.EAxisY;
import com.nakasato.ghstore.domain.filter.impl.PerformanceGraphicFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.order.OrderItem;
import com.nakasato.ghstore.domain.performance.graphic.AxisData;
import com.nakasato.ghstore.domain.performance.graphic.PerformanceGraphicData;

public abstract class DefaultIndicatorAdapter {

	protected void fillAxisData( PerformanceGraphicData data, PerformanceGraphicFilter filter, Order order,
			OrderItem item ) {
		String axisXValue = null;
		Number axisYValue = null;

		
		
		String dataKey = null; 
		if( filter.getAxisX().equals( EAxisX.DAYS.getCode() ) ) {
			dataKey = DateFormatUtils.formatDateToDay( order.getInsertDate() );
			axisXValue = DateFormatUtils.formatDateToReverseDay( order.getInsertDate() );
		} else if( filter.getAxisX().equals( EAxisX.MONTHS.getCode() ) ) {
			dataKey = DateFormatUtils.formatDateToMonth( order.getInsertDate() );
			axisXValue = DateFormatUtils.formatDateToReverseMonth( order.getInsertDate() );
		} else if( filter.getAxisX().equals( EAxisX.YEARS.getCode() ) ) {
			dataKey = DateFormatUtils.formatDateToYear( order.getInsertDate() );
			axisXValue = DateFormatUtils.formatDateToYear( order.getInsertDate() );
		}

		// Verifica se já existe registro para o dia
		AxisData dt = data.getAxisData().get( dataKey );
		if( dt == null ) {
			dt = new AxisData();
			data.getAxisData().put( dataKey, dt );
			dt.setAxisX( axisXValue );
		}

		if( filter.getAxisY().equals( EAxisY.SOLD_AMOUNT.getCode() ) ) {
			axisYValue = item.getAmount();
		} else {
			axisYValue = item.getTotalValue();
		}

		if( dt.getAxisY() == null ) {
			dt.setAxisY( axisYValue );
		} else {
			if( axisYValue instanceof Double ) {
				Double axisY = axisYValue.doubleValue() + dt.getAxisY().doubleValue();
				dt.setAxisY( axisY );
				
				Double dataTotal = data.getAxisTotal().doubleValue() + dt.getAxisY().doubleValue();
				data.setAxisTotal( dataTotal );
				
			} else {
				Integer axisY = axisYValue.intValue() + dt.getAxisY().intValue();
				dt.setAxisY( axisY );
				
				Integer dataTotal = data.getAxisTotal().intValue() + dt.getAxisY().intValue();
				data.setAxisTotal( dataTotal );
			}
		}

	}

}
