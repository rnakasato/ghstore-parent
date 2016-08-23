package com.nakasato.ghtstore.core.business.rules;

import com.nakasato.ghstore.core.IStrategy;
import com.nakasato.ghstore.core.impl.Facade;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.Tag;
import com.nakasato.ghstore.relation.RelationProductTag;

public class UpdateProductTagRelation implements IStrategy {

	@Override
	public String process(AbstractDomainEntity entity) {
		Facade facade = new Facade();
		Product product = (Product) entity;
		RelationProductTag relationToClear = new RelationProductTag();
		relationToClear.setId(product.getId());

		facade.delete(relationToClear);

		if (product.getTagList() != null) {
			for (Tag tag : product.getTagList()) {
				RelationProductTag relation = new RelationProductTag();
				relation.setIdFirstTable(tag.getId());
				relation.setIdSecondTable(product.getId());
				facade.save(relation);
			}
		}

		return null;
	}
}
