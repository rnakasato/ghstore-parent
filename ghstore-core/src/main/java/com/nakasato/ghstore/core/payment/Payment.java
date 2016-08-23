package com.nakasato.ghstore.core.payment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.nakasato.ghstore.core.util.FormatUtils;
import com.nakasato.ghstore.domain.ShoppingCart;

public class Payment {
	private static final String CEP_ORIGEM = "08751300";
	private static final String PESO = "10.000"; // alterar posteriormente
	
	public static Double getTotalPayment(ShoppingCart shoppingCart){
		Double totalPayment = shoppingCart.getTotalValue();
		totalPayment += getShippingCost(shoppingCart);
		return totalPayment;
	}
	
	public static Double getShippingCost(ShoppingCart shoppingCart){
		HttpURLConnection connection = null;
		Double shippingValue = null;
		try{
			URL url;
			// prepara URL do webservice de cálculo de frete
			StringBuilder urlBld = new StringBuilder("http://developers.agenciaideias.com.br/correios/frete/xml");
			urlBld.append("/").append(CEP_ORIGEM);
			urlBld.append("/").append(shoppingCart.getAddress().getCep());
			urlBld.append("/").append(PESO);
			urlBld.append("/").append(FormatUtils.formatToCurrencyNoSymbol(shoppingCart.getTotalValue()));
			url = new URL(urlBld.toString());
			
			// le o xml retornado pelo webservice
			connection = (HttpURLConnection) url.openConnection();
			InputStream content = connection.getInputStream();
			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource(content);
			Document doc = db.parse(is);
			// Lê o nó sedex 
			NodeList nodes = doc.getElementsByTagName("sedex");
			// Recupera o elemento 0 (o nó não possui subnós)
			Element sedexNode = (Element) nodes.item(0);
			// Recupera o valor do nó <sedex>valor do nó</sedex?
			String sedexVal = sedexNode.getFirstChild().getNodeValue();
			shippingValue = Double.parseDouble(sedexVal);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			connection.disconnect();
		}
		return shippingValue;
	}
//	public static String createPayment(ShoppingCart shoppingCart, User loggedUser){
//		String response = "";
//		Checkout checkout = new Checkout();
//		for(ShoppingCartItem item : shoppingCart.getShoppingCartList()){
//			Item paymentItem = new Item();
//			paymentItem.setAmount(new BigDecimal(FormatUtils.formatToCurrencyNoSymbol(item.getProduct().getPrice())));
//			paymentItem.setDescription(item.getProduct().getName());
//			paymentItem.setId(item.getProduct().getId().toString());
//			paymentItem.setQuantity(item.getAmount());
//			paymentItem.setWeight(1000L);// Definir peso para os produtos
//			checkout.addItem(paymentItem);
//		}
//		checkout.setShippingCost(new BigDecimal(FormatUtils.formatToCurrencyNoSymbol(getShippingCost(shoppingCart))));
//		checkout.setShippingAddress(
//				"BRA",
//				shoppingCart.getAddress().getCity().getUf(), 
//				shoppingCart.getAddress().getCity().getName(),
//				shoppingCart.getAddress().getNeighborhood(),
//				shoppingCart.getAddress().getCep(),
//				shoppingCart.getAddress().getStreet(),
//				shoppingCart.getAddress().getNumber().toString(),
//				shoppingCart.getAddress().getComplement()
//				);
//		checkout.setShippingType(ShippingType.SEDEX);
//		Customer user = (Customer)loggedUser;
//		checkout.setSender(
//			user.getName(),
//			user.getEmail(),
//			user.getPhoneList().get(0).getDdd().toString(),
//			user.getPhoneList().get(0).getNumber().toString(),
//			DocumentType.CPF,
//			user.getCpf()
//		);
//		checkout.setCurrency(Currency.BRL);
//		try {  
//			  
//		  boolean onlyCheckoutCode = false;  
//		  response = checkout.register(PagSeguroConfig.getAccountCredentials(), onlyCheckoutCode); 
//		    
//		} catch (PagSeguroServiceException e) {  
//			  
//		    System.err.println(e.getMessage());  
//		}  
//		return response;
//	}
//	
//	public static void main(String[] args) {
//		ShoppingCart sc = new ShoppingCart();
//		sc.setTotalValue(199.90);
//		Address ad = new Address();
//		ad.setCep("08725640");
//		sc.setAddress(ad);
//		System.out.println(getTotalPayment(sc));
//		
//	}
	
}
