package com.nakasato.ghstore.web.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.primefaces.model.chart.LineChartSeries;

import com.nakasato.ghstore.core.adapter.Adapter;
import com.nakasato.ghstore.domain.carrier.PerformanceGraphicCarrier;
import com.nakasato.ghstore.domain.performance.graphic.AxisData;
import com.nakasato.ghstore.domain.performance.graphic.PerformanceGraphicData;
import com.nakasato.ghtstore.core.business.complementor.ComplementAxisData;

public class PerformanceGraphicDataAdapter implements Adapter < PerformanceGraphicCarrier, List < LineChartSeries > > {

	@Override
	public List < LineChartSeries > adapt( PerformanceGraphicCarrier entity ) {

		List < LineChartSeries > seriesList = new ArrayList<>();

		for( PerformanceGraphicData data: entity.getDataList() ) {
			LineChartSeries series = new LineChartSeries();
			series.setLabel( data.getDescription() );
			
			Map< String, AxisData > axisMap = new TreeMap<>( data.getAxisData() );
			
			for( Map.Entry < String, AxisData > entry: axisMap.entrySet() ) {
				series.set( entry.getValue().getAxisX(), entry.getValue().getAxisY() );
			}
			seriesList.add( series );
		}

		return seriesList;
	}
	
	public static void main( String[] args ) {
		List<PerformanceGraphicData> dataList = new ArrayList < PerformanceGraphicData >();
		
		PerformanceGraphicData dataA = new PerformanceGraphicData();
		PerformanceGraphicData dataB = new PerformanceGraphicData();
		PerformanceGraphicData dataC = new PerformanceGraphicData();
		PerformanceGraphicData dataD = new PerformanceGraphicData();
		
		dataList.add( dataA );
		dataList.add( dataB );
		dataList.add( dataC );
		dataList.add( dataD );
		
		Map<String,AxisData> dataMapA = new HashMap<>();
		Map<String,AxisData> dataMapB = new HashMap<>();
		Map<String,AxisData> dataMapC = new HashMap<>();
		
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
		
		
		List<LineChartSeries> ser = new PerformanceGraphicDataAdapter().adapt( carrier );
		System.out.println( ser.get( 0 ).getData().get( "01-2016" ) );
		
	}

}
