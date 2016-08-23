package com.nakasato.ghtstore.core.business.validator;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghtstore.core.business.Validator;

/**
 * Respons�vel por validar os campos obrigat�rios
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
			appendMsg("O campo \"Nome\" � obrigat�rio!");
		}
		
		if(StringUtils.isEmpty(p.getDescription())){
			appendMsg("O campo \"Descri��o\" � obrigat�rio!");
		}
		
		if(p.getStoreCategory() == null || (( p.getStoreCategory().getId() == null) && StringUtils.isEmpty(p.getStoreCategory().getDescription()))){
			appendMsg("O campo \"Categoria\" � obrigat�rio");
		}
		
		if(p.getSubcategory() == null || ( ( p.getSubcategory().getId() == null) && StringUtils.isEmpty(p.getSubcategory().getDescription()))){
			appendMsg("O campo \"Subcategoria\" � obrigat�rio");
		}
		
		if(p.getStock() == null){
			appendMsg("O campo \"Estoque\" � obrigat�rio");
		}
		
		if(p.getImage() == null){
			appendMsg("O campo \"Foto do produto\" � obrigat�rio");
		}
		
		if(p.getPrice() == null){
			appendMsg("O campo \"Pre�o de venda (Un)\" � obrigat�rio");
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
