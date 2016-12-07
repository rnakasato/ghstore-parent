package com.nakasato.ghstore.web.mb.util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghstore.domain.user.User;
import com.nakasato.ghstore.web.mb.session.UserSessionMB;
import com.nakasato.web.util.Redirector;

@ManagedBean( name = "redirectMB" )
@RequestScoped
public class RedirectMB {
	@ManagedProperty( value = "#{userSessionMB}" )
	private UserSessionMB userSessionMB;

	public void redirectAfterPayment() {

		try {
			Thread thread = new Thread();
			thread.sleep( 5000 );
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			String url = "/clientuser/clientHome.jsf?faces-redirect=true";
			Redirector.redirectTo( context, url );
		} catch( InterruptedException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void redirectBack() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String url = userSessionMB.getOldPage();
		Redirector.redirectTo( context, url );
	}

	public void redirectToProductMenu() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String url = "/admin/productSearch.jsf?faces-redirect=true";
		Redirector.redirectTo( context, url );
	}

	public void redirect( String page ) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String redirect = "?faces-redirect=true";
		Redirector.redirectTo( context, page + redirect );
	}

	public void redirectToProductSearch() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String url = "/admin/productSearch.jsf?faces-redirect=true";
		Redirector.redirectTo( context, url );
	}

	public void redirectToUpdate( Product product ) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext context = ctx.getExternalContext();
		if( product != null && ! product.isEmpty() ) {
			context.getFlash().put( "product", product );
			String url = "/admin/productUpdate.jsf?faces-redirect=true";
			Redirector.redirectTo( context, url );
		} else {
			ctx.addMessage( null, new FacesMessage( "Selecione um produto para alterar" ) );
		}
	}

	public void redirectToSaveProduct() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext context = ctx.getExternalContext();
		String url = "/admin/productSave.jsf?faces-redirect=true";
		Redirector.redirectTo( context, url );
	}

	public void redirectToUpdatePromotion( Promotion promotion ) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext context = ctx.getExternalContext();
		if( promotion != null && ! promotion.isEmpty() ) {
			context.getFlash().put( "promotion", promotion );
			String url = "/admin/promotionUpdate.jsf?faces-redirect=true";
			Redirector.redirectTo( context, url );
		} else {
			ctx.addMessage( null, new FacesMessage( "Selecione uma promoção para alterar" ) );
		}
	}

	public void redirectToSavePromotion() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext context = ctx.getExternalContext();
		String url = "/admin/promotionSave.jsf?faces-redirect=true";
		Redirector.redirectTo( context, url );
	}

	public void redirectToPromotionSearch() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext context = ctx.getExternalContext();
		String url = "/admin/promotionSearch.jsf?faces-redirect=true";
		Redirector.redirectTo( context, url );
	}

	public void redirectToProductPage( Product product ) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext context = ctx.getExternalContext();
		if( product != null && ! product.isEmpty() ) {
			HttpServletRequest request = ( HttpServletRequest ) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			String uri = request.getRequestURI();
			uri = uri.replace( "ghstore-web/", "" );
			userSessionMB.setOldPage( uri );

			context.getFlash().put( "product", product );
			StringBuilder sb = new StringBuilder();
			sb.append( "/clientuser/productPage.jsf?faces-redirect=true" );
			sb.append( "&productCode=" );
			sb.append( product.getCode() );

			String url = sb.toString();
			Redirector.redirectTo( context, url );

		} else {
			ctx.addMessage( null, new FacesMessage( "Selecione um produto para comprar" ) );
		}
	}

	public void redirectToGlobalSearch( String productName ) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext context = ctx.getExternalContext();

		HttpServletRequest request = ( HttpServletRequest ) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String uri = request.getRequestURI();
		uri = uri.replace( "ghstore-web/", "" );
		userSessionMB.setOldPage( uri );

		context.getFlash().put( "productName", productName );

		StringBuilder sb = new StringBuilder();
		sb.append( "/clientuser/globalSearch.jsf?faces-redirect=true" );

		String url = sb.toString();
		Redirector.redirectTo( context, url );

	}

	public void redirectToUpdate( User user ) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext context = ctx.getExternalContext();
		if( user != null && ! user.isEmpty() ) {
			context.getFlash().put( "user", user );
			String url = "/admin/userUpdate.jsf?faces-redirect=true";
			Redirector.redirectTo( context, url );
		} else {
			ctx.addMessage( null, new FacesMessage( "Selecione um usuário para alterar" ) );
		}
	}

	public void redirectToSaveUser() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext context = ctx.getExternalContext();
		String url = "/admin/userSave.jsf?faces-redirect=true";
		Redirector.redirectTo( context, url );
	}

	public UserSessionMB getUserSessionMB() {
		return userSessionMB;
	}

	public void setUserSessionMB( UserSessionMB userSessionMB ) {
		this.userSessionMB = userSessionMB;
	}

}
