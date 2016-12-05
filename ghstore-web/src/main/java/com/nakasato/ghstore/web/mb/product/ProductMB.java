package com.nakasato.ghstore.web.mb.product;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import com.nakasato.core.util.enums.EComparator;
import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.command.impl.Command;
import com.nakasato.ghstore.core.product.util.ProductSort;
import com.nakasato.ghstore.core.util.ImageUtils;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.core.util.OrderByType;
import com.nakasato.ghstore.core.util.SaveDirectory;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.filter.impl.SubcategoryFilter;
import com.nakasato.ghstore.domain.filter.impl.TagFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.domain.product.Subcategory;
import com.nakasato.ghstore.domain.product.Tag;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;
import com.nakasato.web.util.Redirector;

public abstract class ProductMB extends BaseMB implements Serializable {

	private static final long serialVersionUID = 1L;

	protected ProductFilter filter;

	protected String subcategory;
	protected Integer stock;
	protected String ImagePath;
	protected Product product;
	protected Integer status;

	protected List < StoreCategory > categoryList;
	protected List < Subcategory > subcategoryList;
	protected List < Product > productList;
	protected List < Tag > tagList;

	protected Integer priceRangeOption;

	protected Integer order;
	protected List < OrderByType > orderTypeList;

	protected boolean isAscendant;

	public ProductMB() {
	}

	protected void initProductOrderType() {
		orderTypeList = new ArrayList<>();
		orderTypeList.add( new OrderByType( EComparator.PRODUCT_NAME, "Nome" ) );
		orderTypeList.add( new OrderByType( EComparator.PRODUCT_CATEGORY, "Categoria" ) );
		orderTypeList.add( new OrderByType( EComparator.PRODUCT_PRICE, "Preço" ) );
		orderTypeList.add( new OrderByType( EComparator.PRODUCT_STATUS, "Status" ) );
		orderTypeList.add( new OrderByType( EComparator.PRODUCT_STOCK, "Estoque" ) );
		orderTypeList.add( new OrderByType( EComparator.PRODUCT_INSERTDATE, "Data de inserção" ) );
	}

	protected void initStoreCategory() {
		List < AbstractDomainEntity > ctList = null;
		try {
			ICommand commandFind = FactoryCommand.build( new StoreCategory(), EOperation.FINDALL );
			ctList = commandFind.execute().getEntityList();
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}

		if( ! ListUtils.isEmpty( ctList ) ) {
			categoryList = new ArrayList<>();
			for( AbstractDomainEntity e: ctList ) {
				StoreCategory s = ( StoreCategory ) e;
				categoryList.add( s );
			}
		}
	}

