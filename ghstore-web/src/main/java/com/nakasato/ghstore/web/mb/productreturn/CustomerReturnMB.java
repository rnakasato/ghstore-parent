package com.nakasato.ghstore.web.mb.productreturn;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.nakasato.ghstore.domain.filter.impl.ProductReturnFilter;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.web.mb.user.LoginMB;

@ManagedBean( name = "customerReturnMB" )
@ViewScoped
public class CustomerReturnMB extends ReturnMB {

	@ManagedProperty( value = "#{loginMB}" )
	private LoginMB loginMB;

	private Customer customer;

	@PostConstruct
	public void init() {
		customer = ( Customer ) loginMB.getLoggedUser();

		filter = new ProductReturnFilter();
		filter.setCustomer( customer );
		initReturnStatus();

	}

	@Override
	public void clearFilter() {
		filter = new ProductReturnFilter();
		filter.setCustomer( customer );
	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB( LoginMB loginMB ) {
		this.loginMB = loginMB;
	}

}
