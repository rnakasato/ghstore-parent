package com.nakasato.ghstore.web.mb.shopping.cart;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.util.SaveDirectory;
import com.nakasato.ghstore.domain.filter.impl.CityFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.City;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.State;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;
import com.nakasato.ghstore.web.mb.user.LoginMB;
import com.nakasato.web.util.Redirector;

@ ManagedBean( name ="shoppingCartMB" )
@ ViewScoped
public class ShoppingCartMB extends BaseMB {

	@ ManagedProperty( value ="#{loginMB}" )
	protected LoginMB loginMB;

	private Customer loggedUser;
	private ICommand command;

	private State selectedState;
	private Address selectedAddress;
	private Address newAddress;

	private List < State > stateList;
	private List < City > cityList;

	private boolean saveOperation;

	@ PostConstruct
	public void init() {
		FacesContext context =FacesContext.getCurrentInstance();
		loggedUser =( Customer ) loginMB.getLoggedUser();
		newAddress =new Address();
		initStateList();
	}

	public void clearFields() {

	}

	public void save() {
		try {

			ICommand command =FactoryCommand.build( loggedUser, EOperation.SAVE );
			String msg =command.execute().getMsg();
			if( StringUtils.isNotEmpty( msg ) ) {
				addMessage( msg );
			}

			FacesContext context =FacesContext.getCurrentInstance();

			Redirector.redirectTo( context.getExternalContext(), "/clientuser/login.jsf?faces-redirect=true" );

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public void initStateList() {
		try {
			ICommand command =FactoryCommand.build( new State(), EOperation.FINDALL );
			Result result =command.execute();
			stateList =result.getEntityList();
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}

	}

	public void initCityList( AjaxBehaviorEvent event ) {
		try {
			if( selectedState !=null ) {
				CityFilter filter =new CityFilter();
				filter.setStateAcronym( selectedState.getAcronym() );
				ICommand command =FactoryCommand.build( filter, EOperation.FIND );
				Result result =command.execute();
				cityList =result.getEntityList();
			}
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public void addNewAddress() {
		saveOperation =true;
		if( newAddress ==null ) {
			newAddress.setCity( new City() );
			newAddress =new Address();
		}
		RequestContext ctx =RequestContext.getCurrentInstance();
		ctx.execute( "PF('addressDialog').show()" );
	}

	public void cancelAddress() {
		RequestContext ctx =RequestContext.getCurrentInstance();
		ctx.execute( "PF('addressDialog').hide()" );
	}

	public void addAddress() {
		try {

			newAddress.setInsertDate( new Date() );
			loggedUser.getDeliveryAddressList().add( newAddress );
			ICommand commandUpdate =FactoryCommand.build( loggedUser, EOperation.UPDATE );
			commandUpdate.execute();

			newAddress =new Address();
			newAddress.setCity( new City() );

			RequestContext ctx =RequestContext.getCurrentInstance();
			ctx.execute( "PF('addressDialog').hide()" );
		} catch( ClassNotFoundException e ) {
			addMessage( "Erro inesperado" );
		}
	}

	public String getImagePath( Product product ) {
		String path;
		if( product !=null ) {
			path =SaveDirectory.REQUEST_IMG_DIR +product.getImage();
		} else {
			path ="default.jpg";
		}
		return path;
	}

	public void removeAddress() {
		List < Address > addressList =loggedUser.getDeliveryAddressList();
		addressList.remove( newAddress );
	}

	public void changeAddress() {
		saveOperation =false;
	}

	public void updateAddress() {
		RequestContext ctx =RequestContext.getCurrentInstance();
		ctx.execute( "PF('addressDialog').hide()" );
	}

	public Customer getCustomer() {
		return loggedUser;
	}

	public void setCustomer( Customer customer ) {
		this.loggedUser =customer;
	}

	public List < State > getStateList() {
		return stateList;
	}

	public void setStateList( List < State > stateList ) {
		this.stateList =stateList;
	}

	public List < City > getCityList() {
		return cityList;
	}

	public void setCityList( List < City > cityList ) {
		this.cityList =cityList;
	}

	public State getSelectedState() {
		return selectedState;
	}

	public void setSelectedState( State selectedState ) {
		this.selectedState =selectedState;
	}

	public Address getSelectedAddress() {
		return selectedAddress;
	}

	public void setSelectedAddress( Address selectedAddress ) {
		this.selectedAddress =selectedAddress;
	}

	public Address getNewAddress() {
		return newAddress;
	}

	public void setNewAddress( Address newAddress ) {
		this.newAddress =newAddress;
	}

	public boolean isSaveOperation() {
		return saveOperation;
	}

	public void setSaveOperation( boolean saveOperation ) {
		this.saveOperation =saveOperation;
	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB( LoginMB loginMB ) {
		this.loginMB =loginMB;
	}

	public Customer getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser( Customer loggedUser ) {
		this.loggedUser =loggedUser;
	}

	@ Override
	public void clearFilter() {
		// TODO Auto-generated method stub

	}

}
