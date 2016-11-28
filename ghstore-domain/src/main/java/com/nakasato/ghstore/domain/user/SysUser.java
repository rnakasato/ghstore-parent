package com.nakasato.ghstore.domain.user;

import java.util.List;

public class SysUser extends User {
	private List < Address > addressList;
	private Boolean firstLogin;
	private Boolean active;

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

}
