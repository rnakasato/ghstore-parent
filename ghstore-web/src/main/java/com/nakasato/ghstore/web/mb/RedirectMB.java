package com.nakasato.ghstore.web.mb;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.nakasato.ghstore.domain.Product;
import com.nakasato.web.util.Redirector;

@ManagedBean (name="redirectMB")
@RequestScoped
public class RedirectMB {
	@ManagedProperty(value="#{userSessionMB}")
	private UserSessionMB userSessionMB;
	
	
	public void redirectAfterPayment(){
		
		try {
			Thread thread = new Thread();
			thread.sleep(5000);			
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			String url = "/clientuser/clientHome.jsf?faces-redirect=true";
			Redirector.redirectTo(context, url);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void redirectBack(){
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String url = userSessionMB.getOldPage();
		Redirector.redirectTo(context, url);
	}
	
	public void redirectToProductMenu() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String url = "/admin/productMain.jsf?faces-redirect=true";
		Redirector.redirectTo(context, url);
	}
	
	public void redirectToProductSearch() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String url = "/admin/productSearch.jsf?faces-redirect=true";
		Redirector.redirectTo(context, url);
	}

	public void redirectToUpdate(Product product) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext context = ctx.getExternalContext();
		if(product != null && !product.isEmpty()){
			context.getFlash().put("product", product);
			String url = "/admin/productUpdate.jsf?faces-redirect=true";
			Redirector.redirectTo(context, url);
		}else{
			ctx.addMessage(null, new FacesMessage("Selecione um produto para alterar"));
		}
	}
	
	public void redirectToSaveProduct() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext context = ctx.getExternalContext();
		String url = "/admin/productSave.jsf?faces-redirect=true";
		Redirector.redirectTo(context, url);
	}
		
	public void redirectToProductPage(Product product){
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext context = ctx.getExternalContext();
		if(product != null && !product.isEmpty()){				
			context.getFlash().put("product", product);
			String url = "/clientuser/productPage.jsf?faces-redirect=true";
			Redirector.redirectTo(context, url);
		}else{
			ctx.addMessage(null, new FacesMessage("Selecione um produto para comprar"));
		}
	}

	public UserSessionMB getUserSessionMB() {
		return userSessionMB;
	}

	public void setUserSessionMB(UserSessionMB userSessionMB) {
		this.userSessionMB = userSessionMB;
	}
	
	
}
