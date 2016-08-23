package com.nakasato.ghtstore.core.business.filler;

import java.util.ArrayList;
import java.util.List;

import com.nakasato.ghstore.core.filter.impl.TagFilter;
import com.nakasato.ghstore.core.impl.Facade;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.Tag;
import com.nakasato.ghtstore.core.business.Filler;

public class TagFiller extends Filler{

	@Override
	public String fill(AbstractDomainEntity entity) {
		Facade facade = new Facade();
		Product product = (Product) entity;
		TagFilter filter = new TagFilter();
		filter.setProductId(product.getId());
		
		List<AbstractDomainEntity> resultList = facade.find(filter).getEntityList();
		List<Tag> tagList = new ArrayList<>();
		for (AbstractDomainEntity e : resultList) {
			Tag t = (Tag) e;
			tagList.add(t);
		}
		product.setTagList(tagList);
		return null;
	}

}
