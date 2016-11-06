package com.nakasato.ghstore.web.mb.session;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.core.util.Parser;
import com.nakasato.ghstore.domain.carrier.PaymentCreationCarrier;
import com.nakasato.ghstore.domain.filter.impl.DiscountCouponFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.productexchange.DiscountCoupon;
import com.nakasato.ghstore.domain.shopping.cart.ShoppingCart;
import com.nakasato.ghstore.domain.shopping.cart.ShoppingCartItem;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;
import com.nakasato.ghstore.web.mb.user.LoginMB;
import com.nakasato.web.util.Redirector;

@ ManagedBean( name ="userSessionMB" )
@ SessionScoped
public class UserSessionMB extends BaseMB {
	/**
	 * 
	 */
	private static final long serialVersionUID =1L;

	@ ManagedProperty( value ="#{loginMB}" )
	protected LoginMB loginMB;

	private Customer loggedUser;
	private ShoppingCart cart;
	private ShoppingCartItem selectedItem;
	private ShoppingCartItem changeItem;
	private String oldPage;
	private String newPage;

	private Integer newAmount;
	private Address newAddress;

	private String transactionCode;

	private boolean useCoupon;

	// total sem desconto para caso desmarque opção de desconto
	private Double temporaryTotal;

	@ PostConstruct
	public void init() {
		if( cart ==null ) {
			cart =new ShoppingCart();
			cart.setShoppingCartList( new ArrayList<>() );
			cart.setTotalValue( 0d );
			cart.setTotalWeight( 0L );
			cart.setProcess( false );
		}

		FacesContext context =FacesContext.getCurrentInstance();
		loggedUser =( Customer ) loginMB.getLoggedUser();
		useCoupon =false;
	}

	public void setLogged() {
		loggedUser =( Customer ) loginMB.getLoggedUser();
		if( loggedUser !=null ) {
			cart.setOwner( loggedUser );
		}
	}

	/**
	 * Utilizado para carregar os valores totais antes de selecionar o valor
	 */
	public void setFirstAddress() {
		loggedUser =( Customer ) loginMB.getLoggedUser();
		if( loggedUser !=null &&cart.getAddress() ==null ) {
			cart.setAddress( loggedUser.getDeliveryAddressList().get( 0 ) );
		}
	}

	/**
	 * Utilizado para carregar os valores totais antes de selecionar o valor
	 */
	public void setNotUseCoupon() {
		useCoupon =false;
	}

	public void removeItem( ShoppingCartItem cartItem ) {
		cart.getShoppingCartList().remove( cartItem );
		if( useCoupon ) {
			cart.setTotalValue( new Double( temporaryTotal ) );
		}
		cart.setTotalValue( cart.getTotalValue() -cartItem.getTotalValue() );
		cart.setTotalWeight( cart.getTotalWeight() -cartItem.getTotalWeigth() );

	}

	public void setUpdateItem( ShoppingCartItem cartItem ) {
		changeItem =cartItem;
		newAmount =new Integer( cartItem.getAmount() );
	}

	public void saveNewItemAmount() {

		Double totalValue =null;
		// recupera peso e valor total
		totalValue =changeItem.getTotalValue();

		Long totalWeight =changeItem.getTotalWeigth().longValue();
		changeItem.setAmount( newAmount );

		// remove o peso e o valor total do item do carrinho
		Double cartValue =null;
		if( useCoupon ) {
			cartValue =temporaryTotal;
		} else {
			cartValue =cart.getTotalValue();
		}

		Long cartWeight =cart.getTotalWeight();

		cartValue -=totalValue;
		cartWeight -=totalWeight;

		// recalcula totais do item
		totalValue =changeItem.getProduct().getPrice() *newAmount;
		totalWeight =changeItem.getProduct().getWeight().longValue() *newAmount;

		cartValue +=totalValue;
		cartWeight +=totalWeight;
		if( newAmount ==0 ) {
			cart.getShoppingCartList().remove( changeItem );
		} else {
			changeItem.setTotalValue( totalValue );
			changeItem.setTotalWeigth( totalWeight );
		}

		cart.setTotalValue( cartValue );
		cart.setTotalWeight( cartWeight );
	}

