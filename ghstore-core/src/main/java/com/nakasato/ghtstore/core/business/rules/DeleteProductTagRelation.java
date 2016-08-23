package com.nakasato.ghtstore.core.business.rules;

import com.nakasato.ghstore.core.IStrategy;
import com.nakasato.ghstore.core.impl.Facade;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.relation.RelationProductTag;

public class DeleteProductTagRelation implements IStrategy{
	
	@Override
	public String process(AbstractDomainEntity entity) {
		Facade facade = new Facade();
		Product product = (Product) entity;
		RelationProductTag relationToClear = new RelationProductTag();
		relationToClear.setId(product.getId());
		
		facade.delete(relationToClear);

		return null;
	}
}
