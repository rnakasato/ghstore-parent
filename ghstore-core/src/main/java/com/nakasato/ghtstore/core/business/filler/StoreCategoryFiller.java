package com.nakasato.ghtstore.core.business.filler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.filter.impl.ProductFilter;
import com.nakasato.ghstore.core.impl.Facade;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.StoreCategory;
import com.nakasato.ghtstore.core.business.Filler;

public class StoreCategoryFiller extends Filler {

	@Override
	public String fill(AbstractDomainEntity entity) {
		Facade f = new Facade();
		
		if (entity != null && entity instanceof Product) {
			
			Product product = (Product) entity;
			if (product.getStoreCategory() != null && (product.getStoreCategory().getId() != null
					|| !StringUtils.isEmpty(product.getStoreCategory().getDescription()))) {
				
				StoreCategory storeCategory = new StoreCategory();
				storeCategory.setId(product.getStoreCategory().getId());
				storeCategory.setDescription(product.getStoreCategory().getDescription());
				
				List<AbstractDomainEntity> storeCategoryList = f.find(storeCategory).getEntityList();
				
				if (!ListUtils.isListEmpty(storeCategoryList)) {
					storeCategory = (StoreCategory) storeCategoryList.get(0);
					product.setStoreCategory(storeCategory);
				}
			}
			
		} else if (entity != null && entity instanceof ProductFilter) {
			
			ProductFilter filter = (ProductFilter) entity;
			
			if (!ListUtils.isListEmpty(filter.getStoreCategoryList())) {				
				for(StoreCategory category : filter.getStoreCategoryList()){
					List<AbstractDomainEntity> storeCategoryList = f.find(category).getEntityList();
					if (!ListUtils.isListEmpty(storeCategoryList)) {
						category = (StoreCategory) storeCategoryList.get(0);						
					}
				}
			}else if(!filter.getCategory().isEmpty()){
				filter.getCategory().setId(null);
				List<AbstractDomainEntity> storeCategoryList = f.find(filter.getCategory()).getEntityList();
				if (!ListUtils.isListEmpty(storeCategoryList)) {
					filter.setCategory((StoreCategory) storeCategoryList.get(0));
				}
			}
		}
		return null;
	}

}
