package com.nakasato.ghstore.web.mb.user;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.domain.user.Administrator;
import com.nakasato.ghstore.domain.user.Operator;
import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.adapter.UserToAdmininistratorAdapter;
import com.nakasato.ghstore.web.adapter.UserToOperatorAdapter;
import com.nakasato.ghstore.web.mb.BaseMB;
import com.nakasato.web.util.Redirector;

@ManagedBean( name = "firstLoginMB" )
@ViewScoped
public class FirstLoginMB extends BaseMB {

	@ManagedProperty( value = "#{loginMB}" )
	private LoginMB loginMB;

	private User user;

	private String passwordConfirmation;
	
	private UserToAdmininistratorAdapter adapterToAdmin;
	private UserToOperatorAdapter adapterToOperator;

	@PostConstruct
	public void init() {
		user = loginMB.getLoggedUser();

	}

	public void update() {
		boolean hasError = false;
		try {
			boolean samePassword = confirmPassword( user );
			if( ! samePassword ) {
				addMessage( "A confirmação da senha difere da senha" );
			} else {
				ICommand command = null;
				user.setFirstLogin( false );
				if( user instanceof Administrator ) {
					Administrator admin = (Administrator) user ;
					command = FactoryCommand.build( admin, EOperation.UPDATE );
				} else if(user instanceof Operator){
					Operator op = (Operator) user ;
					command = FactoryCommand.build( op, EOperation.UPDATE );
				}else{
					addMessage( "Usuário inválido" );
					hasError = true;
				}
					
				if(!hasError){
					String msg = command.execute().getMsg();
					if( StringUtils.isNotEmpty( msg ) ) {
						addMessage( msg );
					} else {
						addMessage( "Senha definitiva salva com sucesso!" );
						
						FacesContext ctx = FacesContext.getCurrentInstance();
						Redirector.redirectTo( ctx.getExternalContext(), "/admin/adminMain.jsf?faces-redirect=true" );
					}					
				}

			}

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}

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
		// TODO Auto-generated method stub

	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB( LoginMB loginMB ) {
		this.loginMB = loginMB;
	}

	public User getUser() {
		return user;
	}

	public void setUser( User user ) {
		this.user = user;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation( String passwordConfirmation ) {
		this.passwordConfirmation = passwordConfirmation;
	}

}