	public void addAmount() {
		if( newAmount <changeItem.getProduct().getStock() ) {
			newAmount =newAmount +1;
		}
	}

	public void removeAmount() {
		if( newAmount >0 ) {
			newAmount =newAmount -1;
		}
	}

	public void finishPayment() {
		if( !loginMB.isLoggedIn() ) {
			addMessage( "Login obrigatório para finalizar pagamento!" );
		} else {
			try {
				if( useCoupon ) {
					cart.setDiscount( true );
				} else {
					cart.setDiscount( false );
				}
				if( this.cart !=null && !ListUtils.isListEmpty( cart.getShoppingCartList() ) ) {

					PaymentCreationCarrier carrier =new PaymentCreationCarrier();
					carrier.setLoggedUser( loggedUser );
					carrier.setCart( cart );
					ICommand commandFind =FactoryCommand.build( carrier, EOperation.FIND );
					commandFind.execute();

					String pagseguroPage =carrier.getPaymentAddress();
					cart.setProcess( true );
					Redirector.redirectToExternalPage( FacesContext.getCurrentInstance().getExternalContext(),
							pagseguroPage );
				} else {
					FacesContext.getCurrentInstance().addMessage( null,
							new FacesMessage( "Não há itens no carrinho de compras" ) );
				}
			} catch( Exception e ) {
				e.printStackTrace();
			}

		}
	}

	public void setDiscount() {
		if( useCoupon ) {
			if( cart.getTotalValue() !=null ) {
				temporaryTotal =new Double( cart.getTotalValue() );
				cart.setTotalValue( new Double( temporaryTotal -( temporaryTotal *0.1 ) ) );
			}
		} else {
			cart.setTotalValue( new Double( temporaryTotal ) );
			temporaryTotal =null;
		}
	}

	public void processOrder() {

		if( StringUtils.isNotEmpty( transactionCode ) &&cart.isProcess() ) {
			try {
				Order order =Parser.parseShoppingCartToOrder( cart );
				order.setTransactionCode( transactionCode );
				ICommand commandSave =FactoryCommand.build( order, EOperation.SAVE );
				Result result =commandSave.execute();
				if( StringUtils.isNotEmpty( result.getMsg() ) ) {
					addMessage( result.getMsg() );
				} else {
					cart =new ShoppingCart();
					cart.setShoppingCartList( new ArrayList<>() );
					cart.setTotalValue( 0d );
					cart.setTotalWeight( 0L );
					cart.setOwner( loggedUser );
					cart.setProcess( false );
					Address address =loggedUser.getDeliveryAddressList().get( 0 );

					cart.setAddress( address );
					transactionCode =null;
					useCoupon =false;
				}

			} catch( ClassNotFoundException e ) {
				e.printStackTrace();
			}
		}
	}

