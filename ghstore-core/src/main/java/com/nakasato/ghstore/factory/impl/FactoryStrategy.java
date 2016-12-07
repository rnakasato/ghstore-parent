package com.nakasato.ghstore.factory.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.IStrategy;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.carrier.HomeProductsCarrier;
import com.nakasato.ghstore.domain.carrier.PaymentCreationCarrier;
import com.nakasato.ghstore.domain.carrier.PerformanceGraphicCarrier;
import com.nakasato.ghstore.domain.filter.impl.AdministratorFilter;
import com.nakasato.ghstore.domain.filter.impl.CustomerFilter;
import com.nakasato.ghstore.domain.filter.impl.OperatorFilter;
import com.nakasato.ghstore.domain.filter.impl.OrderFilter;
import com.nakasato.ghstore.domain.filter.impl.ProductExchangeFilter;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.filter.impl.ProductReturnFilter;
import com.nakasato.ghstore.domain.filter.impl.PromotionFilter;
import com.nakasato.ghstore.domain.order.Order;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Promotion;
import com.nakasato.ghstore.domain.productexchange.ProductExchange;
import com.nakasato.ghstore.domain.productreturn.ProductReturn;
import com.nakasato.ghstore.domain.user.Administrator;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.domain.user.Operator;
import com.nakasato.ghtstore.core.business.complementor.ComplementAxisData;
import com.nakasato.ghtstore.core.business.complementor.ComplementCityAxisData;
import com.nakasato.ghtstore.core.business.complementor.ComplementCustomer;
import com.nakasato.ghtstore.core.business.complementor.ComplementCustomerCoupon;
import com.nakasato.ghtstore.core.business.complementor.ComplementCustomerUpdate;
import com.nakasato.ghtstore.core.business.complementor.ComplementDiscountOrder;
import com.nakasato.ghtstore.core.business.complementor.ComplementInsertDate;
import com.nakasato.ghtstore.core.business.complementor.ComplementOrderDeliverDate;
import com.nakasato.ghtstore.core.business.complementor.ComplementOrderItemPromotion;
import com.nakasato.ghtstore.core.business.complementor.ComplementOrderProductStock;
import com.nakasato.ghtstore.core.business.complementor.ComplementPerformanceGraphicCarrier;
import com.nakasato.ghtstore.core.business.complementor.ComplementProductCode;
import com.nakasato.ghtstore.core.business.complementor.ComplementProductExchange;
import com.nakasato.ghtstore.core.business.complementor.ComplementProductExchangeUpdate;
import com.nakasato.ghtstore.core.business.complementor.ComplementProductReturn;
import com.nakasato.ghtstore.core.business.complementor.ComplementProductReturnUpdate;
import com.nakasato.ghtstore.core.business.complementor.ComplementPromotionCancel;
import com.nakasato.ghtstore.core.business.complementor.ComplementPromotionStatus;
import com.nakasato.ghtstore.core.business.complementor.ComplementSysUserSave;
import com.nakasato.ghtstore.core.business.complementor.ComplementSysUserUpdate;
import com.nakasato.ghtstore.core.business.complementor.ComplementTags;
import com.nakasato.ghtstore.core.business.complementor.NewProductComplementor;
import com.nakasato.ghtstore.core.business.complementor.ProductMostSoldComplementor;
import com.nakasato.ghtstore.core.business.complementor.ProductRecommendComplementor;
import com.nakasato.ghtstore.core.business.filler.PaymentCreationFiller;
import com.nakasato.ghtstore.core.business.filler.StoreCategoryFiller;
import com.nakasato.ghtstore.core.business.filler.SubcategoryFiller;
import com.nakasato.ghtstore.core.business.filler.TagSaveUpdateFiller;
import com.nakasato.ghtstore.core.business.validator.CustomerCEPValidator;
import com.nakasato.ghtstore.core.business.validator.EmailValidator;
import com.nakasato.ghtstore.core.business.validator.PriceValidator;
import com.nakasato.ghtstore.core.business.validator.ProductExchangeAmountValidator;
import com.nakasato.ghtstore.core.business.validator.ProductNameValidator;
import com.nakasato.ghtstore.core.business.validator.ProductReturnAmountValidator;
import com.nakasato.ghtstore.core.business.validator.ProductUpdateValidator;
import com.nakasato.ghtstore.core.business.validator.PromotionDiscountValidator;
import com.nakasato.ghtstore.core.business.validator.PromotionEndDateValidator;
import com.nakasato.ghtstore.core.business.validator.PromotionFilterDiscountValidator;
import com.nakasato.ghtstore.core.business.validator.PromotionFilterEndDateValidator;
import com.nakasato.ghtstore.core.business.validator.PromotionFilterStartDateValidator;
import com.nakasato.ghtstore.core.business.validator.PromotionPeriodValidator;
import com.nakasato.ghtstore.core.business.validator.StockValidator;
import com.nakasato.ghtstore.core.business.validator.StoreCategoryValidator;
import com.nakasato.ghtstore.core.business.validator.SysUserCEPValidator;
import com.nakasato.ghtstore.core.business.validator.TransactionCodeValidator;
import com.nakasato.ghtstore.core.business.validator.UserBirthDateValidator;
import com.nakasato.ghtstore.core.business.validator.UserCPFValidator;
import com.nakasato.ghtstore.core.business.validator.UserPhoneValidator;
import com.nakasato.ghtstore.core.business.validator.UsernameValidator;
import com.nakasato.ghtstore.core.business.validator.fields.CustomerRequiredFieldsValidator;
import com.nakasato.ghtstore.core.business.validator.fields.PerformanceGraphicRequiredFieldsValidator;
import com.nakasato.ghtstore.core.business.validator.fields.ProductExchangeRequiredFieldsValidator;
import com.nakasato.ghtstore.core.business.validator.fields.ProductRequiredFieldsValidator;
import com.nakasato.ghtstore.core.business.validator.fields.ProductReturnRequiredFieldsValidator;
import com.nakasato.ghtstore.core.business.validator.fields.PromotionRequiredFieldsValidator;
import com.nakasato.ghtstore.core.business.validator.fields.SysUserRequiredFieldsValidator;

