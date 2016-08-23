package com.nakasato.ghtstore.core.business.validator;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghtstore.core.business.Validator;

/**
 * Responsável por validar os campos obrigatórios
 * @author Rafael
 *
 */
public class ProductRequiredFieldsValidator extends Validator{
	private StringBuilder sbMessaage;
	
	@Override
	public String validate(AbstractDomainEntity entity) {
		sbMessaage = null;
		msg = null;
		Product p = (Product) entity;		
		if(StringUtils.isEmpty(p.getName())){
			appendMsg("O campo \"Nome\" é obrigatório!");
		}
		
		if(StringUtils.isEmpty(p.getDescription())){
			appendMsg("O campo \"Descrição\" é obrigatório!");
		}
		
		if(p.getStoreCategory() == null || (( p.getStoreCategory().getId() == null) && StringUtils.isEmpty(p.getStoreCategory().getDescription()))){
			appendMsg("O campo \"Categoria\" é obrigatório");
		}
		
		if(p.getSubcategory() == null || ( ( p.getSubcategory().getId() == null) && StringUtils.isEmpty(p.getSubcategory().getDescription()))){
			appendMsg("O campo \"Subcategoria\" é obrigatório");
		}
		
		if(p.getStock() == null){
			appendMsg("O campo \"Estoque\" é obrigatório");
		}
		
		if(p.getImage() == null){
			appendMsg("O campo \"Foto do produto\" é obrigatório");
		}
		
		if(p.getPrice() == null){
			appendMsg("O campo \"Preço de venda (Un)\" é obrigatório");
		}
		if(sbMessaage != null && sbMessaage.length() > 0){
			msg = sbMessaage.toString();
		}
		return msg;
	}
	
	private void appendMsg(String message){
		if(sbMessaage == null){
			sbMessaage = new StringBuilder();
		}
		if(sbMessaage.length() > 0){
			sbMessaage.append("\n");
		}
		sbMessaage.append(message);	
	}
}