	public void addCartItem( Product product, Integer amount ) {
		FacesMessage msg;

		if( amount >0 ) {
			List < ShoppingCartItem > itemList =cart.getShoppingCartList();
			boolean alreadyExists =false;
			int index =0;
			for( ShoppingCartItem item: itemList ) {
				if( item.getProduct().getId() ==product.getId() ) {
					alreadyExists =true;
					break;
				}
				index ++;
			}

			// remove peso e valor do total do carrinho
			Double totalValue =0D;
			if( temporaryTotal !=null ) {
				totalValue =temporaryTotal;
			} else {
				totalValue =cart.getTotalValue();
			}

			if( alreadyExists ) {
				ShoppingCartItem cartItem =itemList.get( index );

				cart.setTotalValue( totalValue -cartItem.getTotalValue() );
				cart.setTotalWeight( cart.getTotalWeight() -cartItem.getTotalWeigth() );

				// carrega novos valores do item
				Integer totalAmount =cartItem.getAmount() +amount;
				cartItem.setAmount( totalAmount );
				cartItem.setTotalValue( totalAmount *cartItem.getProduct().getPrice() );
				Long weight =cartItem.getProduct().getWeight().longValue();
				cartItem.setTotalWeigth( totalAmount *weight );

				cart.setTotalValue( totalValue +cartItem.getTotalValue() );
				cart.setTotalWeight( cart.getTotalWeight() +cartItem.getTotalWeigth() );
			} else {
				ShoppingCartItem cartItem =new ShoppingCartItem();
				cartItem.setAmount( amount );
				cartItem.setProduct( product );
				cartItem.setTotalValue( amount *product.getPrice() );

				Long weight =product.getWeight().longValue();
				cartItem.setTotalWeigth( amount *weight );

				cart.addItem( cartItem );

				cart.setTotalValue( totalValue +cartItem.getTotalValue() );

				cart.setTotalWeight( cart.getTotalWeight() +cartItem.getTotalWeigth() );

			}
			temporaryTotal =new Double( cart.getTotalValue() );

			StringBuilder message =new StringBuilder();
			message.append( "Produto adicionado no carrinho de compras" );
			msg =new FacesMessage( "Produto adicionado ao carrinho" );
			FacesContext.getCurrentInstance().addMessage( null, msg );
		} else {
			msg =new FacesMessage( "Deve ser adicionado ao menos uma unidade do produto para adicionar ao carrinho" );
			FacesContext.getCurrentInstance().addMessage( null, msg );
		}
	}

	public String couponTotal() {
		String total =null;
		try {
			if( loggedUser !=null &&loggedUser.getCoupons() !=null ) {
				DiscountCouponFilter filter =new DiscountCouponFilter();
				filter.setCustomerId( loggedUser.getId() );
				filter.setUsed( false );
				ICommand commandFind =FactoryCommand.build( filter, EOperation.FIND );
				List < DiscountCoupon > coupons =commandFind.execute().getEntityList();
				Integer totalCoupon =coupons.size();
				total =totalCoupon.toString();
			} else {
				total ="0";
			}
		} catch( ClassNotFoundException e ) {
			total ="0";
			e.printStackTrace();
		}

		return total;
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart( ShoppingCart cart ) {
		this.cart =cart;
	}

	public ShoppingCartItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem( ShoppingCartItem selectedItem ) {
		this.selectedItem =selectedItem;
	}

	public Customer getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser( Customer loggedUser ) {
		this.loggedUser =loggedUser;
	}

	public String getOldPage() {
		return oldPage;
	}

	public void setOldPage( String oldPage ) {
		this.oldPage =oldPage;
	}

	public String getNewPage() {
		return newPage;
	}

	public void setNewPage( String newPage ) {
		this.newPage =newPage;
	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB( LoginMB loginMB ) {
		this.loginMB =loginMB;
	}

	public ShoppingCartItem getChangeItem() {
		return changeItem;
	}

	public void setChangeItem( ShoppingCartItem changeItem ) {
		this.changeItem =changeItem;
	}

	public Integer getNewAmount() {
		return newAmount;
	}

	public void setNewAmount( Integer newAmount ) {
		this.newAmount =newAmount;
	}

	public Address getNewAddress() {
		return newAddress;
	}

	public void setNewAddress( Address newAddress ) {
		this.newAddress =newAddress;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode( String transactionCode ) {
		this.transactionCode =transactionCode;
	}

	@ Override
	public void clearFilter() {
		// TODO Auto-generated method stub

	}

	public boolean isUseCoupon() {
		return useCoupon;
	}

	public void setUseCoupon( boolean useCoupon ) {
		this.useCoupon =useCoupon;
	}

}
