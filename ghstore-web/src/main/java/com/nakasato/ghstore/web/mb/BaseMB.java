package com.nakasato.ghstore.web.mb;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.nakasato.ghstore.domain.filter.Filter;

public abstract class BaseMB implements Serializable {

	private static final long serialVersionUID =1L;

	private Filter baseFilter;
	private Boolean isSelected;

	public Filter getBaseFilter() {
		return baseFilter;
	}

	public void setBaseFilter( Filter filter ) {
		this.baseFilter =filter;
	}

	public void select( SelectEvent event ) {
		isSelected =true;
	}

	public void unSelect( UnselectEvent event ) {
		isSelected =false;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public abstract void clearFilter();

	public void addMessage( String msg ) {
		FacesContext context =FacesContext.getCurrentInstance();
		context.addMessage( null, new FacesMessage( msg ) );
	}

	public void addRedirectMessage( String msg ) {
		FacesContext context =FacesContext.getCurrentInstance();
		ExternalContext ext =context.getExternalContext();
		ext.getFlash().setKeepMessages( true );
		ext.getFlash().setRedirect( true );
		context.addMessage( null, new FacesMessage( msg ) );
	}

	public Date getToday() {
		return new Date();
	}

}
