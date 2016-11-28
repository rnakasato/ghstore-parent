package com.nakasato.ghstore.web.mb.user;

import java.util.LinkedList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.City;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.Phone;

@ManagedBean(name="customerUpdateMB")
@ViewScoped
public class CustomerUpdateMB extends CustomerMB{
	
	@ManagedProperty( value = "#{loginMB}" )
	private LoginMB loginMB;
	
	public void init(){
		customer = (Customer) loginMB.getLoggedUser();			

		newPhone = customer.getPhoneList().get( 0 );
		
		selectedAddress = new Address();
		selectedAddress.setCity( new City() );

		newAddress = new Address();
		newAddress.setCity( new City() );

		initStateList();
	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB( LoginMB loginMB ) {
		this.loginMB = loginMB;
	}
	
	

}
