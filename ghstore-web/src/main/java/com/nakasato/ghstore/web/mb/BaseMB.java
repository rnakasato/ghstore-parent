package com.nakasato.ghstore.web.mb;

import java.io.Serializable;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.nakasato.ghstore.core.filter.Filter;

public abstract class BaseMB implements Serializable {
	private static final long serialVersionUID = 1L;

	// @ManagedProperty(value="#{userSessionMB}")
	// protected UserSessionMB userSessionMB;
	private Filter baseFilter;
	private Boolean isSelected;

	public Filter getBaseFilter() {
		return baseFilter;
	}

	public void setBaseFilter(Filter filter) {
		this.baseFilter = filter;
	}
	
	public void select(SelectEvent event){
		isSelected = true;
	}
	
	public void unSelect(UnselectEvent event){
		isSelected = false;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	

	// public void setPageUrl(String page){
	// if(this.userSessionMB.getOldPage() != null &&
	// this.userSessionMB.getNewPage() != null){
	// if(!this.userSessionMB.getOldPage().equals(userSessionMB.getNewPage())){
	// userSessionMB.setOldPage(userSessionMB.getNewPage());
	// }
	// userSessionMB.setNewPage(page);
	// }else{
	// userSessionMB.setOldPage(page);
	// userSessionMB.setNewPage(page);
	// }
	// }

}