public class FactoryStrategy {

	/**
	 * Mapa para conter as regras de negócio de todas operações por entidade; O
	 * valor é um mapa que de regras de negócio indexado pela operação A Chave é
	 * o nome da entidade referente às regras de negócio e o valor é um mapa
	 * contendo todos os Strategies referentes à cada operação
	 * (salvar,alterar,consultar,excluir)
	 */
	private static Map < String, Map < String, List < IStrategy > > > rns;

	private static Map < String, List < IStrategy > > rnsProduct;
	private static Map < String, List < IStrategy > > rnsCustomer;
	private static Map < String, List < IStrategy > > rnsAdministrator;
	private static Map < String, List < IStrategy > > rnsOperator;

	private static Map < String, List < IStrategy > > rnsOrder;
	private static Map < String, List < IStrategy > > rnsProductReturn;
	private static Map < String, List < IStrategy > > rnsProductExchange;
	private static Map < String, List < IStrategy > > rnsPromotion;

	private static Map < String, List < IStrategy > > rnsPaymentCreationCarrier;
	private static Map < String, List < IStrategy > > rnsPerformanceGraphicCarrier;
	private static Map < String, List < IStrategy > > rnsHomeProductsCarrier;

	public static List < IStrategy > build( AbstractDomainEntity entity, String operation ) {
		if( rns == null ) {
			initMap();
		}
		List < IStrategy > operationRules = new ArrayList<>();
		Map < String, List < IStrategy > > entityRules = rns.get( entity.getClass().getName() );
		if( entityRules != null ) {
			operationRules = entityRules.get( operation );
		}
		return operationRules;
	}

