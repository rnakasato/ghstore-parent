package com.nakasato.ghtstore.core.business.validator.fields;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghtstore.core.business.FieldsValidator;

/**
 * Responsável por validar os campos obrigatórios
 * 
 * @author Rafael
 *
 */
public class ProductRequiredFieldsValidator extends FieldsValidator<Product> {

	@Override
	public String validate(Product entity) {
		Product p = entity;
		if (StringUtils.isEmpty(p.getName())) {
			appendMsg("Nome");
		}

		if (StringUtils.isEmpty(p.getDescription())) {
			appendMsg("Descrição");
		}

		if (p.getStoreCategory() == null || ((p.getStoreCategory().getId() == null)
				&& StringUtils.isEmpty(p.getStoreCategory().getDescription()))) {
			appendMsg("Categoria");
		}

		if (p.getSubcategory() == null
				|| ((p.getSubcategory().getId() == null) && StringUtils.isEmpty(p.getSubcategory().getDescription()))) {
			appendMsg("Subcategoria");
		}

		if (p.getTagList() == null || p.getTagList().isEmpty()) {
			appendMsg("Tags");
		}

		if (p.getWeight() == null) {
			appendMsg("Peso");
		}

		if (p.getStock() == null) {
			appendMsg("Estoque");
		}

		if (p.getImage() == null) {
			appendMsg("Foto do produto");
		}

		if (p.getPrice() == null) {
			appendMsg("Preço");
		}
		return getMessage();
	}

}
