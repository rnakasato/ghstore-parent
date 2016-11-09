package com.nakasato.ghstore.web.adapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.primefaces.model.chart.LineChartSeries;

import com.nakasato.ghstore.core.adapter.Adapter;
import com.nakasato.ghstore.core.util.DateFormatUtils;
import com.nakasato.ghstore.domain.carrier.PerformanceGraphicCarrier;
import com.nakasato.ghstore.domain.enums.EAxisX;
import com.nakasato.ghstore.domain.filter.impl.PerformanceGraphicFilter;
import com.nakasato.ghstore.domain.performance.graphic.AxisData;
import com.nakasato.ghstore.domain.performance.graphic.PerformanceGraphicData;
import com.nakasato.ghtstore.core.business.complementor.ComplementAxisData;

public class PerformanceGraphicDataAdapter implements Adapter < PerformanceGraphicCarrier, List < LineChartSeries > > {

	@Override
	public List < LineChartSeries > adapt( PerformanceGraphicCarrier entity ) {

		List < LineChartSeries > seriesList = new ArrayList<>();

		boolean incrementData = false;
		for( PerformanceGraphicData data: entity.getDataList() ) {
			LineChartSeries series = new LineChartSeries();
			series.setLabel( data.getDescription() );

			Map < String, AxisData > axisMap = new TreeMap<>( data.getAxisData() );
			if( axisMap.size() == 1 && ! entity.getFilter().getAxisX().equals( EAxisX.DAYS.getCode() ) ) {
				incrementData = true;
			}

			for( Map.Entry < String, AxisData > entry: axisMap.entrySet() ) {
				String axisX = getXAxisValue( entry, entity.getFilter(), incrementData );
				
				series.set( axisX, entry.getValue().getAxisY() );
			}
			seriesList.add( series );
		}

		return seriesList;
	}

	private String getXAxisValue( Map.Entry < String, AxisData > entry, PerformanceGraphicFilter filter,
			boolean incrementData ) {
		String xAxisData = null;

//		if( filter.isShowOnlyResults() ) {
//			xAxisData = entry.getKey();
//		} else {
			xAxisData = entry.getValue().getAxisX();
			if( incrementData ) {
				xAxisData = getIncrementedDate( xAxisData, filter.getAxisX() );
			}
//		}

		return xAxisData;

	}

	private String getIncrementedDate( String dateString, String axisX ) {
		String incrementedDate = null;
		try {
			Date date = null;
			if( axisX.equals( EAxisX.MONTHS.getCode() ) ) {
				date = DateFormatUtils.yyyyMM.parse( dateString );
				Calendar c = Calendar.getInstance();
				c.setTime( date );
				c.add( Calendar.MONTH, 1 );
				incrementedDate = DateFormatUtils.formatDateToReverseMonth( c.getTime() );
			} else {
				date = DateFormatUtils.yyyy.parse( dateString );
				Calendar c = Calendar.getInstance();
				c.setTime( date );
				c.add( Calendar.YEAR, 1 );
				incrementedDate = DateFormatUtils.formatDateToYear( c.getTime() );
			}
		} catch( ParseException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return incrementedDate;
	}

	public static void main( String[] args ) {
		List < PerformanceGraphicData > dataList = new ArrayList < PerformanceGraphicData >();

		PerformanceGraphicData dataA = new PerformanceGraphicData();
		PerformanceGraphicData dataB = new PerformanceGraphicData();
		PerformanceGraphicData dataC = new PerformanceGraphicData();
		PerformanceGraphicData dataD = new PerformanceGraphicData();

		dataList.add( dataA );
		dataList.add( dataB );
		dataList.add( dataC );
		dataList.add( dataD );

		Map < String, AxisData > dataMapA = new HashMap<>();
		Map < String, AxisData > dataMapB = new HashMap<>();
		Map < String, AxisData > dataMapC = new HashMap<>();

		AxisData dt1 = new AxisData();
		AxisData dt2 = new AxisData();
		AxisData dt3 = new AxisData();
		AxisData dt4 = new AxisData();
		AxisData dt5 = new AxisData();
		AxisData dt6 = new AxisData();
		AxisData dt7 = new AxisData();
		AxisData dt8 = new AxisData();

		dt1.setAxisX( "01-2016" );
		dt1.setAxisY( 30 );

		dt2.setAxisX( "03-2016" );
		dt2.setAxisY( 35 );

		dt3.setAxisX( "02-2016" );
		dt3.setAxisY( 35 );

		dt4.setAxisX( "02-2016" );
		dt4.setAxisY( 30 );

		dt5.setAxisX( "05-2016" );
		dt5.setAxisY( 35 );

		dt6.setAxisX( "04-2016" );
		dt6.setAxisY( 35 );

		dt7.setAxisX( "07-2016" );
		dt7.setAxisY( 30 );

		dt8.setAxisX( "01-2016" );
		dt8.setAxisY( 35 );

		dataMapA.put( dt1.getAxisX(), dt1 );
		dataMapA.put( dt2.getAxisX(), dt2 );
		dataMapA.put( dt3.getAxisX(), dt3 );

		dataMapB.put( dt4.getAxisX(), dt4 );
		dataMapB.put( dt5.getAxisX(), dt5 );
		dataMapB.put( dt6.getAxisX(), dt6 );

		dataMapC.put( dt7.getAxisX(), dt7 );
		dataMapC.put( dt8.getAxisX(), dt8 );

		dataA.setAxisData( dataMapA );
		dataB.setAxisData( dataMapB );

		dataC.setAxisData( dataMapC );
		dataD.setAxisData( dataMapA );

		PerformanceGraphicCarrier carrier = new PerformanceGraphicCarrier();
		carrier.setDataList( dataList );
		new ComplementAxisData().complement( carrier );

		List < LineChartSeries > ser = new PerformanceGraphicDataAdapter().adapt( carrier );
		System.out.println( ser.get( 0 ).getData().get( "01-2016" ) );

	}

}