	private static void initMap() {
		// inicialização do mapa de regras de negócio total
		rns = new HashMap<>();

		// Inicialização do mapa de regras de negócio do produto
		rnsProduct = new HashMap<>();
		rns.put( Product.class.getName(), rnsProduct );
		rns.put( ProductFilter.class.getName(), rnsProduct );
		initProductRns();

		// Inicialização do mapa de regras de negócio do usuário
		rnsCustomer = new HashMap<>();
		rns.put( Customer.class.getName(), rnsCustomer );
		rns.put( CustomerFilter.class.getName(), rnsCustomer );
		initCustomerRns();

		rnsAdministrator = new HashMap<>();
		rns.put( Administrator.class.getName(), rnsAdministrator );
		rns.put( AdministratorFilter.class.getName(), rnsAdministrator );
		initAdministratorRns();

		rnsOperator = new HashMap<>();
		rns.put( Operator.class.getName(), rnsOperator );
		rns.put( OperatorFilter.class.getName(), rnsOperator );
		initOperatorRns();

		rnsOrder = new HashMap<>();
		rns.put( Order.class.getName(), rnsOrder );
		rns.put( OrderFilter.class.getName(), rnsOrder );
		initOrderRns();

		rnsProductReturn = new HashMap<>();
		rns.put( ProductReturn.class.getName(), rnsProductReturn );
		rns.put( ProductReturnFilter.class.getName(), rnsProductReturn );
		initProductReturnRns();

		rnsProductExchange = new HashMap<>();
		rns.put( ProductExchange.class.getName(), rnsProductExchange );
		rns.put( ProductExchangeFilter.class.getName(), rnsProductExchange );
		initProductExchangeRns();

		rnsPromotion = new HashMap<>();
		rns.put( Promotion.class.getName(), rnsPromotion );
		rns.put( PromotionFilter.class.getName(), rnsPromotion );
		initPromotionRns();

		rnsPaymentCreationCarrier = new HashMap<>();
		rns.put( PaymentCreationCarrier.class.getName(), rnsPaymentCreationCarrier );
		initPaymentCreationRns();

		rnsPerformanceGraphicCarrier = new HashMap<>();
		rns.put( PerformanceGraphicCarrier.class.getName(), rnsPerformanceGraphicCarrier );
		initPerformanceGraphicRns();

		rnsHomeProductsCarrier = new HashMap<>();
		rns.put( HomeProductsCarrier.class.getName(), rnsHomeProductsCarrier );
		initrnsHomeProductsRns();

	}

	private static void initPaymentCreationRns() {
		List < IStrategy > rnsFind = new ArrayList<>();
		rnsFind.add( new PaymentCreationFiller() );
		rnsPaymentCreationCarrier.put( EOperation.FIND, rnsFind );
	}

	private static void initPerformanceGraphicRns() {
		List < IStrategy > rnsFind = new ArrayList<>();
		rnsFind.add( new PerformanceGraphicRequiredFieldsValidator() );
		rnsFind.add( new ComplementPerformanceGraphicCarrier() );
		rnsFind.add( new ComplementAxisData() );
		rnsFind.add( new ComplementCityAxisData() );

		rnsPerformanceGraphicCarrier.put( EOperation.FIND, rnsFind );
	}

	private static void initrnsHomeProductsRns() {
		List < IStrategy > rnsFind = new ArrayList<>();
		rnsFind.add( new NewProductComplementor() );
		rnsFind.add( new ProductMostSoldComplementor() );
		rnsFind.add( new ProductRecommendComplementor() );

		rnsHomeProductsCarrier.put( EOperation.FIND, rnsFind );
	}

