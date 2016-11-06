package com.nakasato.ghstore.web.mb.product;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

@ ManagedBean( name ="productDetailMB" )
@ ViewScoped
public class ProductDetailMB extends ProductMB {

	/**
	 * 
	 */
	private static final long serialVersionUID =1L;
	private Integer amount;
	private Integer maxValue;

	private String code;

	@ PostConstruct
	public void init() {

	}

	public void loadProduct() {
		try {
			if( !StringUtils.isEmpty( code ) &&product ==null ) {
				ProductFilter filter =new ProductFilter();
				filter.setCode( code );
				ICommand commandFind;
				commandFind =FactoryCommand.build( filter, EOperation.FIND );
				List < Product > productList =commandFind.execute().getEntityList();
				if( productList !=null && !productList.isEmpty() ) {
					product =productList.get( 0 );
					amount =1;
					maxValue =product.getStock();
				}

			}

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}

	}

	public void addAmount() {
		if( amount ==null ) {
			amount =1;
		}
		if( amount <product.getStock() ) {
			amount +=1;
		}
	}

	public void removeAmount() {
		if( amount ==null ) {
			amount =1;
		}
		if( amount >1 ) {
			amount -=1;
		}
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount( Integer amount ) {
		this.amount =amount;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue( Integer maxValue ) {
		this.maxValue =maxValue;
	}

	public void resetPage() {
		amount =1;
	}

	public String getCode() {
		return code;
	}

	public void setCode( String code ) {
		this.code =code;
	}

}
