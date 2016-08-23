package com.nakasato.ghtstore.core.business.filler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.filter.impl.ProductFilter;
import com.nakasato.ghstore.core.impl.Facade;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.Subcategory;
import com.nakasato.ghtstore.core.business.Filler;

public class SubcategoryFiller extends Filler {

	@Override
	public String fill(AbstractDomainEntity entity) {
		Facade f = new Facade();
		if (entity != null && entity instanceof Product) {
			Product product = (Product) entity;
			if (product.getSubcategory() != null && (product.getSubcategory().getId() != null
					|| !StringUtils.isEmpty(product.getSubcategory().getDescription()))) {

				Subcategory subcategory = new Subcategory();
				subcategory.setId(product.getSubcategory().getId());
				subcategory.setDescription(product.getSubcategory().getDescription());
				List<AbstractDomainEntity> subcategoryList = f.find(subcategory).getEntityList();
				if (!ListUtils.isListEmpty(subcategoryList)) {
					subcategory = (Subcategory) subcategoryList.get(0);
					product.setSubcategory(subcategory);
				}
			}
		}else if (entity != null && entity instanceof ProductFilter) {
			ProductFilter filter = (ProductFilter) entity;
			if (!ListUtils.isListEmpty(filter.getSelectedSubcategoryIds())) {
				filter.setSubcategoryList(new ArrayList<>());
				for(String id: filter.getSelectedSubcategoryIds()){
					Subcategory sub = new Subcategory();
					sub.setId(Integer.parseInt(id));
					List<AbstractDomainEntity> SubcategoryList = f.find(sub).getEntityList();
					if (!ListUtils.isListEmpty(SubcategoryList)) {
						filter.getSubcategoryList().add((Subcategory) SubcategoryList.get(0));						
					}
				}
			}else if(!filter.getSubcategory().isEmpty()){
				filter.getSubcategory().setId(null);
				List<AbstractDomainEntity> subcategoryList = f.find(filter.getSubcategory()).getEntityList();
				if (!ListUtils.isListEmpty(subcategoryList)) {
					filter.setSubcategory((Subcategory) subcategoryList.get(0));
					
				}
			}
		}
		return null;
	}

}
