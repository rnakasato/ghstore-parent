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

public class FactoryPostStrategy {
	/**
	 * Mapa para conter as regras de neg�cio de todas opera��es por entidade; O
	 * valor � um mapa que de regras de neg�cio indexado pela opera��o A Chave �
	 * o nome da entidade referente �s regras de neg�cio e o valor � um mapa
	 * contendo todos os Strategies referentes � cada opera��o
	 * (salvar,alterar,consultar,excluir)
	 */
	private static Map<String, Map<String, List<IStrategy>>> rns;
	private static Map<String, List<IStrategy>> rnsProduct;

	public static List<IStrategy> build(AbstractDomainEntity entity, String operation) {
		if (rns == null) {
			initMap();
		}
		List<IStrategy> operationRules = new ArrayList<>();
		Map<String, List<IStrategy>> entityRules = rns.get(entity.getClass().getName());
		if (entityRules != null) {
			operationRules = entityRules.get(operation);
		}
		return operationRules;
	}

	private static void initMap() {
		// inicializa��o do mapa de regras de neg�cio total
		rns = new HashMap<>();

		// Inicializa��o do mapa de regras de neg�cio do produto
		rnsProduct = new HashMap<>();
		rns.put(Product.class.getName(), rnsProduct);
		rns.put(ProductFilter.class.getName(), rnsProduct);
		initProductRns();

	}

	private static void initProductRns() {
		List<IStrategy> rnsSave = new ArrayList<>();
		List<IStrategy> rnsUpdate = new ArrayList<>();
		// N�o h� regras para a busca de produto
		List<IStrategy> rnsFind = new ArrayList<>();
		List<IStrategy> rnsDelete = new ArrayList<>();

		// Insere as regras de neg�cio por opera��o
		rnsProduct.put(EOperation.SAVE, rnsSave);
		rnsProduct.put(EOperation.UPDATE, rnsUpdate);
		rnsProduct.put(EOperation.DELETE, rnsDelete);
		rnsProduct.put(EOperation.FIND, rnsFind);
	}

}
