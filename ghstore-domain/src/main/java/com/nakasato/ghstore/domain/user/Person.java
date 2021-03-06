package com.nakasato.ghstore.domain.user;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.text.MaskFormatter;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

public abstract class Person extends AbstractDomainEntity {

	private String name;
	private String cpf;
	private Date birthDate;
	private String sex;
	private List < Phone > phoneList;

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public String getFormattedCPF() {
		String pattern = "###.###.###-##";
		MaskFormatter mf;
		try {
			mf = new MaskFormatter( pattern );
			mf.setValueContainsLiteralCharacters( false );
			return mf.valueToString( cpf );
		} catch( ParseException ex ) {
			return cpf;
		}
	}

	public void setCpf( String cpf ) {
		this.cpf = cpf;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate( Date birthDate ) {
		this.birthDate = birthDate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex( String sex ) {
		this.sex = sex;
	}

	public List < Phone > getPhoneList() {
		return phoneList;
	}

	public void setPhoneList( List < Phone > phoneList ) {
		this.phoneList = phoneList;
	}

}