	public List < String > fillSubcategory( String query ) {
		List < String > acSubcategory = null;
		if( product != null && product.getStoreCategory() != null ) {
			SubcategoryFilter filter = new SubcategoryFilter();
			filter.setDescription( query );
			filter.setStoreCategory( product.getStoreCategory() );
			try {
				Command command;
				command = FactoryCommand.build( filter, EOperation.FIND );
				List < AbstractDomainEntity > scList = command.execute().getEntityList();
				acSubcategory = new ArrayList<>();
				if( ! ListUtils.isEmpty( scList ) ) {
					for( AbstractDomainEntity e: scList ) {
						Subcategory s = ( Subcategory ) e;
						acSubcategory.add( s.getDescription() );
					}
				}
			} catch( ClassNotFoundException e1 ) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return acSubcategory;
	}

	public List < Tag > fillTags( String query ) {
		List < Tag > tagList = null;
		TagFilter filter = new TagFilter();
		filter.setDescription( query );
		try {
			Command command;
			command = FactoryCommand.build( filter, EOperation.FIND );
			tagList = command.execute().getEntityList();
			boolean exists = false;

			if( ListUtils.isEmpty( tagList ) ) {
				tagList = new ArrayList<>();
			} else {
				for( Tag t: tagList ) {
					if( t.getDescription().equals( query ) ) {
						exists = true;
						break;
					}
				}
			}
			if( ! exists ) {
				Tag tag = new Tag();
				tag.setDescription( query );
				tagList.add( tag );
			}

		} catch( ClassNotFoundException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return tagList;
	}

	public void fillSubcategoryByCategory( StoreCategory storeCategory ) {
		SubcategoryFilter filter = new SubcategoryFilter();
		filter.setStoreCategory( storeCategory );

		try {
			Command command;
			command = FactoryCommand.build( filter, EOperation.FIND );

			List < AbstractDomainEntity > scList = command.execute().getEntityList();
			List < Subcategory > subcategoryList = new ArrayList<>();
			for( AbstractDomainEntity sc: scList ) {
				Subcategory subcat = ( Subcategory ) sc;
				subcategoryList.add( subcat );
			}
			this.subcategoryList = subcategoryList;
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public void doUpload( FileUploadEvent event ) {
		FacesMessage msg = new FacesMessage( "Arquivo salvo! ", event.getFile().getFileName() + " is uploaded." );
		FacesContext.getCurrentInstance().addMessage( null, msg );

		try {
			ImageUtils.copyImage( event.getFile().getFileName(), event.getFile().getInputstream() );
			String image = event.getFile().getFileName();
			product.setImage( image );
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}

	public void listProducts() {
		try {
			if( priceRangeOption != null ) {
				fillPriceRange();
			}

			Command command;
			command = FactoryCommand.build( filter, EOperation.FIND );
			List < Product > products = command.execute().getEntityList();

			if( products != null && ! products.isEmpty() ) {
				productList = new ArrayList<>();
				for( AbstractDomainEntity e: products ) {
					Product pr = ( Product ) e;
					if( status != null && status != 0 ) {
						if( pr.getStatus() == status ) {
							productList.add( pr );
						}
					} else {
						productList.add( pr );
					}
				}
				if( order != null ) {
					ProductSort.sortProducts( productList, order, isAscendant );
				}

			} else {
				productList = null;
			}
		} catch( ClassNotFoundException e1 ) {
			e1.printStackTrace();
		}
	}

	public abstract void fillPriceRange();

	public void save() {
		Product p = prepareProduct();
		p.setInsertDate( new Date() );
		try {
			Command command;
			p.setActive( true );
			command = FactoryCommand.build( p, EOperation.SAVE );
			Result result = command.execute();
			FacesContext ctx = FacesContext.getCurrentInstance();

			if( ! StringUtils.isEmpty( result.getMsg() ) ) {
				ctx.addMessage( null, new FacesMessage( result.getMsg(), result.getMsg() ) );
			} else {
				ctx.addMessage( null, new FacesMessage( "Produto cadastrado com código: " + p.getCode() ) );
				Flash flash = ctx.getExternalContext().getFlash();
				flash.setKeepMessages( true );
				flash.setRedirect( true );
				Redirector.redirectTo( ctx.getExternalContext(), "/admin/productSearch.jsf?faces-redirect=true" );
			}

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public void update() {
		Product p = prepareProduct();
		p.setId( product.getId() );
		if( stock != null ) {
			p.setStock( product.getStock() + stock );
		} else {
			p.setStock( product.getStock() );
		}

		p.setInsertDate( product.getInsertDate() );
		p.setWeight( product.getWeight() );
		p.setUpdateDate( new Date() );
		p.setCode( product.getCode() );
		p.setActive( product.getActive() );
		try {
			Command command;
			command = FactoryCommand.build( p, EOperation.UPDATE );
			Result result = command.execute();
			FacesContext ctx = FacesContext.getCurrentInstance();

			if( ! StringUtils.isEmpty( result.getMsg() ) ) {
				ctx.addMessage( null, new FacesMessage( result.getMsg(), result.getMsg() ) );
			} else {
				ctx.addMessage( null, new FacesMessage( "Produto alterado" ) );
				Flash flash = ctx.getExternalContext().getFlash();
				flash.setKeepMessages( true );

				flash.setRedirect( true );
				Redirector.redirectTo( ctx.getExternalContext(), "/admin/productSearch.jsf?faces-redirect=true" );
			}
		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if( product != null && ! product.isEmpty() ) {
			try {
				Command command;
				product.setActive( false );
				command = FactoryCommand.build( product, EOperation.UPDATE );
				Result result = command.execute();

				if( ! StringUtils.isEmpty( result.getMsg() ) ) {
					ctx.addMessage( null, new FacesMessage( "Erro ao excluir produto", result.getMsg() ) );
				} else {
					ctx.addMessage( null, new FacesMessage( "Produto excluído" ) );
					super.select( null );
				}
				product = null;
				listProducts();
			} catch( ClassNotFoundException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			ctx.addMessage( null, new FacesMessage( "Selecione um produto para excluir" ) );
		}
	}

	public void showProductDetails() {
		if( product != null && ! product.isEmpty() ) {
			RequestContext ctx = RequestContext.getCurrentInstance();
			ctx.execute( "PF('prodDialog').show()" );
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage( null, new FacesMessage( "Selecione um produto para ver detalhes" ) );
		}
	}

	/**
	 * Prepara produto para enviar para fachada
	 * 
	 * @return
	 */
	private Product prepareProduct() {
		Subcategory sc = new Subcategory();
		sc.setDescription( subcategory );
		sc.setStoreCategory( product.getStoreCategory() );
		product.setSubcategory( sc );
		product.setUpdateDate( new Date() );
		return product;
	}

	public String getImagePath() {

		if( product != null && product.getImage() != null ) {
			ImagePath = SaveDirectory.REQUEST_IMG_DIR + product.getImage();
		} else {
			ImagePath = SaveDirectory.REQUEST_IMG_DIR + "default.jpg";
		}

		return ImagePath;
	}

	public String getImagePath( Product product ) {
		String path;
		if( product != null && product.getImage() != null ) {
			path = SaveDirectory.REQUEST_IMG_DIR + product.getImage();
		} else {
			path = SaveDirectory.REQUEST_IMG_DIR + "default.jpg";
		}
		return path;
	}

	public String getProductImagePath() {
		return getImagePath();
	}

	public void clearFields() {
		product = new Product();
		product = new Product();
		subcategory = null;
		stock = null;
		tagList = null;
		priceRangeOption = 0;
	}

	public List < Product > getProductList() {
		return productList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus( Integer status ) {
		this.status = status;
	}

	public Integer getMinStock() {
		Integer oldStock = null;
		if( product != null && product.getStock() != null ) {
			oldStock = product.getStock() * - 1;
		}
		return oldStock;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct( Product product ) {
		if( product != null ) {
			this.product = product;
		}
	}

	public void clearTableResults() {
		product = null;
		productList = null;
		super.unSelect( null );
	}

	@Override
	public void clearFilter() {
		filter = new ProductFilter();
		listProducts();
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder( Integer order ) {
		this.order = order;
	}

	public List < OrderByType > getOrderTypeList() {
		return orderTypeList;
	}

	public List < Subcategory > getSubcategoryList() {
		return subcategoryList;
	}

	public ProductFilter getFilter() {
		return filter;
	}

	public void setFilter( ProductFilter filter ) {
		this.filter = filter;
	}

	public List < Tag > getTagList() {
		return tagList;
	}

	public void setTagList( List < Tag > tagList ) {
		this.tagList = tagList;
	}

	public Integer getPriceRangeOption() {
		return priceRangeOption;
	}

	public void setPriceRangeOption( Integer priceRangeOption ) {
		this.priceRangeOption = priceRangeOption;
	}

	public boolean isAscendant() {
		return isAscendant;
	}

	public void setAscendant( boolean isAscendant ) {
		this.isAscendant = isAscendant;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory( String subcategory ) {
		this.subcategory = subcategory;
	}

	public void setCategoryList( List < StoreCategory > categoryList ) {
		this.categoryList = categoryList;
	}

	public void setSubcategoryList( List < Subcategory > subcategoryList ) {
		this.subcategoryList = subcategoryList;
	}

	public void setProductList( List < Product > productList ) {
		this.productList = productList;
	}

	public void setImagePath( String imagePath ) {
		ImagePath = imagePath;
	}

	public void setOrderTypeList( List < OrderByType > orderTypeList ) {
		this.orderTypeList = orderTypeList;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock( Integer stock ) {
		this.stock = stock;
	}

	public List < StoreCategory > getCategoryList() {
		return categoryList;
	}

}
