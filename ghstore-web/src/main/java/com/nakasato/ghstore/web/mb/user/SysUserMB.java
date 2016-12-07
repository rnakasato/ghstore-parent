package com.nakasato.ghstore.web.mb.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.filter.impl.AdministratorFilter;
import com.nakasato.ghstore.domain.filter.impl.CityFilter;
import com.nakasato.ghstore.domain.filter.impl.OperatorFilter;
import com.nakasato.ghstore.domain.filter.impl.SysUserFilter;
import com.nakasato.ghstore.domain.filter.impl.UserTypeFilter;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.Administrator;
import com.nakasato.ghstore.domain.user.City;
import com.nakasato.ghstore.domain.user.Operator;
import com.nakasato.ghstore.domain.user.Phone;
import com.nakasato.ghstore.domain.user.State;
import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghstore.domain.user.UserType;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.adapter.UserToAdmininistratorAdapter;
import com.nakasato.ghstore.web.adapter.UserToOperatorAdapter;
import com.nakasato.ghstore.web.mb.BaseMB;
import com.nakasato.ghstore.web.mb.util.RedirectMB;
import com.nakasato.web.util.Redirector;

@ManagedBean( name = "sysUserMB" )
@ViewScoped
public class SysUserMB extends BaseMB {

	@ManagedProperty( value = "#{loginMB}" )
	private LoginMB loginMB;

	private User newUser;
	private User selectedUser;
	private SysUserFilter filter;

	private String passwordConfirmation;

	// O status está como String porque
	// o componente de Radio não processa
	// as opções como boleanos quando
	// há #{null} como opção
	private String status;

	private Phone newPhone;
	private State selectedState;
	private Address selectedAddress;
	private Address newAddress;

	private List < State > stateList;
	private List < City > cityList;
	private List < UserType > userTypeList;

	private List < User > userList;

	private boolean saveOperation;

	private UserToAdmininistratorAdapter adapterToAdmin;
	private UserToOperatorAdapter adapterToOperator;

	@PostConstruct
	public void init() {
		adapterToAdmin = new UserToAdmininistratorAdapter();
		adapterToOperator = new UserToOperatorAdapter();

		filter = new SysUserFilter();
		newUser = new User();
		newUser.setAddressList( new LinkedList<>() );
		newUser.setPhoneList( new LinkedList<>() );

		newPhone = new Phone();

		selectedAddress = new Address();
		selectedAddress.setCity( new City() );

		newAddress = new Address();
		newAddress.setCity( new City() );

		FacesContext context = FacesContext.getCurrentInstance();
		selectedUser = ( User ) context.getExternalContext().getFlash().get( "user" );

		String currentPage = context.getViewRoot().getViewId();
		if( currentPage.contains( "userSearch" ) && loginMB.getLoggedUser() instanceof Operator ) {
			RedirectMB redirectMB = new RedirectMB();
			redirectMB.redirectToUpdate( loginMB.getLoggedUser() );
		} else if( currentPage.contains( "userUpdate" ) && loginMB.getLoggedUser() instanceof Operator ) {
			selectedUser = loginMB.getLoggedUser();
		}

		if( selectedUser != null ) {
			newPhone = selectedUser.getPhoneList().get( 0 );
		}

		initStateList();
		initUserType();
	}

	public void listUsers() {
		userList = new ArrayList<>();
		if( StringUtils.isNotEmpty( status ) ) {
			if( status.equals( "0" ) ) {
				filter.setActive( false );
			} else if( status.equals( "1" ) ) {
				filter.setActive( true );
			}
		}

		List < User > admList = findAdministrators();
		List < User > opList = findOperators();

		if( ListUtils.isNotEmpty( admList ) ) {
			userList.addAll( admList );
		}

		if( ListUtils.isNotEmpty( opList ) ) {
			userList.addAll( opList );
		}
	}

	private List < User > findAdministrators() {
		List < User > userList = null;
		try {
			AdministratorFilter adminFilter = new AdministratorFilter();
			adminFilter.setName( filter.getName() );
			adminFilter.setUserName( filter.getUserName() );
			adminFilter.setActive( filter.getActive() );
			adminFilter.setUserType( filter.getUserType() );
			adminFilter.setCpf( filter.getCpf() );
			adminFilter.setLoadAddress( true );

			ICommand commandFind;
			commandFind = FactoryCommand.build( adminFilter, EOperation.FIND );
			List < Administrator > adminList = commandFind.execute().getEntityList();
			if( ListUtils.isNotEmpty( adminList ) ) {
				userList = new ArrayList<>();
				userList.addAll( adminList );
			}
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}

		return userList;
	}

