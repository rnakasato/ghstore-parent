package com.nakasato.ghstore.domain.user;

import java.util.Date;
import java.util.List;

public class User extends Person {
	public static final String LOGGED_USER = "loggedUser";

	private String username;
	private String password;
	private String email;
	private UserType userType;
	private Date updateDate;
	private List < Address > addressList;
	private Boolean firstLogin;
	private Boolean active;

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

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType( UserType userType ) {
		this.userType = userType;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate( Date updateDate ) {
		this.updateDate = updateDate;
	}

	public List < Address > getAddressList() {
		return addressList;
	}

	public void setAddressList( List < Address > addressList ) {
		this.addressList = addressList;
	}

	public Boolean getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin( Boolean firstLogin ) {
		this.firstLogin = firstLogin;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive( Boolean active ) {
		this.active = active;
	}

	public String getActiveDescription() {
		String desc = null;
		if( active != null ) {
			if(active){
				desc = "Ativo";
			}else{
				desc = "Inativo";
			}
		}
		return desc;
	}

}
