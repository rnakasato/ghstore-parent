package com.nakasato.ghstore.web.mb.product;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DualListModel;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.web.util.Redirector;

@ManagedBean( name = "promotionUpdateMB" )
@ViewScoped
public class PromotionUpdateMB extends PromotionMB {

	@PostConstruct
	public void init() {
		selectedPromotion = ( Promotion ) FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.get( "promotion" );
		if( selectedPromotion != null ) {
			targetProducts = selectedPromotion.getProductList();
		}
		initProductModel();
	}

	@Override
	protected void initSourceProducts(ProductFilter filter) {
		filter.setNotInList( selectedPromotion.getProductList() );
		super.initSourceProducts(filter);
	}

	public void update() {
		try {
			selectedPromotion.setProductList( productModel.getTarget() );
			ICommand commandUpdate = FactoryCommand.build( selectedPromotion, EOperation.UPDATE );
			String msg = commandUpdate.execute().getMsg();
			if( StringUtils.isNotEmpty( msg ) ) {
				addMessage( msg );
			}else{
				FacesContext ctx = FacesContext.getCurrentInstance();
				ctx.addMessage( null, new FacesMessage( "Promoção \"" + newPromotion.getDescription() + "\" alterada com sucesso! " ) );
				Flash flash = ctx.getExternalContext().getFlash();
				flash.setKeepMessages( true );
				flash.setRedirect( true );
				
				Redirector.redirectTo( ctx.getExternalContext(), "/admin/promotionSearch.jsf?faces-redirect=true" );
			}
			// Criar validador de campos obrigatórios de promoção
		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