	private List < User > findOperators() {
		List < User > userList = null;
		try {
			OperatorFilter opFilter = new OperatorFilter();

			opFilter.setName( filter.getName() );
			opFilter.setUserName( filter.getUserName() );
			opFilter.setActive( filter.getActive() );
			opFilter.setUserType( filter.getUserType() );
			opFilter.setCpf( filter.getCpf() );
			opFilter.setLoadAddress( true );

			ICommand commandFind;
			commandFind = FactoryCommand.build( opFilter, EOperation.FIND );
			List < Operator > opList = commandFind.execute().getEntityList();
			if( ListUtils.isNotEmpty( opList ) ) {
				userList = new ArrayList<>();
				userList.addAll( opList );
			}
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
		return userList;
	}

	private void initUserType() {
		try {
			UserTypeFilter filter = new UserTypeFilter();
			List < String > codeList = new ArrayList<>();
			codeList.add( UserType.COD_ADMINISTRATOR );
			codeList.add( UserType.COD_OPERATOR );

			filter.setCodeList( codeList );
			ICommand commandFind = FactoryCommand.build( filter, EOperation.FIND );
			userTypeList = commandFind.execute().getEntityList();
		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initStateList() {
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
					Administrator admin = adapterToAdmin.adapt( newUser );
					command = FactoryCommand.build( admin, EOperation.SAVE );
				} else {
					Operator op = adapterToOperator.adapt( newUser );
					command = FactoryCommand.build( op, EOperation.SAVE );
				}
				String msg = command.execute().getMsg();
				if( StringUtils.isNotEmpty( msg ) ) {
					addMessage( msg );
				} else {
					FacesContext ctx = FacesContext.getCurrentInstance();
					ctx.addMessage( null, new FacesMessage( "Usuário " + newUser.getUsername() + " cadastrado com sucesso! " ) );
					Flash flash = ctx.getExternalContext().getFlash();
					flash.setKeepMessages( true );
					flash.setRedirect( true );
					
					Redirector.redirectTo( ctx.getExternalContext(), "/admin/userSearch.jsf?faces-redirect=true" );
				}

			}

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
				if( selectedUser.getUserType().getCode().equals( UserType.COD_ADMINISTRATOR ) ) {
					Administrator admin = adapterToAdmin.adapt( selectedUser );
					command = FactoryCommand.build( admin, EOperation.UPDATE );
				} else {
					Operator op = adapterToOperator.adapt( selectedUser );
					command = FactoryCommand.build( op, EOperation.UPDATE );
				}

				String msg = command.execute().getMsg();
				if( StringUtils.isNotEmpty( msg ) ) {
					addMessage( msg );
				} else {
					FacesContext ctx = FacesContext.getCurrentInstance();
					ctx.addMessage( null, new FacesMessage( "Usuário " + selectedUser.getUsername() + " alterado com sucesso! " ) );
					Flash flash = ctx.getExternalContext().getFlash();
					flash.setKeepMessages( true );
					flash.setRedirect( true );

					Redirector.redirectTo( ctx.getExternalContext(), "/admin/userSearch.jsf?faces-redirect=true" );
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
				Administrator admin = adapterToAdmin.adapt( selectedUser );
				command = FactoryCommand.build( admin, EOperation.UPDATE );
			} else {
				Operator op = adapterToOperator.adapt( selectedUser );
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

	public void activate() {
		try {
			ICommand command;
			selectedUser.setActive( true );
			if( selectedUser.getUserType().getCode().equals( UserType.COD_ADMINISTRATOR ) ) {
				Administrator admin = adapterToAdmin.adapt( selectedUser );
				command = FactoryCommand.build( admin, EOperation.UPDATE );
			} else {
				Operator op = adapterToOperator.adapt( selectedUser );
				command = FactoryCommand.build( op, EOperation.UPDATE );
			}

			String msg = command.execute().getMsg();
			if( StringUtils.isNotEmpty( msg ) ) {
				addMessage( msg );
			} else {
				addMessage( "Usuário ativado!" );
			}

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public void addPhone( User user ) {
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

	public void addAddress( User user ) {
		user.getAddressList().add( newAddress );
		newAddress = new Address();
		newAddress.setCity( new City() );
		RequestContext ctx = RequestContext.getCurrentInstance();
		ctx.execute( "PF('addressDialog').hide()" );
	}

	public void removeAddress( User user ) {
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
		newUser = new User();
		newUser.setAddressList( new LinkedList<>() );
		newUser.setPhoneList( new LinkedList<>() );

		newPhone = new Phone();
		newAddress = new Address();
		newAddress.setCity( new City() );

	}

	private boolean confirmPassword( User user ) {
		boolean same = false;
		if( StringUtils.isNotEmpty( passwordConfirmation ) && StringUtils.isNotEmpty( user.getPassword() )
				&& passwordConfirmation.equals( user.getPassword() ) ) {
			same = true;
		}
		return same;
	}

	@Override
	public void clearFilter() {
		filter = new SysUserFilter();
		status = null;
	}

	// Getters e Setters
	public User getNewUser() {
		return newUser;
	}

	public void setNewUser( User newUser ) {
		this.newUser = newUser;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser( User selectedUser ) {
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

	public SysUserFilter getFilter() {
		return filter;
	}

	public void setFilter( SysUserFilter filter ) {
		this.filter = filter;
	}

	public List < UserType > getUserTypeList() {
		return userTypeList;
	}

	public void setUserTypeList( List < UserType > userTypeList ) {
		this.userTypeList = userTypeList;
	}

	public List < User > getUserList() {
		return userList;
	}

	public void setUserList( List < User > userList ) {
		this.userList = userList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus( String status ) {
		this.status = status;
	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB( LoginMB loginMB ) {
		this.loginMB = loginMB;
	}

}
