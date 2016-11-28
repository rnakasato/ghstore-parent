package com.nakasato.ghstore.web.mb.product;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean( name = "clientProductMB" )
@ViewScoped
public class ClientProductMB extends ProductMB {

	private String productName;

	@PostConstruct
	public void init() {

	}

	@Override
	public void fillPriceRange() {
		// TODO Auto-generated method stub

	}

	public String getProductName() {
		return productName;
	}

	public void setProductName( String productName ) {
		this.productName = productName;
	}

}
