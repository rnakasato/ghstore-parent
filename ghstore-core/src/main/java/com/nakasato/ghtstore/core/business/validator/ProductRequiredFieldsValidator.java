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
			appendMsg("Nome");
		}
		
		if(StringUtils.isEmpty(p.getDescription())){
			appendMsg("Descrição");
		}
		
		if(p.getStoreCategory() == null || (( p.getStoreCategory().getId() == null) && StringUtils.isEmpty(p.getStoreCategory().getDescription()))){
			appendMsg("Categoria");
		}
		
		if(p.getSubcategory() == null || ( ( p.getSubcategory().getId() == null) && StringUtils.isEmpty(p.getSubcategory().getDescription()))){
			appendMsg("Subcategoria");
		}
		
		if(p.getTagList() == null || p.getTagList().isEmpty()){
			appendMsg("Tags");
		}
		
		if(p.getWeight() == null){
			appendMsg("Peso");
		}
		
		if(p.getStock() == null){
			appendMsg("Estoque");
		}
		
		if(p.getImage() == null){
			appendMsg("Foto do produto");
		}
		
		if(p.getPrice() == null){
			appendMsg("Preço");
		}
		if(sbMessaage != null && sbMessaage.length() > 0){
			msg = sbMessaage.toString();
		}
		return msg;
	}
	
	private void appendMsg(String message){
		if(sbMessaage == null){
			sbMessaage = new StringBuilder();
			sbMessaage.append("O(s) campo(s) é(são) obrigatório(s): \n");
		}
		if(sbMessaage.length() > 0){
			sbMessaage.append(", ");
		}
		sbMessaage.append(message);	
	}
}
