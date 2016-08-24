package com.nakasato.ghstore.factory.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.IStrategy;
import com.nakasato.ghstore.core.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghtstore.core.business.filler.StoreCategoryFiller;
import com.nakasato.ghtstore.core.business.filler.SubcategoryFiller;
import com.nakasato.ghtstore.core.business.filler.TagSaveUpdateFiller;
import com.nakasato.ghtstore.core.business.validator.PriceValidator;
import com.nakasato.ghtstore.core.business.validator.ProductRequiredFieldsValidator;
import com.nakasato.ghtstore.core.business.validator.StockValidator;
import com.nakasato.ghtstore.core.business.validator.StoreCategoryValidator;

public class FactoryStrategy {
	
	/**
	 * Mapa para conter as regras de negócio de todas operações por entidade;
	 * O valor é um mapa que de regras de negócio indexado pela operação
	 * A Chave é o nome da entidade referente às regras de negócio e o valor é um mapa contendo 
	 * todos os Strategies referentes à cada operação (salvar,alterar,consultar,excluir)
	 */
	private static Map<String, Map<String, List<IStrategy>>> rns;
	private static Map<String, List<IStrategy>> rnsProduct;	
	
	public static List<IStrategy> build(AbstractDomainEntity entity, String operation){
		if(rns == null){
			initMap();
		}
		List<IStrategy> operationRules = new ArrayList<>();
		Map<String, List<IStrategy>> entityRules = rns.get(entity.getClass().getName());
		if(entityRules != null){
			operationRules = entityRules.get(operation);
		}
		return operationRules;
	}
	
	private static void initMap(){
		// inicialização do mapa de regras de negócio total
		rns = new HashMap<>();
		
		// Inicialização do mapa de regras de negócio do produto
		rnsProduct = new HashMap<>();
		rns.put(Product.class.getName(), rnsProduct);
		rns.put(ProductFilter.class.getName(), rnsProduct);
		initProductRns();
		
		
	}
	private static void initProductRns(){
		List<IStrategy> rnsSave = new ArrayList<>();
		List<IStrategy> rnsUpdate = new ArrayList<>();
		// Não há regras para a busca de produto
		List<IStrategy> rnsFind = new ArrayList<>();  
		List<IStrategy> rnsDelete = new ArrayList<>();
		
		// Adicionando regras de negócio para salvar um Product
		rnsSave.add(new ProductRequiredFieldsValidator());
		rnsSave.add(new StockValidator());
		rnsSave.add(new PriceValidator());
		rnsSave.add(new StoreCategoryValidator());
		rnsSave.add(new StoreCategoryFiller());
		rnsSave.add(new SubcategoryFiller());
		rnsSave.add(new TagSaveUpdateFiller());
		
		
		// Adicionando regras de negócio para alterar um Product
		rnsUpdate.add(new ProductRequiredFieldsValidator());
		rnsUpdate.add(new StockValidator());
		rnsUpdate.add(new PriceValidator());
		rnsUpdate.add(new StoreCategoryValidator());
		rnsUpdate.add(new StoreCategoryFiller());
		rnsUpdate.add(new SubcategoryFiller());
		rnsUpdate.add(new TagSaveUpdateFiller());
		
		// Adicionando regras de negócio para buscar um Product
		rnsFind.add(new StoreCategoryFiller());
		rnsFind.add(new SubcategoryFiller());
		
		// Insere as regras de negócio por operação 
		rnsProduct.put(EOperation.SAVE, rnsSave);
		rnsProduct.put(EOperation.UPDATE, rnsUpdate);
		rnsProduct.put(EOperation.DELETE, rnsDelete);
		rnsProduct.put(EOperation.FIND, rnsFind);
	}
}
