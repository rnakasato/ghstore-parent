package com.nakasato.ghstore.core.payment.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.nakasato.ghstore.domain.shopping.cart.ShoppingCart;
import com.nakasato.ghstore.domain.user.Address;

public class FreteUtil {
	public static final String CEP_DESTINO = "08751300";

	public static final String COD_SERV = "40010";

	private static final String FORMATO_CAIXA_PACOTE = "1";
	// private static final String FORMATO_ROLO_PRISMA = "2";
	// private static final String FORMATO_ENVELOPE = "3";

	private static final String MAO_PROPRIA = "N";
	private static final String SV_VALOR_DECLARADO = "0";

	private static SOAPMessage getShippingResponse( ShoppingCart cart ) {
		SOAPMessage soapResponse = null;
		try {

			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			String url = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx";
			soapResponse = soapConnection.call( createSOAPRequest( cart ), url );

			// printSOAPResponse(soapResponse);

			soapConnection.close();

		} catch( Exception e ) {
			e.printStackTrace();
		}
		return soapResponse;

	}

	private static SOAPMessage createSOAPRequest( ShoppingCart cart ) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		SOAPHeader header = soapMessage.getSOAPHeader();
		header.detachNode();

		String serverURI = "http://tempuri.org/";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration( "tem", serverURI );

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();

		SOAPElement calcPreco = soapBody.addChildElement( new QName( null, "CalcPreco", "tem" ) );

		SOAPElement nCdEmpresa = calcPreco.addChildElement( new QName( "http://tempuri.org/", "nCdEmpresa", "tem" ) );
		nCdEmpresa.addTextNode( "" );

		SOAPElement nDsSenha = calcPreco.addChildElement( new QName( "http://tempuri.org/", "nDsSenha", "tem" ) );
		nDsSenha.addTextNode( "" );

		SOAPElement nCdServico = calcPreco.addChildElement( new QName( "http://tempuri.org/", "nCdServico", "tem" ) );
		nCdServico.addTextNode( COD_SERV );

		SOAPElement sCepOrigem = calcPreco.addChildElement( new QName( "http://tempuri.org/", "sCepOrigem", "tem" ) );
		sCepOrigem.addTextNode( cart.getAddress().getFormattedCep() );

		SOAPElement sCepDestino = calcPreco.addChildElement( new QName( "http://tempuri.org/", "sCepDestino", "tem" ) );
		sCepDestino.addTextNode( CEP_DESTINO );

		SOAPElement nVlPeso = calcPreco.addChildElement( new QName( "http://tempuri.org/", "nVlPeso", "tem" ) );
		Double kg = cart.getTotalWeight() / 1000D;
		nVlPeso.addTextNode( kg.toString() );

		SOAPElement nCdFormato = calcPreco.addChildElement( new QName( "http://tempuri.org/", "nCdFormato", "tem" ) );
		nCdFormato.addTextNode( FORMATO_CAIXA_PACOTE );

		// VERIFICAR FORMA DE DEFINIR PESO, ALTURA, LARGURA
		SOAPElement nVlComprimento = calcPreco
				.addChildElement( new QName( "http://tempuri.org/", "nVlComprimento", "tem" ) );
		nVlComprimento.addTextNode( "50" );

		SOAPElement nVlAltura = calcPreco.addChildElement( new QName( "http://tempuri.org/", "nVlAltura", "tem" ) );
		nVlAltura.addTextNode( "50" );

		SOAPElement nVlLargura = calcPreco.addChildElement( new QName( "http://tempuri.org/", "nVlLargura", "tem" ) );
		nVlLargura.addTextNode( "11" );

		SOAPElement nVlDiametro = calcPreco.addChildElement( new QName( "http://tempuri.org/", "nVlDiametro", "tem" ) );
		nVlDiametro.addTextNode( "1" );

		SOAPElement sCdMaoPropria = calcPreco
				.addChildElement( new QName( "http://tempuri.org/", "sCdMaoPropria", "tem" ) );
		sCdMaoPropria.addTextNode( "N" );

		SOAPElement nVlValorDeclarado = calcPreco
				.addChildElement( new QName( "http://tempuri.org/", "nVlValorDeclarado", "tem" ) );
		nVlValorDeclarado.addTextNode( SV_VALOR_DECLARADO );

		SOAPElement sCdAvisoRecebimento = calcPreco
				.addChildElement( new QName( "http://tempuri.org/", "sCdAvisoRecebimento", "tem" ) );
		sCdAvisoRecebimento.addTextNode( MAO_PROPRIA );

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader( "SOAPAction", serverURI + "CalcPreco" );

		soapMessage.saveChanges();

		/* Print the request message */
		// System.out.print("Request SOAP Message = ");
		// soapMessage.writeTo(System.out);
		// System.out.println();

		return soapMessage;
	}

	/**
	 * Method used to print the SOAP Response
	 */
	private static void printSOAPResponse( SOAPMessage soapResponse ) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		System.out.print( "\nResponse SOAP Message = " );
		StreamResult result = new StreamResult( System.out );
		transformer.transform( sourceContent, result );
	}

	public static Double getShippingCost( ShoppingCart cart ) {

		Double value = 0D;
		if( cart.getShoppingCartList() != null && ! cart.getShoppingCartList().isEmpty() ) {
			SOAPMessage soapResponse = getShippingResponse( cart );

			try {

				Document document = soapResponse.getSOAPBody().extractContentAsDocument();
				File responseXML = new File( "output.xml" );
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				Result output = new StreamResult( responseXML );
				Source input = new DOMSource( document );

				transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
				transformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "2" );
				transformer.transform( input, output );

				String cost = "";
				Path path = Paths.get( responseXML.getPath() );
				List < String > xmlLines = Files.readAllLines( path );
				for( String line: xmlLines ) {
					if( line.contains( "Valor" ) ) {
						cost = line;
						break;
					}

				}
				cost = cost.replace( "<Valor>", "" );
				cost = cost.replace( "</Valor>", "" );
				cost = cost.replace( ",", "." );
				cost = cost.trim();

				value = Double.valueOf( cost );
			} catch( Exception e ) {
				e.printStackTrace();
			}

		}

		return value;

	}

	public static void main( String[] args ) throws Exception {
		ShoppingCart cart = new ShoppingCart();
		Address address = new Address();
		address.setCep( "08725640" );
		cart.setTotalWeight( 500L );
		cart.setAddress( address );
		System.out.println( getShippingCost( cart ) );

	}

}
