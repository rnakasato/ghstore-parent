package com.nakasato.ghstore.domain;

public class Phone extends AbstractDomainEntity {

	private Integer ddd;
	private Integer number;

	public Integer getDdd() {
		return ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return super.isEmpty();
	}

}
