package com.nakasato.ghstore.domain.shopping.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.Customer;

/**
 * @author rafae Descrição:
 */
public class ShoppingCart extends AbstractDomainEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List < ShoppingCartItem > shoppingCartList;
	private Double totalValue;
	private Long totalWeight;
	private Address address;
	private Customer owner;
	private boolean process;
	private boolean discount;

	public List < ShoppingCartItem > getShoppingCartList() {
		return shoppingCartList;
	}

	public void setShoppingCartList( List < ShoppingCartItem > shoppingCartList ) {
		this.shoppingCartList = shoppingCartList;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue( Double totalValue ) {
		this.totalValue = totalValue;
	}

	public void addItem( ShoppingCartItem cartItem ) {
		if( shoppingCartList == null ) {
			shoppingCartList = new ArrayList<>();
		}
		shoppingCartList.add( cartItem );
	}

	public void removeItem( ShoppingCartItem cartItem ) {
		if( shoppingCartList != null ) {
			shoppingCartList.remove( cartItem );
		}
	}

	public Customer getOwner() {
		return owner;
	}

	public void setOwner( Customer owner ) {
		this.owner = owner;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress( Address address ) {
		this.address = address;
	}

	public Long getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight( Long totalWeight ) {
		this.totalWeight = totalWeight;
	}

	public boolean isProcess() {
		return process;
	}

	public void setProcess( boolean process ) {
		this.process = process;
	}

	public boolean isDiscount() {
		return discount;
	}

	public void setDiscount( boolean discount ) {
		this.discount = discount;
	}

	@Override
	public boolean isEmpty() {

		return false;
	}

}