	private static void initCustomerRns() {
		List < IStrategy > rnsSave = new ArrayList<>();
		List < IStrategy > rnsUpdate = new ArrayList<>();
		// Não há regras para a busca de usuário
		List < IStrategy > rnsFind = new ArrayList<>();
		List < IStrategy > rnsDelete = new ArrayList<>();

		// Adicionando regras de negócio para salvar um Usuário
		rnsSave.add( new CustomerRequiredFieldsValidator() );
		rnsSave.add( new UsernameValidator() );
		rnsSave.add( new UserCPFValidator() );
		rnsSave.add( new UserBirthDateValidator() );
		rnsSave.add( new CustomerCEPValidator() );
		rnsSave.add( new UserPhoneValidator() );
		rnsSave.add( new EmailValidator() );
		rnsSave.add( new ComplementCustomer() );
		// Verificar se Nome de usuário e CPF já existem

		rnsUpdate.add( new CustomerRequiredFieldsValidator() );
		rnsUpdate.add( new UsernameValidator() );
		rnsUpdate.add( new UserBirthDateValidator() );
		rnsUpdate.add( new CustomerCEPValidator() );
		rnsUpdate.add( new UserPhoneValidator() );
		rnsUpdate.add( new EmailValidator() );
		rnsUpdate.add( new UserCPFValidator() );
		rnsUpdate.add( new CustomerCEPValidator() );
		rnsUpdate.add( new ComplementCustomerUpdate() );

		// Insere as regras de negócio por operação
		rnsCustomer.put( EOperation.SAVE, rnsSave );
		rnsCustomer.put( EOperation.UPDATE, rnsUpdate );
		rnsCustomer.put( EOperation.DELETE, rnsDelete );
		rnsCustomer.put( EOperation.FIND, rnsFind );

	}

	private static void initAdministratorRns() {
		List < IStrategy > rnsSave = new ArrayList<>();
		List < IStrategy > rnsUpdate = new ArrayList<>();
		// Não há regras para a busca de usuário
		List < IStrategy > rnsFind = new ArrayList<>();
		List < IStrategy > rnsDelete = new ArrayList<>();

		// Adicionando regras de negócio para salvar um Usuário
		rnsSave.add( new SysUserRequiredFieldsValidator() );
		rnsSave.add( new UsernameValidator() );
		rnsSave.add( new UserCPFValidator() );
		rnsSave.add( new UserBirthDateValidator() );
		rnsSave.add( new SysUserCEPValidator() );
		rnsSave.add( new UserPhoneValidator() );
		rnsSave.add( new EmailValidator() );
		rnsSave.add( new ComplementSysUserSave() );
		// Verificar se Nome de usuário e CPF já existem

		rnsUpdate.add( new SysUserRequiredFieldsValidator() );
		rnsUpdate.add( new UsernameValidator() );
		rnsUpdate.add( new UserBirthDateValidator() );
		rnsUpdate.add( new SysUserCEPValidator() );
		rnsUpdate.add( new UserPhoneValidator() );
		rnsUpdate.add( new EmailValidator() );
		rnsUpdate.add( new SysUserCEPValidator() );
		rnsUpdate.add( new ComplementSysUserUpdate() );

		// Insere as regras de negócio por operação
		rnsAdministrator.put( EOperation.SAVE, rnsSave );
		rnsAdministrator.put( EOperation.UPDATE, rnsUpdate );
		rnsAdministrator.put( EOperation.DELETE, rnsDelete );
		rnsAdministrator.put( EOperation.FIND, rnsFind );

	}

