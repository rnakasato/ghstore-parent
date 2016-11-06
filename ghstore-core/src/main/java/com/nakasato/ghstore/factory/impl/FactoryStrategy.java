package com.nakasato.ghstore.factory.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.IStrategy;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.carrier.PaymentCreationCarrier;
import com.nakasato.ghstore.domain.filter.impl.CustomerFilter;
import com.nakasato.ghstore.domain.filter.impl.OrderFilter;
import com.nakasato.ghstore.domain.filter.impl.ProductExchangeFilter;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.filter.impl.ProductReturnFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.productexchange.ProductExchange;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghtstore.core.business.complementor.ComplementCustomer;
import com.nakasato.ghtstore.core.business.complementor.ComplementCustomerCoupon;
import com.nakasato.ghtstore.core.business.complementor.ComplementCustomerUpdate;
import com.nakasato.ghtstore.core.business.complementor.ComplementDiscountOrder;
import com.nakasato.ghtstore.core.business.complementor.ComplementInsertDate;
import com.nakasato.ghtstore.core.business.complementor.ComplementOrderProductStock;
import com.nakasato.ghtstore.core.business.complementor.ComplementProductCode;
import com.nakasato.ghtstore.core.business.complementor.ComplementProductExchange;
import com.nakasato.ghtstore.core.business.complementor.ComplementProductExchangeProductStock;
import com.nakasato.ghtstore.core.business.complementor.ComplementProductReturn;
import com.nakasato.ghtstore.core.business.complementor.ComplementProductReturnProductStock;
import com.nakasato.ghtstore.core.business.complementor.ComplementTags;
import com.nakasato.ghtstore.core.business.filler.PaymentCreationFiller;
import com.nakasato.ghtstore.core.business.filler.StoreCategoryFiller;
import com.nakasato.ghtstore.core.business.filler.SubcategoryFiller;
import com.nakasato.ghtstore.core.business.filler.TagSaveUpdateFiller;
import com.nakasato.ghtstore.core.business.validator.CustomerCEPValidator;
import com.nakasato.ghtstore.core.business.validator.EmailValidator;
import com.nakasato.ghtstore.core.business.validator.PriceValidator;
import com.nakasato.ghtstore.core.business.validator.ProductExchangeAmountValidator;
import com.nakasato.ghtstore.core.business.validator.ProductReturnAmountValidator;
import com.nakasato.ghtstore.core.business.validator.StockValidator;
import com.nakasato.ghtstore.core.business.validator.StoreCategoryValidator;
import com.nakasato.ghtstore.core.business.validator.TransactionCodeValidator;
import com.nakasato.ghtstore.core.business.validator.UserBirthDateValidator;
import com.nakasato.ghtstore.core.business.validator.UserCPFValidator;
import com.nakasato.ghtstore.core.business.validator.UserPhoneValidator;
import com.nakasato.ghtstore.core.business.validator.fields.ProductExchangeRequiredFieldsValidator;
import com.nakasato.ghtstore.core.business.validator.fields.ProductRequiredFieldsValidator;
import com.nakasato.ghtstore.core.business.validator.fields.ProductReturnRequiredFieldsValidator;
import com.nakasato.ghtstore.core.business.validator.fields.UserRequiredFieldsValidator;

public class FactoryStrategy {

	/**
	 * Mapa para conter as regras de neg�cio de todas opera��es por entidade; O
	 * valor � um mapa que de regras de neg�cio indexado pela opera��o A Chave �
	 * o nome da entidade referente �s regras de neg�cio e o valor � um mapa
	 * contendo todos os Strategies referentes � cada opera��o
	 * (salvar,alterar,consultar,excluir)
	 */
	private static Map < String, Map < String, List < IStrategy > > > rns;

	private static Map < String, List < IStrategy > > rnsProduct;
	private static Map < String, List < IStrategy > > rnsCustomer;
	private static Map < String, List < IStrategy > > rnsOrder;
	private static Map < String, List < IStrategy > > rnsProductReturn;
	private static Map < String, List < IStrategy > > rnsProductExchange;

	private static Map < String, List < IStrategy > > rnsPaymentCreationCarrier;

