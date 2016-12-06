package com.nakasato.ghstore.web.mb.productexchange;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.nakasato.ghstore.domain.filter.impl.ProductExchangeFilter;
import com.nakasato.ghstore.domain.filter.impl.ProductReturnFilter;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.web.mb.user.LoginMB;

@ManagedBean( name = "customerExchangeMB" )
@ViewScoped
public class CustomerExchangeMB extends ExchangeMB {

	@ManagedProperty( value = "#{loginMB}" )
	private LoginMB loginMB;

	@PostConstruct
	public void init() {
		customer = ( Customer ) loginMB.getLoggedUser();

		filter = new ProductExchangeFilter();
		filter.setCustomer( customer );
		initExchangeStatus();

	}

	@Override
	public void clearFilter() {
		filter = new ProductExchangeFilter();
		filter.setCustomer( customer );
	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB( LoginMB loginMB ) {
		this.loginMB = loginMB;
	}

}
