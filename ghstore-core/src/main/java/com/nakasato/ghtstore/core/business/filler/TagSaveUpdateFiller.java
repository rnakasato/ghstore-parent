package com.nakasato.ghtstore.core.business.filler;

import java.util.Date;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.Tag;
import com.nakasato.ghtstore.core.business.Filler;

public class TagSaveUpdateFiller extends Filler{

	@Override
	public String fill(AbstractDomainEntity entity) {
		if(entity instanceof Product){
			Product product = (Product) entity;
			for (Tag tag : product.getTagList()) {
				tag.setInsertDate(new Date());
			}
		}
		return null;
	}

}
