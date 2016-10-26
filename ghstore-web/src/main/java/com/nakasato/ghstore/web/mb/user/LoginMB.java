package com.nakasato.ghstore.web.mb.user;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.domain.filter.impl.CustomerFilter;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;
import com.nakasato.web.util.Redirector;

@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB extends BaseMB {

	private User loggedUser;

	private String username;
	private String password;

	public void doLogin() {
		try {
			boolean hasError = validateFields();
			if (!hasError) {
				FacesContext context = FacesContext.getCurrentInstance();
				String currentPage = context.getViewRoot().getViewId();

				User user = (User) context.getExternalContext().getSessionMap().get(User.LOGGED_USER);

				if (user == null) {
					if (currentPage.contains("/clientuser/login.xhtml")) {
						CustomerFilter filter = new CustomerFilter();
						filter.setUserName(username);

						ICommand commandFind;
						commandFind = FactoryCommand.build(filter, EOperation.FIND);
						Result customerResult = commandFind.execute();
						List<Customer> customerList = customerResult.getEntityList();
						if (customerList != null && !customerList.isEmpty()) {
							Customer customer = customerList.get(0);
							if (customer.getPassword().equals(password)) {
								loggedUser = customer;
								context.getExternalContext().getSessionMap().put(User.LOGGED_USER, loggedUser);
								Redirector.redirectTo(context.getExternalContext(),
										"/clientuser/clientHome.jsf?faces-redirect=true");
								addRedirectMessage("Bem vindo " + loggedUser.getUsername());
							} else {
								hasError = true;
							}

						} else {
							hasError = true;
						}

					} else if (currentPage.contains("/admin/login.xhtml")) {

					} else {
						hasError = true;
					}
				}
			}

			if (hasError) {
				addMessage("Usuário e/ou senha inválido(s)");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private boolean validateFields() {
		boolean isValid = true;
		if (StringUtils.isNotEmpty(username) || StringUtils.isNotEmpty(password)) {
			isValid = false;
		}
		return isValid;
	}

	public void doLogout() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put(User.LOGGED_USER, null);
		context.getExternalContext().invalidateSession();
		Redirector.redirectTo(context.getExternalContext(), "/clientuser/login.jsf?faces-redirect=true");
	}

	public boolean isLoggedIn() {
		FacesContext context = FacesContext.getCurrentInstance();
		loggedUser = (User) context.getExternalContext().getSessionMap().get(User.LOGGED_USER);
		return loggedUser != null;
	}

	public User getLoggedUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		loggedUser = (User) context.getExternalContext().getSessionMap().get(User.LOGGED_USER);
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void clearFilter() {
		// TODO Auto-generated method stub
		
	}

	
}
