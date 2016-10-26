package com.nakasato.ghstore.domain.user;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public class Phone extends AbstractDomainEntity {

	private String ddd;
	private String number;
	private User user;

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		if (number != null) {
			hash = number.hashCode() / 97;
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return super.isEmpty();
	}

}