	private static void initOperatorRns() {
		List < IStrategy > rnsSave = new ArrayList<>();
		List < IStrategy > rnsUpdate = new ArrayList<>();
		// Não há regras para a busca de usuário
		List < IStrategy > rnsFind = new ArrayList<>();
		List < IStrategy > rnsDelete = new ArrayList<>();

		// Adicionando regras de negócio para salvar um Usuário
		rnsSave.add( new SysUserRequiredFieldsValidator() );
		rnsSave.add( new UsernameValidator() );
		rnsSave.add( new UserCPFValidator() );
		rnsSave.add( new UserBirthDateValidator() );
		rnsSave.add( new SysUserCEPValidator() );
		rnsSave.add( new UserPhoneValidator() );
		rnsSave.add( new EmailValidator() );
		rnsSave.add( new ComplementSysUserSave() );
		// Verificar se Nome de usuário e CPF já existem

		rnsUpdate.add( new SysUserRequiredFieldsValidator() );
		rnsUpdate.add( new UsernameValidator() );
		rnsUpdate.add( new UserBirthDateValidator() );
		rnsUpdate.add( new SysUserCEPValidator() );
		rnsUpdate.add( new UserPhoneValidator() );
		rnsUpdate.add( new EmailValidator() );
		rnsUpdate.add( new UserCPFValidator() );
		rnsUpdate.add( new ComplementSysUserUpdate() );

		// Insere as regras de negócio por operação
		rnsOperator.put( EOperation.SAVE, rnsSave );
		rnsOperator.put( EOperation.UPDATE, rnsUpdate );
		rnsOperator.put( EOperation.DELETE, rnsDelete );
		rnsOperator.put( EOperation.FIND, rnsFind );

	}

	private static void initPromotionRns() {
		List < IStrategy > rnsSave = new ArrayList<>();
		List < IStrategy > rnsUpdate = new ArrayList<>();
		List < IStrategy > rnsFind = new ArrayList<>();
		List < IStrategy > rnsDelete = new ArrayList<>();

		rnsSave.add( new PromotionRequiredFieldsValidator() );
		rnsSave.add( new PromotionEndDateValidator() );
		rnsSave.add( new PromotionDiscountValidator() );
		rnsSave.add( new PromotionPeriodValidator() );
		rnsSave.add( new ComplementPromotionStatus() );

		rnsUpdate.add( new PromotionRequiredFieldsValidator() );
		rnsUpdate.add( new ProductUpdateValidator() );
		rnsUpdate.add( new PromotionEndDateValidator() );
		rnsUpdate.add( new PromotionDiscountValidator() );
		rnsUpdate.add( new PromotionPeriodValidator() );
		rnsUpdate.add( new ComplementPromotionStatus() );
		rnsUpdate.add( new ComplementPromotionCancel() );

		rnsFind.add( new PromotionFilterDiscountValidator() );
		rnsFind.add( new PromotionFilterStartDateValidator() );
		rnsFind.add( new PromotionFilterEndDateValidator() );

		// Insere as regras de negócio por operação
		rnsPromotion.put( EOperation.SAVE, rnsSave );
		rnsPromotion.put( EOperation.UPDATE, rnsUpdate );
		rnsPromotion.put( EOperation.DELETE, rnsDelete );
		rnsPromotion.put( EOperation.FIND, rnsFind );

	}

	private static void initOrderRns() {
		List < IStrategy > rnsSave = new ArrayList<>();
		List < IStrategy > rnsUpdate = new ArrayList<>();
		// Não há regras para a busca de usuário
		List < IStrategy > rnsFind = new ArrayList<>();
		List < IStrategy > rnsDelete = new ArrayList<>();

		// Adicionando regras de negócio para salvar um Pedido
		rnsSave.add( new TransactionCodeValidator() );
		rnsSave.add( new ComplementDiscountOrder() );
		rnsSave.add( new ComplementOrderItemPromotion() );

		rnsUpdate.add( new ComplementOrderDeliverDate() );
		rnsUpdate.add( new ComplementOrderProductStock() );

		// Insere as regras de negócio por operação
		rnsOrder.put( EOperation.SAVE, rnsSave );
		rnsOrder.put( EOperation.UPDATE, rnsUpdate );
		rnsOrder.put( EOperation.DELETE, rnsDelete );
		rnsOrder.put( EOperation.FIND, rnsFind );

	}

