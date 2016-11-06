package com.nakasato.ghstore.domain;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractDomainEntity implements IEntity, Serializable {

	private Integer id;
	private Date insertDate;
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId( Integer id ) {
		this.id =id;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate( Date insertDate ) {
		this.insertDate =insertDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description =description;
	}

	@ Override
	public int hashCode() {
		final int prime =31;
		int result =1;
		result =prime *result +( ( getId() ==null ) ? 0: getId().hashCode() );
		return result;
	}

	@ Override
	public boolean equals( Object obj ) {
		if( this ==obj ) {
			return true;
		}
		if( obj ==null ) {
			return false;
		}
		if( getClass() !=obj.getClass() ) {
			return false;
		}
		AbstractDomainEntity other =( AbstractDomainEntity ) obj;
		if( id ==null ) {
			if( other.id !=null )
				return false;
		} else if( !id.equals( other.id ) )
			return false;
		return true;
	}

	public boolean isEmpty() {
		boolean result =false;
		if( id ==null ) {
			result =true;
		}
		return result;
	}

}
