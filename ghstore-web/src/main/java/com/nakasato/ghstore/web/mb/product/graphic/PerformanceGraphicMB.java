package com.nakasato.ghstore.web.mb.product.graphic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.carrier.PerformanceGraphicCarrier;
import com.nakasato.ghstore.domain.enums.EAxisX;
import com.nakasato.ghstore.domain.enums.EAxisY;
import com.nakasato.ghstore.domain.filter.impl.CityFilter;
import com.nakasato.ghstore.domain.filter.impl.PerformanceGraphicFilter;
import com.nakasato.ghstore.domain.performance.graphic.AxisData;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.domain.user.City;
import com.nakasato.ghstore.domain.user.State;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.adapter.PerformanceGraphicDataAdapter;
import com.nakasato.ghstore.web.mb.BaseMB;

/**
 * 
 * @author rafae Descrição:
 */

@ManagedBean( name = "performanceGraphicMB" )
@ViewScoped
public class PerformanceGraphicMB extends BaseMB {

	protected List < StoreCategory > categoryList;
	protected List < Product > productList;
	private List < State > stateList;
	private List < City > cityList;
	private PerformanceGraphicFilter filter;

	private Double axisYMaxValue;

	private LineChartModel chartModel;

	@PostConstruct
	public void init() {
		initLists();
		filter = new PerformanceGraphicFilter();
		initEmptyChart();
	}

	public void createLineChart() {
		try {
			PerformanceGraphicCarrier carrier = new PerformanceGraphicCarrier();
			carrier.setFilter( filter );
			
			if(filter.getEndDate() != null){
				Calendar c = Calendar.getInstance();
				c.setTime( filter.getEndDate() );
				c.set( Calendar.HOUR, 23 );
				c.set( Calendar.MINUTE, 59 );
				c.set( Calendar.SECOND, 59 );
				filter.setEndDate( c.getTime() );
			}

			ICommand commandFind;
			commandFind = FactoryCommand.build( carrier, EOperation.FIND );
			Result result = commandFind.execute();

			if( StringUtils.isNotEmpty( result.getMsg() ) ) {
				addMessage( result.getMsg() );
				initEmptyChart();

			} else {
				chartModel = initLinearModel( carrier );
				chartModel.setTitle( "Gráfico de performance" );
				chartModel.setAnimate( true );
				chartModel.setLegendPosition( "se" );
			}
		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private LineChartModel initLinearModel( PerformanceGraphicCarrier carrier ) {
		LineChartModel model = new LineChartModel();
		model.setZoom( true );
		
		PerformanceGraphicDataAdapter adapter = new PerformanceGraphicDataAdapter();
		List < LineChartSeries > seriesList = adapter.adapt( carrier );
		for( LineChartSeries series: seriesList ) {
			model.addSeries( series );
		}

		Axis yAxis = model.getAxis( AxisType.Y );

		yAxis.setMin( 0 );
		yAxis.setMax( axisYMaxValue );

		if( filter.getAxisY().equals( EAxisY.TOTAL_VALUE.getCode() ) ) {
			yAxis.setTickFormat( "R$ %.2f" );
		} else {
			yAxis.setTickFormat( "%.0f" );
		}

		String yLabel = EAxisY.getValue( filter.getAxisY() ).getDescription();
		yAxis.setLabel( yLabel );

		Axis xAxis = null;
		xAxis = getShowDateAxis( filter );
		

		model.getAxes().put( AxisType.X, xAxis );
		model.setDatatipFormat( getDataTipFormat() );
		model.setExtender( "customExtender" );

		return model;
	}
	
	private String getDataTipFormat(){
		StringBuilder sb = new StringBuilder();
		return "<span>Valor: %s</span>";
	}

	private Axis getShowDateAxis( PerformanceGraphicFilter filter ) {
		Axis xAxis = new DateAxis();
		String xLabel = EAxisX.getValue( filter.getAxisX() ).getDescription();
		xAxis.setTickFormat( EAxisX.getValue( filter.getAxisX() ).getTickFormat() );
		xAxis.setLabel( xLabel );
		xAxis.setTickInterval( EAxisX.getValue( filter.getAxisX() ).getTickInterval() );
		return xAxis;
	}

//	private Axis getShowOnlyResultAxis( PerformanceGraphicFilter filter ) {
//		Axis xAxis = new CategoryAxis();
//		String xLabel = EAxisX.getValue( filter.getAxisX() ).getDescription();
//		xAxis.setLabel( xLabel );
//		xAxis.setTickFormat( "%" );
//		return xAxis;
//	}

	private void initEmptyChart() {
		chartModel = new LineChartModel();

		LineChartSeries defaultSerie = new LineChartSeries();
		defaultSerie.setLabel( "" );
		defaultSerie.set( 0, 0 );
		chartModel.addSeries( defaultSerie );

		chartModel.setTitle( "Gráfico de performance" );
		chartModel.setAnimate( true );
		chartModel.setLegendPosition( "se" );

		Axis yAxis = chartModel.getAxis( AxisType.Y );

		yAxis.setMin( 0 );
		yAxis.setMax( axisYMaxValue );

		yAxis.setLabel( "-" );
		chartModel.getAxes().put( AxisType.X, new CategoryAxis( "-" ) );

	}

	private void initLists() {
		try {
			ICommand commandFind = FactoryCommand.build( new StoreCategory(), EOperation.FINDALL );
			categoryList = commandFind.execute().getEntityList();

			commandFind = FactoryCommand.build( new Product(), EOperation.FINDALL );
			productList = commandFind.execute().getEntityList();

			commandFind = FactoryCommand.build( new State(), EOperation.FINDALL );
			stateList = commandFind.execute().getEntityList();

			cityList = new ArrayList < City >();

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
			addMessage( "Erro inesperado" );
		}

	}

	public void initCityList( AjaxBehaviorEvent event ) {
		try {
			if( filter.getState() != null ) {
				CityFilter cityFilter = new CityFilter();
				cityFilter.setStateAcronym( filter.getState().getAcronym() );
				ICommand command = FactoryCommand.build( cityFilter, EOperation.FIND );
				Result result = command.execute();
				cityList = result.getEntityList();
			}
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void clearFilter() {
		filter = new PerformanceGraphicFilter();
	}

	public List < StoreCategory > getCategoryList() {
		return categoryList;
	}

	public void setCategoryList( List < StoreCategory > categoryList ) {
		this.categoryList = categoryList;
	}

	public List < Product > getProductList() {
		return productList;
	}

	public void setProductList( List < Product > productList ) {
		this.productList = productList;
	}

	public List < State > getStateList() {
		return stateList;
	}

	public void setStateList( List < State > stateList ) {
		this.stateList = stateList;
	}

	public List < City > getCityList() {
		return cityList;
	}

	public void setCityList( List < City > cityList ) {
		this.cityList = cityList;
	}

	public PerformanceGraphicFilter getFilter() {
		return filter;
	}

	public void setFilter( PerformanceGraphicFilter filter ) {
		this.filter = filter;
	}

	public Double getAxisYMaxValue() {
		return axisYMaxValue;
	}

	public void setAxisYMaxValue( Double axisYMaxValue ) {
		this.axisYMaxValue = axisYMaxValue;
	}

	public LineChartModel getChartModel() {
		return chartModel;
	}

	public void setChartModel( LineChartModel chartModel ) {
		this.chartModel = chartModel;
	}

}

