package com.nakasato.ghstore.web.mb.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.command.impl.Command;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.core.util.SaveDirectory;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.TagFilter;
import com.nakasato.ghstore.domain.filter.impl.WishlistFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.domain.product.Subcategory;
import com.nakasato.ghstore.domain.product.Tag;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.Wishlist;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;
import com.nakasato.ghstore.web.mb.user.LoginMB;

@ManagedBean( name = "wishlistMB" )
@ViewScoped
public class WishlistMB extends BaseMB {

	@ManagedProperty( value = "#{loginMB}" )
	private LoginMB loginMB;

	private WishlistFilter filter;

	private List < StoreCategory > categoryList;
	private List < Subcategory > subcategoryList;
	private String ImagePath;
	private List < Tag > tagList;

	private List < Wishlist > wishList;
	private Wishlist wish;
	
	private Customer customer;

	@PostConstruct
	public void init() {
		filter = new WishlistFilter();
		initStoreCategory();
		initSubcategory();
		
		customer = (Customer) loginMB.getLoggedUser();
	}
	
	protected void initStoreCategory(){
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
	
	protected void initSubcategory(){
		List < AbstractDomainEntity > ctList = null;
		try {
			ICommand commandFind = FactoryCommand.build( new Subcategory(), EOperation.FINDALL );
			ctList = commandFind.execute().getEntityList();
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}

		if( ! ListUtils.isEmpty( ctList ) ) {
			subcategoryList = new ArrayList<>();
			for( AbstractDomainEntity e: ctList ) {
				Subcategory s = ( Subcategory ) e;
				subcategoryList.add( s );
			}
		}
	}


	public void listWishlist() {
		try {
			filter.setCustomer( customer );
			ICommand commandFind = FactoryCommand.build( filter, EOperation.FIND );
			wishList = commandFind.execute().getEntityList();		
			
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}
	

	public void removeWishlist( Wishlist wish ) {

		try {
			if( wish != null ) {

				ICommand commandUpdate = FactoryCommand.build( wish, EOperation.DELETE );
				Result result = commandUpdate.execute();

				if( StringUtils.isNotEmpty( result.getMsg() ) ) {
					addMessage( result.getMsg() );
				} else {
					addMessage( "Produto removido da lista de desejos" );
				}
			}
			listWishlist();
		} catch( ClassNotFoundException e ) {
			addMessage( "Erro inesperado" );
			e.printStackTrace();
		}
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

	public String getImagePath( Wishlist wishlist ) {
		String path;
		if( wishlist != null ) {
			Product product = wishlist.getProduct();
			path = SaveDirectory.REQUEST_IMG_DIR + product.getImage();
		} else {
			path = "default.jpg";
		}
		return path;
	}

	// Getters e Setters
	public WishlistFilter getFilter() {
		return filter;
	}

	public void setFilter( WishlistFilter filter ) {
		this.filter = filter;
	}

	public List < StoreCategory > getCategoryList() {
		return categoryList;
	}

	public void setCategoryList( List < StoreCategory > categoryList ) {
		this.categoryList = categoryList;
	}

	public List < Subcategory > getSubcategoryList() {
		return subcategoryList;
	}

	public void setSubcategoryList( List < Subcategory > subcategoryList ) {
		this.subcategoryList = subcategoryList;
	}

	public String getImagePath() {
		return ImagePath;
	}

	public void setImagePath( String imagePath ) {
		ImagePath = imagePath;
	}

	public List < Tag > getTagList() {
		return tagList;
	}

	public void setTagList( List < Tag > tagList ) {
		this.tagList = tagList;
	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB( LoginMB loginMB ) {
		this.loginMB = loginMB;
	}

	public List < Wishlist > getWishList() {
		return wishList;
	}

	public void setWishList( List < Wishlist > wishList ) {
		this.wishList = wishList;
	}

	public Wishlist getWish() {
		return wish;
	}

	public void setWish( Wishlist wish ) {
		this.wish = wish;
	}

	@Override
	public void clearFilter() {
		filter = new WishlistFilter();

	}
}
