package com.nakasato.ghstore.web.mb.user;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.filter.impl.AdministratorFilter;
import com.nakasato.ghstore.domain.filter.impl.CustomerFilter;
import com.nakasato.ghstore.domain.filter.impl.OperatorFilter;
import com.nakasato.ghstore.domain.user.Administrator;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.Operator;
import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;
import com.nakasato.web.util.Redirector;

@ManagedBean( name = "loginMB" )
@SessionScoped
public class LoginMB extends BaseMB {

	private User loggedUser;

	private String username;
	private String password;

	public void doLogin() {
		try {
			boolean hasError = validateFields();
			if( ! hasError ) {
				FacesContext context = FacesContext.getCurrentInstance();
				String currentPage = context.getViewRoot().getViewId();

				User user = ( User ) context.getExternalContext().getSessionMap().get( User.LOGGED_USER );

				if( user == null ) {
					if( currentPage.contains( "/clientuser/login" ) ) {
						hasError = doCustomerLogin( username, context );
					} else if( currentPage.contains( "/admin/login" ) ) {
						hasError = doSysUserLogin( username, context );
					} else {
						hasError = true;
					}
				}
			}

			if( hasError ) {
				addMessage( "Usuário e/ou senha inválido(s)" );
			}

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}

	}

	private boolean doCustomerLogin( String username, FacesContext context ) throws ClassNotFoundException {
		boolean hasError = false;
		Customer customer = findCustomer( username );

		if( customer != null ) {
			if( customer.getPassword().equals( password ) ) {
				loggedUser = customer;
				context.getExternalContext().getSessionMap().put( User.LOGGED_USER, loggedUser );
				Redirector.redirectTo( context.getExternalContext(), "/clientuser/clientHome.jsf?faces-redirect=true" );
				addRedirectMessage( "Bem vindo " + loggedUser.getUsername() );
			} else {
				hasError = true;
			}
		} else {
			hasError = true;
		}

		return hasError;
	}

	private boolean doSysUserLogin( String username, FacesContext context ) throws ClassNotFoundException {
		boolean hasError = false;
		boolean isOk = false;
		Administrator admin = findAdministrator( username );

		if( admin != null ) {
			if( admin.getPassword().equals( password ) && admin.getActive() ) {
				loggedUser = admin;
				isOk = true;
			} else {
				hasError = true;
			}
		} else {
			// Se o usuário não for Administrador, verificar se é Operador
			Operator op = findOperator( username );

			if( op != null ) {
				if( op.getPassword().equals( password ) && op.getActive() ) {
					loggedUser = op;
					isOk = true;
				} else {
					hasError = true;
				}
			} else {
				hasError = true;
			}
		}

		if( isOk ) {
			context.getExternalContext().getSessionMap().put( User.LOGGED_USER, loggedUser );
			if( loggedUser.getFirstLogin() != null && loggedUser.getFirstLogin() ) {
				Redirector.redirectTo( context.getExternalContext(), "/admin/adminFirstLogin.jsf?faces-redirect=true" );
			} else {
				Redirector.redirectTo( context.getExternalContext(), "/admin/adminMain.jsf?faces-redirect=true" );
			}

			addRedirectMessage( "Bem vindo " + loggedUser.getUsername() );
		}

		return hasError;
	}

	private Customer findCustomer( String username ) throws ClassNotFoundException {
		Customer customer = null;
		CustomerFilter filter = new CustomerFilter();
		filter.setUserName( username );

		ICommand commandFind;
		commandFind = FactoryCommand.build( filter, EOperation.FIND );
		Result customerResult = commandFind.execute();
		List < Customer > customerList = customerResult.getEntityList();
		if( customerList != null && ! customerList.isEmpty() ) {
			customer = customerList.get( 0 );
		}

		return customer;
	}

	private Administrator findAdministrator( String username ) throws ClassNotFoundException {
		Administrator admin = null;
		AdministratorFilter filter = new AdministratorFilter();
		filter.setUserName( username );

		ICommand commandFind;
		commandFind = FactoryCommand.build( filter, EOperation.FIND );
		Result adminResult = commandFind.execute();
		List < Administrator > adminList = adminResult.getEntityList();
		if( adminList != null && ! adminList.isEmpty() ) {
			admin = adminList.get( 0 );
		}

		return admin;
	}

	private Operator findOperator( String username ) throws ClassNotFoundException {
		Operator op = null;
		OperatorFilter filter = new OperatorFilter();
		filter.setUserName( username );

		ICommand commandFind;
		commandFind = FactoryCommand.build( filter, EOperation.FIND );
		Result opResult = commandFind.execute();
		List < Operator > opList = opResult.getEntityList();
		if( opList != null && ! opList.isEmpty() ) {
			op = opList.get( 0 );
		}

		return op;
	}

	private boolean validateFields() {
		boolean isValid = true;
		if( StringUtils.isNotEmpty( username ) || StringUtils.isNotEmpty( password ) ) {
			isValid = false;
		}
		return isValid;
	}

	public void doLogout() {
		String redirectPage = null;
		FacesContext context = FacesContext.getCurrentInstance();
		User user = ( User ) context.getExternalContext().getSessionMap().get( User.LOGGED_USER );
		if( user != null && user instanceof Administrator ) {
			redirectPage = "/admin/login.jsf?faces-redirect=true";
		} else {
			redirectPage = "/clientuser/login.jsf?faces-redirect=true";
		}

		context.getExternalContext().getSessionMap().put( User.LOGGED_USER, null );
		context.getExternalContext().invalidateSession();
		Redirector.redirectTo( context.getExternalContext(), redirectPage );
	}

	public boolean isLoggedIn() {
		FacesContext context = FacesContext.getCurrentInstance();
		loggedUser = ( User ) context.getExternalContext().getSessionMap().get( User.LOGGED_USER );
		return loggedUser != null;
	}

	public User getLoggedUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		loggedUser = ( User ) context.getExternalContext().getSessionMap().get( User.LOGGED_USER );
		return loggedUser;
	}

	public void setLoggedUser( User loggedUser ) {
		this.loggedUser = loggedUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername( String username ) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword( String password ) {
		this.password = password;
	}

	@Override
	public void clearFilter() {
		// TODO Auto-generated method stub

	}

}