	private static void initProductReturnRns() {
		List < IStrategy > rnsSave = new ArrayList<>();
		List < IStrategy > rnsUpdate = new ArrayList<>();
		// Não há regras para a busca de usuário
		List < IStrategy > rnsFind = new ArrayList<>();
		List < IStrategy > rnsDelete = new ArrayList<>();

		// Adicionando regras de negócio para salvar uma devolução
		rnsSave.add( new ProductReturnRequiredFieldsValidator() );
		rnsSave.add( new ProductReturnAmountValidator() );
		rnsSave.add( new ComplementProductReturn() );

		rnsUpdate.add( new ComplementProductReturnUpdate() );

		// Insere as regras de negócio por operação
		rnsProductReturn.put( EOperation.SAVE, rnsSave );
		rnsProductReturn.put( EOperation.UPDATE, rnsUpdate );
		rnsProductReturn.put( EOperation.DELETE, rnsDelete );
		rnsProductReturn.put( EOperation.FIND, rnsFind );

	}

	private static void initProductExchangeRns() {
		List < IStrategy > rnsSave = new ArrayList<>();
		List < IStrategy > rnsUpdate = new ArrayList<>();
		// Não há regras para a busca de usuário
		List < IStrategy > rnsFind = new ArrayList<>();
		List < IStrategy > rnsDelete = new ArrayList<>();

		// Adicionando regras de negócio para salvar uma devolução
		rnsSave.add( new ProductExchangeRequiredFieldsValidator() );
		rnsSave.add( new ProductExchangeAmountValidator() );
		rnsSave.add( new ComplementProductExchange() );

		rnsUpdate.add( new ComplementProductExchangeUpdate() );
		rnsUpdate.add( new ComplementCustomerCoupon() );

		// Insere as regras de negócio por operação
		rnsProductExchange.put( EOperation.SAVE, rnsSave );
		rnsProductExchange.put( EOperation.UPDATE, rnsUpdate );
		rnsProductExchange.put( EOperation.DELETE, rnsDelete );
		rnsProductExchange.put( EOperation.FIND, rnsFind );

	}

	private static void initProductRns() {

		List < IStrategy > rnsSave = new ArrayList<>();
		List < IStrategy > rnsUpdate = new ArrayList<>();
		// Não há regras para a busca de produto
		List < IStrategy > rnsFind = new ArrayList<>();
		List < IStrategy > rnsDelete = new ArrayList<>();

		// Adicionando regras de negócio para salvar um Product
		rnsSave.add( new ProductRequiredFieldsValidator() );
		rnsSave.add( new ProductNameValidator() );		
		rnsSave.add( new StockValidator() );
		rnsSave.add( new PriceValidator() );
		rnsSave.add( new StoreCategoryValidator() );
		rnsSave.add( new StoreCategoryFiller() );
		rnsSave.add( new SubcategoryFiller() );
		rnsSave.add( new ComplementTags() );
		rnsSave.add( new TagSaveUpdateFiller() );
		rnsSave.add( new ComplementInsertDate() );
		rnsSave.add( new ComplementProductCode() );

		// Adicionando regras de negócio para alterar um Product
		rnsUpdate.add( new ProductRequiredFieldsValidator() );
		rnsUpdate.add( new ProductNameValidator() );
		rnsUpdate.add( new StockValidator() );
		rnsUpdate.add( new PriceValidator() );
		rnsUpdate.add( new StoreCategoryValidator() );
		rnsUpdate.add( new StoreCategoryFiller() );
		rnsUpdate.add( new SubcategoryFiller() );
		rnsUpdate.add( new ComplementTags() );
		rnsUpdate.add( new TagSaveUpdateFiller() );

		// Adicionando regras de negócio para buscar um Product
		// rnsFind.add(new StoreCategoryFiller());
		// rnsFind.add(new SubcategoryFiller());

		// Insere as regras de negócio por operação
		rnsProduct.put( EOperation.SAVE, rnsSave );
		rnsProduct.put( EOperation.UPDATE, rnsUpdate );
		rnsProduct.put( EOperation.DELETE, rnsDelete );
		rnsProduct.put( EOperation.FIND, rnsFind );
	}
}
