package com.nakasato.ghstore.domain.user;

import java.io.Serializable;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public class Address extends AbstractDomainEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private City city;
	private String neighborhood;
	private String street;
	private String complement;
	private Integer number;
	private String cep;

	public City getCity() {
		return city;
	}

	public void setCity( City city ) {
		this.city = city;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood( String neighborhood ) {
		this.neighborhood = neighborhood;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet( String street ) {
		this.street = street;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement( String complement ) {
		this.complement = complement;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber( Integer number ) {
		this.number = number;
	}

	public String getCep() {
		return cep;
	}

	public String getFormattedCep() {
		return cep.replace( "-", "" );
	}

	public void setCep( String cep ) {
		this.cep = cep;
	}

	public String getFormattedAddress() {
		String formattedAddress = null;
		StringBuilder sb = new StringBuilder();
		if( getStreet() != null && getCity() != null && getCity().getState() != null ) {
			sb.append( getStreet() );
			sb.append( "," );
			sb.append( getNeighborhood() );
			sb.append( "," );
			sb.append( getCity().getName() );
			sb.append( "," );
			sb.append( getCity().getState().getAcronym() );
			formattedAddress = sb.toString();
		}

		return formattedAddress;
	}

	@Override
	public boolean isEmpty() {

		return super.isEmpty();
	}

}
