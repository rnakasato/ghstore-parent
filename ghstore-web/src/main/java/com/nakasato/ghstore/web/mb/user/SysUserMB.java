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
import com.nakasato.ghstore.domain.user.Administrator;
import com.nakasato.ghstore.domain.user.City;
import com.nakasato.ghstore.domain.user.Operator;
import com.nakasato.ghstore.domain.user.Phone;
import com.nakasato.ghstore.domain.user.State;
import com.nakasato.ghstore.domain.user.SysUser;
import com.nakasato.ghstore.domain.user.UserType;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;
import com.nakasato.web.util.Redirector;

@ManagedBean( name = "sysUserMB" )
@ViewScoped
public class SysUserMB extends BaseMB {
	private SysUser newUser;
	private SysUser selectedUser;

	protected String passwordConfirmation;

	protected Phone newPhone;
	protected State selectedState;
	protected Address selectedAddress;
	protected Address newAddress;

	protected List < State > stateList;
	protected List < City > cityList;

	protected boolean saveOperation;

	@PostConstruct
	public void init() {
		newUser = new SysUser();
		newUser.setAddressList( new LinkedList<>() );
		newUser.setPhoneList( new LinkedList<>() );

		newPhone = new Phone();

		selectedAddress = new Address();
		selectedAddress.setCity( new City() );

		newAddress = new Address();
		newAddress.setCity( new City() );

		initStateList();
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

	public void save() {
		try {
			boolean samePassword = confirmPassword( newUser );
			if( ! samePassword ) {
				addMessage( "A confirmação da senha difere da senha" );
			} else {
				addPhone( newUser );

				ICommand command;
				if( newUser.getUserType().getCode().equals( UserType.COD_ADMINISTRATOR ) ) {
					Administrator admin = ( Administrator ) newUser;
					command = FactoryCommand.build( admin, EOperation.SAVE );
				} else {
					Operator op = ( Operator ) newUser;
					command = FactoryCommand.build( op, EOperation.SAVE );
				}

				String msg = command.execute().getMsg();
				if( StringUtils.isNotEmpty( msg ) ) {
					addMessage( msg );
				}

			}
			FacesContext context = FacesContext.getCurrentInstance();

			Redirector.redirectTo( context.getExternalContext(), "/admin/login.jsf?faces-redirect=true" );

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			boolean samePassword = confirmPassword( selectedUser );
			if( ! samePassword ) {
				addMessage( "A confirmação da senha difere da senha" );
			} else {
				selectedUser.setPhoneList( new LinkedList<>() );
				addPhone( selectedUser );

				ICommand command;
				if( newUser.getUserType().getCode().equals( UserType.COD_ADMINISTRATOR ) ) {
					Administrator admin = ( Administrator ) newUser;
					command = FactoryCommand.build( admin, EOperation.UPDATE );
				} else {
					Operator op = ( Operator ) newUser;
					command = FactoryCommand.build( op, EOperation.UPDATE );
				}

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

	public void deactivate() {
		try {
			ICommand command;
			selectedUser.setActive( false );
			if( selectedUser.getUserType().getCode().equals( UserType.COD_ADMINISTRATOR ) ) {
				Administrator admin = ( Administrator ) selectedUser;
				command = FactoryCommand.build( admin, EOperation.UPDATE );
			} else {
				Operator op = ( Operator ) selectedUser;
				command = FactoryCommand.build( op, EOperation.UPDATE );
			}

			String msg = command.execute().getMsg();
			if( StringUtils.isNotEmpty( msg ) ) {
				addMessage( msg );
			} else {
				addMessage( "Usuário desativado!" );
			}

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public void addPhone( SysUser user ) {
		newPhone.setUser( user );
		newPhone.setInsertDate( new Date() );
		user.getPhoneList().add( newPhone );
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

	public void addAddress( SysUser user ) {
		user.getAddressList().add( newAddress );
		newAddress = new Address();
		newAddress.setCity( new City() );
		RequestContext ctx = RequestContext.getCurrentInstance();
		ctx.execute( "PF('addressDialog').hide()" );
	}

	public void removeAddress( SysUser user ) {
		List < Address > addressList = user.getAddressList();
		addressList.remove( newAddress );
	}

	public void changeAddress() {
		saveOperation = false;
	}

	public void updateAddress() {
		RequestContext ctx = RequestContext.getCurrentInstance();
		ctx.execute( "PF('addressDialog').hide()" );
	}

	public void clearFields() {
		newUser = new SysUser();
		newUser.setAddressList( new LinkedList<>() );
		newUser.setPhoneList( new LinkedList<>() );

		newPhone = new Phone();
		newAddress = new Address();
		newAddress.setCity( new City() );

	}

	private boolean confirmPassword( SysUser user ) {
		boolean same = false;
		if( StringUtils.isNotEmpty( passwordConfirmation ) && StringUtils.isNotEmpty( user.getPassword() )
				&& passwordConfirmation.equals( user.getPassword() ) ) {
			same = true;
		}
		return same;
	}

	@Override
	public void clearFilter() {
		// TODO Auto-generated method stub

	}

	public SysUser getNewUser() {
		return newUser;
	}

	public void setNewUser( SysUser newUser ) {
		this.newUser = newUser;
	}

	public SysUser getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser( SysUser selectedUser ) {
		this.selectedUser = selectedUser;
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

	public boolean isSaveOperation() {
		return saveOperation;
	}

	public void setSaveOperation( boolean saveOperation ) {
		this.saveOperation = saveOperation;
	}

}
