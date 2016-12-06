package com.nakasato.ghstore.core.payment.utils;

import java.math.BigDecimal;

import com.nakasato.ghstore.core.util.FormatUtils;
import com.nakasato.ghstore.domain.shopping.cart.ShoppingCart;
import com.nakasato.ghstore.domain.shopping.cart.ShoppingCartItem;
import com.nakasato.ghstore.domain.user.Address;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.User;

import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.checkout.Checkout;
import br.com.uol.pagseguro.enums.Currency;
import br.com.uol.pagseguro.enums.DocumentType;
import br.com.uol.pagseguro.enums.ShippingType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;

public class PaymentUtil {
	
	public static Double getTotalPayment( ShoppingCart shoppingCart ) {
		Double totalPayment = shoppingCart.getTotalValue();
		try {
			totalPayment += FreteUtil.getShippingCost( shoppingCart );
		} catch( Exception e ) {
			// Para propósitos de teste caso o WS não esteja disponível o Frete é igual a 0
		}
		return totalPayment;
	}

	public static String createPayment( ShoppingCart shoppingCart, User loggedUser ) {
		String response = "";
		Checkout checkout = new Checkout();
		for( ShoppingCartItem item: shoppingCart.getShoppingCartList() ) {
			Item paymentItem = new Item();
			paymentItem.setAmount(
					new BigDecimal( FormatUtils.formatToCurrencyNoSymbol( item.getProduct().getDiscountPrice() ) ) );
			paymentItem.setDescription( item.getProduct().getName() );
			paymentItem.setId( item.getProduct().getId().toString() );
			paymentItem.setQuantity( item.getAmount() );
			paymentItem.setWeight( shoppingCart.getTotalWeight() );// Peso em
																	// gramas
			checkout.addItem( paymentItem );
		}
		try {
			checkout.setShippingCost( new BigDecimal(
					FormatUtils.formatToCurrencyNoSymbol( FreteUtil.getShippingCost( shoppingCart ) ) ) );
		} catch( Exception e ) {
			// Para propósitos de teste caso o WS não esteja disponível o Frete é igual a 0
			checkout.setShippingCost( new BigDecimal( FormatUtils.formatToCurrencyNoSymbol( 0D ) ) );
		}

		checkout.setShippingAddress( "BRA", shoppingCart.getAddress().getCity().getUf(),
				shoppingCart.getAddress().getCity().getName(), shoppingCart.getAddress().getNeighborhood(),
				shoppingCart.getAddress().getCep(), shoppingCart.getAddress().getStreet(),
				shoppingCart.getAddress().getNumber().toString(), shoppingCart.getAddress().getComplement() );
		checkout.setShippingType( ShippingType.SEDEX );
		Customer user = ( Customer ) loggedUser;
		checkout.setSender( user.getName(), user.getEmail(), user.getPhoneList().get( 0 ).getDdd(),
				user.getPhoneList().get( 0 ).getNumber(), DocumentType.CPF, user.getCpf() );
		checkout.setCurrency( Currency.BRL );
		try {

			boolean onlyCheckoutCode = false;
			response = checkout.register( PagSeguroConfig.getAccountCredentials(), onlyCheckoutCode );

		} catch( PagSeguroServiceException e ) {

			System.err.println( e.getMessage() );
		}
		return response;
	}

	public static void main( String[] args ) throws Exception {
		ShoppingCart sc = new ShoppingCart();
		sc.setTotalValue( 199.90 );
		Address ad = new Address();
		ad.setCep( "08725640" );
		sc.setAddress( ad );
		System.out.println( getTotalPayment( sc ) );

	}

}