	public static List < IStrategy > build( AbstractDomainEntity entity, String operation ) {
		if( rns ==null ) {
			initMap();
		}
		List < IStrategy > operationRules =new ArrayList<>();
		Map < String, List < IStrategy > > entityRules =rns.get( entity.getClass().getName() );
		if( entityRules !=null ) {
			operationRules =entityRules.get( operation );
		}
		return operationRules;
	}

	private static void initMap() {
		// inicializa��o do mapa de regras de neg�cio total
		rns =new HashMap<>();

		// Inicializa��o do mapa de regras de neg�cio do produto
		rnsProduct =new HashMap<>();
		rns.put( Product.class.getName(), rnsProduct );
		rns.put( ProductFilter.class.getName(), rnsProduct );
		initProductRns();

		// Inicializa��o do mapa de regras de neg�cio do usu�rio
		rnsCustomer =new HashMap<>();
		rns.put( Customer.class.getName(), rnsCustomer );
		rns.put( CustomerFilter.class.getName(), rnsCustomer );
		initCustomerRns();

		rnsOrder =new HashMap<>();
		rns.put( Order.class.getName(), rnsOrder );
		rns.put( OrderFilter.class.getName(), rnsOrder );
		initOrderRns();

		rnsProductReturn =new HashMap<>();
		rns.put( ProductReturn.class.getName(), rnsProductReturn );
		rns.put( ProductReturnFilter.class.getName(), rnsProductReturn );
		initProductReturnRns();

		rnsProductExchange =new HashMap<>();
		rns.put( ProductExchange.class.getName(), rnsProductExchange );
		rns.put( ProductExchangeFilter.class.getName(), rnsProductExchange );
		initProductExchangeRns();

		rnsPaymentCreationCarrier =new HashMap<>();
		rns.put( PaymentCreationCarrier.class.getName(), rnsPaymentCreationCarrier );
		initPaymentCreationRns();

	}

	private static void initPaymentCreationRns() {
		List < IStrategy > rnsFind =new ArrayList<>();
		rnsFind.add( new PaymentCreationFiller() );
		rnsPaymentCreationCarrier.put( EOperation.FIND, rnsFind );
	}

	private static void initCustomerRns() {
		List < IStrategy > rnsSave =new ArrayList<>();
		List < IStrategy > rnsUpdate =new ArrayList<>();
		// N�o h� regras para a busca de usu�rio
		List < IStrategy > rnsFind =new ArrayList<>();
		List < IStrategy > rnsDelete =new ArrayList<>();

		// Adicionando regras de neg�cio para salvar um Usu�rio
		rnsSave.add( new UserRequiredFieldsValidator() );
		rnsSave.add( new UserCPFValidator() );
		rnsSave.add( new UserBirthDateValidator() );
		rnsSave.add( new CustomerCEPValidator() );
		rnsSave.add( new UserPhoneValidator() );
		rnsSave.add( new EmailValidator() );
		rnsSave.add( new ComplementCustomer() );
		// Verificar se Nome de usu�rio e CPF j� existem

		rnsUpdate.add( new UserCPFValidator() );
		rnsUpdate.add( new CustomerCEPValidator() );
		rnsUpdate.add( new ComplementCustomerUpdate() );

		// Insere as regras de neg�cio por opera��o
		rnsCustomer.put( EOperation.SAVE, rnsSave );
		rnsCustomer.put( EOperation.UPDATE, rnsUpdate );
		rnsCustomer.put( EOperation.DELETE, rnsDelete );
		rnsCustomer.put( EOperation.FIND, rnsFind );

	}

	private static void initOrderRns() {
		List < IStrategy > rnsSave =new ArrayList<>();
		List < IStrategy > rnsUpdate =new ArrayList<>();
		// N�o h� regras para a busca de usu�rio
		List < IStrategy > rnsFind =new ArrayList<>();
		List < IStrategy > rnsDelete =new ArrayList<>();

		// Adicionando regras de neg�cio para salvar um Pedido
		rnsSave.add( new TransactionCodeValidator() );
		rnsSave.add( new ComplementOrderProductStock() );
		rnsSave.add( new ComplementDiscountOrder() );

		// Insere as regras de neg�cio por opera��o
		rnsOrder.put( EOperation.SAVE, rnsSave );
		rnsOrder.put( EOperation.UPDATE, rnsUpdate );
		rnsOrder.put( EOperation.DELETE, rnsDelete );
		rnsOrder.put( EOperation.FIND, rnsFind );

	}

