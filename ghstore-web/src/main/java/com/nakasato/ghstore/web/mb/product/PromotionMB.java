package com.nakasato.ghstore.web.mb.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DualListModel;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.util.SaveDirectory;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.filter.impl.PromotionFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;

@ManagedBean( name = "promotionMB" )
@ViewScoped
public class PromotionMB extends BaseMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected PromotionFilter filter;

	protected Promotion selectedPromotion;
	protected List < Promotion > promotionList;

	// Produtos contidos no pickList que estão selecionáveis
	protected List < Product > sourceProducts;

	// Produtos contidos no pickList que estão no target
	protected List < Product > targetProducts;

	// Produtos selecionados no picklist
	protected List < Product > selectedProducts;

	protected List < StoreCategory > categoryList;

	protected List < Product > filterProducts;

	protected DualListModel < Product > productModel;

	// NA INICIALIZACAO DE ALTERACAO FAZER O FINDALL en sourceProducts E DEPOIS
	// sourceProducts.remove(targetProducts)

	// Utilizado para recuperar o valor do filtro de status
	// como há problema ao realizar o set(null) em valor
	// booleano foi adicionado o status como String inicialmente
	protected String promotionStatus;

	protected Promotion newPromotion;

	@PostConstruct
	public void init() {
		selectedPromotion = null;
		filter = new PromotionFilter();
		initCategory();
		initProducts();
	}

	public void listPromotion() {
		try {
			setBooleanFilter();

			ICommand commandFind = FactoryCommand.build( filter, EOperation.FIND );
			promotionList = commandFind.execute().getEntityList();

		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void initSourceProducts(ProductFilter filter) {
		try {
			ICommand commandFind = FactoryCommand.build( filter, EOperation.FINDALL );
			sourceProducts = commandFind.execute().getEntityList();
		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void initProductModel() {
		initSourceProducts(new ProductFilter());
		if( targetProducts == null ) {
			targetProducts = new ArrayList<>();
		}
		productModel = new DualListModel<>( sourceProducts, targetProducts );
	}

	protected void initProducts() {
		try {
			ICommand commandFind = FactoryCommand.build( new Product(), EOperation.FINDALL );
			filterProducts = commandFind.execute().getEntityList();
		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void initCategory() {
		try {
			ICommand commandFind = FactoryCommand.build( new StoreCategory(), EOperation.FINDALL );
			categoryList = commandFind.execute().getEntityList();
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}

	}

	protected void setBooleanFilter() {
		if( StringUtils.isNotEmpty( promotionStatus )
				&& ( promotionStatus.equals( "true" ) || promotionStatus.equals( "false" ) ) ) {
			Boolean status = Boolean.valueOf( promotionStatus );
			filter.setActive( status );
		}else{
			filter.setActive( null );
		}
	}
	
	public boolean notAllowUpdate() {
		boolean notAllow = true;
		if( selectedPromotion != null && selectedPromotion.getEndDate().after( getToday() ) ) {
			notAllow = false;
		}
		return notAllow;
	}

	public Double promotionValue( Product product ) {
		Double promotionValue = 0D;
		if( selectedPromotion != null ) {
			Double discount = product.getPrice() * selectedPromotion.getDiscountPercentage() / 100;
			promotionValue = product.getPrice() - discount;
		}
		return promotionValue;
	}

	public void cancel() {
		try {
			selectedPromotion.setActive( false );
			selectedPromotion.setCancel( true );
			ICommand commandUpdate = FactoryCommand.build( selectedPromotion, EOperation.UPDATE );
			String msg = commandUpdate.execute().getMsg();
			if( StringUtils.isNotEmpty( msg ) ) {
				addMessage( msg );
			}

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public String getImagePath( Product product ) {
		String path;
		if( product != null ) {
			path = SaveDirectory.REQUEST_IMG_DIR + product.getImage();
		} else {
			path = "default.jpg";
		}
		return path;
	}

	@Override
	public void clearFilter() {
		filter = new PromotionFilter();
	}

	// Getters e Setters
	public PromotionFilter getFilter() {
		return filter;
	}

	public void setFilter( PromotionFilter filter ) {
		this.filter = filter;
	}

	public Promotion getSelectedPromotion() {
		return selectedPromotion;
	}

	public void setSelectedPromotion( Promotion selectedPromotion ) {
		this.selectedPromotion = selectedPromotion;
	}

	public List < Promotion > getPromotionList() {
		return promotionList;
	}

	public void setPromotionList( List < Promotion > promotionList ) {
		this.promotionList = promotionList;
	}

	public List < Product > getSourceProducts() {
		return sourceProducts;
	}

	public void setSourceProducts( List < Product > sourceProducts ) {
		this.sourceProducts = sourceProducts;
	}

	public List < Product > getTargetProducts() {
		return targetProducts;
	}

	public void setTargetProducts( List < Product > targetProducts ) {
		this.targetProducts = targetProducts;
	}

	public List < Product > getFilterProducts() {
		return filterProducts;
	}

	public void setFilterProducts( List < Product > filterProducts ) {
		this.filterProducts = filterProducts;
	}

	public Promotion getNewPromotion() {
		return newPromotion;
	}

	public void setNewPromotion( Promotion newPromotion ) {
		this.newPromotion = newPromotion;
	}

	public String getPromotionStatus() {
		return promotionStatus;
	}

	public void setPromotionStatus( String promotionStatus ) {
		this.promotionStatus = promotionStatus;
	}

	public List < StoreCategory > getCategoryList() {
		return categoryList;
	}

	public void setCategoryList( List < StoreCategory > categoryList ) {
		this.categoryList = categoryList;
	}

	public DualListModel < Product > getProductModel() {
		return productModel;
	}

	public void setProductModel( DualListModel < Product > productModel ) {
		this.productModel = productModel;
	}

	public List < Product > getSelectedProducts() {
		return selectedProducts;
	}

	public void setSelectedProducts( List < Product > selectedProducts ) {
		this.selectedProducts = selectedProducts;
	}

}
