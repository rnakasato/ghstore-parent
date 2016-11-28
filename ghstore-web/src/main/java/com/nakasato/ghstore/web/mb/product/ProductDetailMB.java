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
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.Wishlist;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.user.LoginMB;

@ManagedBean( name = "productDetailMB" )
@ViewScoped
public class ProductDetailMB extends ProductMB {

	@ManagedProperty( value = "#{loginMB}" )
	private LoginMB loginMB;

	private Customer customer;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer amount;
	private Integer maxValue;

	private String code;

	@PostConstruct
	public void init() {
		customer = ( Customer ) loginMB.getLoggedUser();
	}

	public void loadProduct() {
		try {
			if( ! StringUtils.isEmpty( code ) && product == null ) {
				ProductFilter filter = new ProductFilter();
				filter.setCode( code );
				ICommand commandFind;
				commandFind = FactoryCommand.build( filter, EOperation.FIND );
				List < Product > productList = commandFind.execute().getEntityList();
				if( productList != null && ! productList.isEmpty() ) {
					product = productList.get( 0 );
					amount = 1;
					maxValue = product.getStock();
				}

			}

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}

	}

	public boolean existWishlist() {
		boolean exists = false;

		if( customer != null ) {
			List < Wishlist > wishList = customer.getWishList();
			if( ListUtils.isEmpty( wishList ) ) {
				wishList = new ArrayList<>();
				customer.setWishList( wishList );
			} else {
				for( Wishlist w: wishList ) {
					if( w.getProduct().equals( this.product ) ) {
						exists = true;
						break;
					}
				}
			}
		}
		return exists;

	}

	public void addWishlist() {
		boolean exists = existWishlist();

		try {
			if( customer != null ) {

				if( exists ) {
					addMessage( "O item ja está na lista de desejo" );
				} else {
					Wishlist wish = new Wishlist();
					wish.setProduct( product );
					wish.setCustomer( customer );
					
					List<Wishlist> wishList = customer.getWishList();
					wishList.add( wish );

					ICommand commandUpdate = FactoryCommand.build( customer, EOperation.UPDATE );
					Result result = commandUpdate.execute();

					if( StringUtils.isNotEmpty( result.getMsg() ) ) {
						addMessage( result.getMsg() );
					} else {
						addMessage( "Produto adicionado à lista de desejos" );
					}
				}

			}
		} catch( ClassNotFoundException e ) {
			addMessage( "Erro inesperado" );
			e.printStackTrace();
		}
	}

	public void removeWishlist() {

		try {
			if( customer != null ) {
				List < Wishlist > wishList = customer.getWishList();
				Wishlist removedItem = null;

				if( ListUtils.isEmpty( wishList ) ) {
					wishList = new ArrayList<>();
				} else {
					for( Wishlist w: wishList ) {
						if( w.getProduct().equals( this.product ) ) {
							removedItem = w;
							break;
						}
					}
				}

				if( removedItem == null ) {
					addMessage( "Item não existe na lista de desejos" );
				} else {

					wishList.remove( removedItem );
					ICommand commandDelete = FactoryCommand.build( removedItem, EOperation.DELETE );
					Result result = commandDelete.execute();

					if( StringUtils.isNotEmpty( result.getMsg() ) ) {
						addMessage( result.getMsg() );
					} else {
						addMessage( "Produto removido da lista de desejos" );
					}
				}
			}

		} catch( ClassNotFoundException e ) {
			addMessage( "Erro inesperado" );
			e.printStackTrace();
		}
	}

	public void addAmount() {
		if( amount == null ) {
			amount = 1;
		}
		if( amount < product.getStock() ) {
			amount += 1;
		}
	}

	public void removeAmount() {
		if( amount == null ) {
			amount = 1;
		}
		if( amount > 1 ) {
			amount -= 1;
		}
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount( Integer amount ) {
		this.amount = amount;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue( Integer maxValue ) {
		this.maxValue = maxValue;
	}

	public void resetPage() {
		amount = 1;
	}

	public String getCode() {
		return code;
	}

	public void setCode( String code ) {
		this.code = code;
	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB( LoginMB loginMB ) {
		this.loginMB = loginMB;
	}

	@Override
	public void fillPriceRange() {
		// TODO Auto-generated method stub

	}

}