	private static void initProductReturnRns() {
		List < IStrategy > rnsSave =new ArrayList<>();
		List < IStrategy > rnsUpdate =new ArrayList<>();
		// N�o h� regras para a busca de usu�rio
		List < IStrategy > rnsFind =new ArrayList<>();
		List < IStrategy > rnsDelete =new ArrayList<>();

		// Adicionando regras de neg�cio para salvar uma devolu��o
		rnsSave.add( new ProductReturnRequiredFieldsValidator() );
		rnsSave.add( new ProductReturnAmountValidator() );
		rnsSave.add( new ComplementProductReturn() );
		rnsSave.add( new ComplementProductReturnProductStock() );

		// Insere as regras de neg�cio por opera��o
		rnsProductReturn.put( EOperation.SAVE, rnsSave );
		rnsProductReturn.put( EOperation.UPDATE, rnsUpdate );
		rnsProductReturn.put( EOperation.DELETE, rnsDelete );
		rnsProductReturn.put( EOperation.FIND, rnsFind );

	}

	private static void initProductExchangeRns() {
		List < IStrategy > rnsSave =new ArrayList<>();
		List < IStrategy > rnsUpdate =new ArrayList<>();
		// N�o h� regras para a busca de usu�rio
		List < IStrategy > rnsFind =new ArrayList<>();
		List < IStrategy > rnsDelete =new ArrayList<>();

		// Adicionando regras de neg�cio para salvar uma devolu��o
		rnsSave.add( new ProductExchangeRequiredFieldsValidator() );
		rnsSave.add( new ProductExchangeAmountValidator() );
		rnsSave.add( new ComplementProductExchange() );
		rnsSave.add( new ComplementProductExchangeProductStock() );
		rnsSave.add( new ComplementCustomerCoupon() );

		// Insere as regras de neg�cio por opera��o
		rnsProductExchange.put( EOperation.SAVE, rnsSave );
		rnsProductExchange.put( EOperation.UPDATE, rnsUpdate );
		rnsProductExchange.put( EOperation.DELETE, rnsDelete );
		rnsProductExchange.put( EOperation.FIND, rnsFind );

	}

	private static void initProductRns() {

		List < IStrategy > rnsSave =new ArrayList<>();
		List < IStrategy > rnsUpdate =new ArrayList<>();
		// N�o h� regras para a busca de produto
		List < IStrategy > rnsFind =new ArrayList<>();
		List < IStrategy > rnsDelete =new ArrayList<>();

		// Adicionando regras de neg�cio para salvar um Product
		rnsSave.add( new ProductRequiredFieldsValidator() );
		rnsSave.add( new StockValidator() );
		rnsSave.add( new PriceValidator() );
		rnsSave.add( new StoreCategoryValidator() );
		rnsSave.add( new StoreCategoryFiller() );
		rnsSave.add( new SubcategoryFiller() );
		rnsSave.add( new ComplementTags() );
		rnsSave.add( new TagSaveUpdateFiller() );
		rnsSave.add( new ComplementInsertDate() );
		rnsSave.add( new ComplementProductCode() );

		// Adicionando regras de neg�cio para alterar um Product
		rnsUpdate.add( new ProductRequiredFieldsValidator() );
		rnsUpdate.add( new StockValidator() );
		rnsUpdate.add( new PriceValidator() );
		rnsUpdate.add( new StoreCategoryValidator() );
		rnsUpdate.add( new StoreCategoryFiller() );
		rnsUpdate.add( new SubcategoryFiller() );
		rnsUpdate.add( new ComplementTags() );
		rnsUpdate.add( new TagSaveUpdateFiller() );

		// Adicionando regras de neg�cio para buscar um Product
		// rnsFind.add(new StoreCategoryFiller());
		// rnsFind.add(new SubcategoryFiller());

		// Insere as regras de neg�cio por opera��o
		rnsProduct.put( EOperation.SAVE, rnsSave );
		rnsProduct.put( EOperation.UPDATE, rnsUpdate );
		rnsProduct.put( EOperation.DELETE, rnsDelete );
		rnsProduct.put( EOperation.FIND, rnsFind );
	}
}
