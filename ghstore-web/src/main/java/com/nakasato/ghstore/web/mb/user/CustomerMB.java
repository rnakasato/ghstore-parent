package com.nakasato.ghstore.web.mb.user;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.filter.impl.CityFilter;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.City;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.Phone;
import com.nakasato.ghstore.domain.user.State;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;
import com.nakasato.web.util.Redirector;

@ManagedBean( name = "customerMB" )
@ViewScoped
public class CustomerMB extends BaseMB {
	protected Customer customer;
	protected String passwordConfirmation;
	protected ICommand command;

	protected Phone newPhone;
	protected State selectedState;
	protected Address selectedAddress;
	protected Address newAddress;

	protected List < State > stateList;
	protected List < City > cityList;

	protected boolean saveOperation;

	@PostConstruct
	public void init() {
		customer = new Customer();
		customer.setDeliveryAddressList( new LinkedList<>() );
		customer.setPhoneList( new LinkedList<>() );

		newPhone = new Phone();
		selectedAddress = new Address();
		selectedAddress.setCity( new City() );

		newAddress = new Address();
		newAddress.setCity( new City() );

		initStateList();
	}

	public void clearFields() {
		customer = new Customer();
		customer.setDeliveryAddressList( new LinkedList<>() );
		customer.setPhoneList( new LinkedList<>() );

		newPhone = new Phone();
		newAddress = new Address();
		newAddress.setCity( new City() );

	}

	public void save() {
		try {
			boolean samePassword = confirmPassword();
			if( ! samePassword ) {
				addMessage( "A confirmação da senha difere da senha" );
			} else {
				addPhone();
				ICommand command = FactoryCommand.build( customer, EOperation.SAVE );
				String msg = command.execute().getMsg();
				if( StringUtils.isNotEmpty( msg ) ) {
					addMessage( msg );
				} else {
					FacesContext context = FacesContext.getCurrentInstance();

					Redirector.redirectTo( context.getExternalContext(), "/clientuser/login.jsf?faces-redirect=true" );

				}

			}

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			boolean samePassword = confirmPassword();
			if( ! samePassword ) {
				addMessage( "A confirmação da senha difere da senha" );
			} else {
				customer.setPhoneList( new LinkedList<>() );
				addPhone();

				ICommand command = FactoryCommand.build( customer, EOperation.UPDATE );
				String msg = command.execute().getMsg();
				if( StringUtils.isNotEmpty( msg ) ) {
					addMessage( msg );
				} else {
					addMessage( "Dados alterados com sucesso!" );
				}

			}

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public void initStateList() {
		try {
			ICommand command = FactoryCommand.build( new State(), EOperation.FINDALL );
			Result result = command.execute();
			stateList = result.getEntityList();
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}

	}

	public void initCityList( AjaxBehaviorEvent event ) {
		try {
			if( selectedState != null ) {
				CityFilter filter = new CityFilter();
				filter.setStateAcronym( selectedState.getAcronym() );
				ICommand command = FactoryCommand.build( filter, EOperation.FIND );
				Result result = command.execute();
				cityList = result.getEntityList();
			}
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public void addNewAddress() {
		saveOperation = true;
		newAddress = new Address();
		newAddress.setCity( new City() );
		RequestContext ctx = RequestContext.getCurrentInstance();
		ctx.execute( "PF('addressDialog').show()" );
	}

	public void cancelAddress() {
		RequestContext ctx = RequestContext.getCurrentInstance();
		ctx.execute( "PF('addressDialog').hide()" );
	}

	public void addAddress() {
		customer.getDeliveryAddressList().add( newAddress );
		newAddress = new Address();
		newAddress.setCity( new City() );
		RequestContext ctx = RequestContext.getCurrentInstance();
		ctx.execute( "PF('addressDialog').hide()" );
	}

	public void removeAddress() {
		List < Address > addressList = customer.getDeliveryAddressList();
		addressList.remove( newAddress );
	}

	public void changeAddress() {
		saveOperation = false;
	}

	public void updateAddress() {
		RequestContext ctx = RequestContext.getCurrentInstance();
		ctx.execute( "PF('addressDialog').hide()" );
	}

	public void addPhone() {
		newPhone.setUser( customer );
		newPhone.setInsertDate( new Date() );
		customer.getPhoneList().add( newPhone );
	}

	private boolean confirmPassword() {
		boolean same = false;
		if( StringUtils.isNotEmpty( passwordConfirmation ) && StringUtils.isNotEmpty( customer.getPassword() )
				&& passwordConfirmation.equals( customer.getPassword() ) ) {
			same = true;
		}
		return same;
	}

	// Getters e Setters
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer( Customer customer ) {
		this.customer = customer;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation( String passwordConfirmation ) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public Phone getNewPhone() {
		return newPhone;
	}

	public void setNewPhone( Phone newPhone ) {
		this.newPhone = newPhone;
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

	public State getSelectedState() {
		return selectedState;
	}

	public void setSelectedState( State selectedState ) {
		this.selectedState = selectedState;
	}

	public Address getSelectedAddress() {
		return selectedAddress;
	}

	public void setSelectedAddress( Address selectedAddress ) {
		this.selectedAddress = selectedAddress;
	}

	public Address getNewAddress() {
		return newAddress;
	}

	public void setNewAddress( Address newAddress ) {
		this.newAddress = newAddress;
	}

	public boolean isSaveOperation() {
		return saveOperation;
	}

	public void setSaveOperation( boolean saveOperation ) {
		this.saveOperation = saveOperation;
	}

	@Override
	public void clearFilter() {
		// TODO Auto-generated method stub

	}

}
