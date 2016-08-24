package com.nakasato.ghstore.web.mb;

import java.io.Serializable;

import javax.faces.bean.ManagedProperty;

import com.nakasato.ghstore.core.filter.Filter;

public abstract class BaseMB implements Serializable {
	private static final long serialVersionUID = 1L;	
	
//	@ManagedProperty(value="#{userSessionMB}")
//	protected UserSessionMB userSessionMB;
	private Filter baseFilter;
	
	public Filter getBaseFilter() {
		return baseFilter;
	}

	public void setBaseFilter(Filter filter) {
		this.baseFilter = filter;
	}
	
//	public void setPageUrl(String page){
//		if(this.userSessionMB.getOldPage() != null && this.userSessionMB.getNewPage() != null){
//			if(!this.userSessionMB.getOldPage().equals(userSessionMB.getNewPage())){
//				userSessionMB.setOldPage(userSessionMB.getNewPage());				
//			}
//			userSessionMB.setNewPage(page);
//		}else{
//			userSessionMB.setOldPage(page);
//			userSessionMB.setNewPage(page);
//		}
//	}

}
