package com.nakasato.ghstore.web.mb.product;

import java.util.ArrayList;
import java.util.Date;

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
import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.web.util.Redirector;

@ManagedBean( name = "promotionSaveMB" )
@ViewScoped
public class PromotionSaveMB extends PromotionMB {

	@PostConstruct
	public void init() {
		newPromotion = new Promotion();
		newPromotion.setActive( false );
		initProductModel();
	}
	
	protected void initProductModel(){
		initSourceProducts(new ProductFilter());
		targetProducts = new ArrayList<>();
		productModel = new DualListModel<>( sourceProducts, targetProducts );		
	}

	public void save() {
		newPromotion.setProductList( productModel.getTarget() );
		try {
			ICommand commandSave = FactoryCommand.build( newPromotion, EOperation.SAVE );
			String msg = commandSave.execute().getMsg();
			if( StringUtils.isNotEmpty( msg ) ) {
				addMessage( msg );
			}else{
				FacesContext ctx = FacesContext.getCurrentInstance();
				ctx.addMessage( null, new FacesMessage( "Promoção \"" + newPromotion.getDescription() + "\" agendada com sucesso! " ) );
				Flash flash = ctx.getExternalContext().getFlash();
				flash.setKeepMessages( true );
				flash.setRedirect( true );
				
				
				Redirector.redirectTo( ctx.getExternalContext(), "/admin/promotionSearch.jsf?faces-redirect=true" );
			}
		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void setActive(){
		newPromotion.setActive( true );
		newPromotion.setStartDate( new Date() );
	}
	
	public void clearFields(){
		newPromotion = new Promotion();
		newPromotion.setActive( false );
	}

}
