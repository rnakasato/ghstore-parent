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
	private static final String CEP_ORIGEM = "08751300";
	private static final String PESO = "10.000"; // alterar posteriormente

	public static Double getTotalPayment( ShoppingCart shoppingCart ) {
		Double totalPayment = shoppingCart.getTotalValue();
		totalPayment += FreteUtil.getShippingCost( shoppingCart );
		return totalPayment;
	}

	public static String createPayment( ShoppingCart shoppingCart, User loggedUser ) {
		String response = "";
		Checkout checkout = new Checkout();
		for( ShoppingCartItem item: shoppingCart.getShoppingCartList() ) {
			Item paymentItem = new Item();
			paymentItem.setAmount(
					new BigDecimal( FormatUtils.formatToCurrencyNoSymbol( item.getProduct().getPrice() ) ) );
			paymentItem.setDescription( item.getProduct().getName() );
			paymentItem.setId( item.getProduct().getId().toString() );
			paymentItem.setQuantity( item.getAmount() );
			paymentItem.setWeight( shoppingCart.getTotalWeight() );// Peso em
																	// gramas
			checkout.addItem( paymentItem );
		}
		checkout.setShippingCost(
				new BigDecimal( FormatUtils.formatToCurrencyNoSymbol( FreteUtil.getShippingCost( shoppingCart ) ) ) );
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
