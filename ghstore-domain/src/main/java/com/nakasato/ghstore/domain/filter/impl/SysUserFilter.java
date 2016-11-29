package com.nakasato.ghstore.domain.filter.impl;

import com.nakasato.ghstore.domain.filter.Filter;
import com.nakasato.ghstore.domain.user.UserType;

public class SysUserFilter extends Filter {

	private Boolean loadAddress;
	private String loginUserName;
	private String userName;
	private String name;
	private String cpf;
	private Boolean active;
	private UserType userType;

	public Boolean getLoadAddress() {
		return loadAddress;
	}

	public void setLoadAddress( Boolean loadAddress ) {
		this.loadAddress = loadAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName( String userName ) {
		this.userName = userName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive( Boolean active ) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName( String loginUserName ) {
		this.loginUserName = loginUserName;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType( UserType userType ) {
		this.userType = userType;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf( String cpf ) {
		this.cpf = cpf;
	}

}
