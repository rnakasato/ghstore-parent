package com.nakasato.ghtstore.core.business.rules;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.IStrategy;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.impl.Facade;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.Tag;

public class SaveProductTags implements IStrategy{

	@Override
	public String process(AbstractDomainEntity entity) {
		Facade facade = new Facade();
		Product product = (Product) entity;
		
		Result result = null;
		for (Tag tag : product.getTagList()) {
			result = facade.save(tag);
		}
		String msg = null;
		if(result != null && StringUtils.isNotEmpty(result.getMsg())){
			msg = result.getMsg();
		}
		return msg;
	}
	
}
