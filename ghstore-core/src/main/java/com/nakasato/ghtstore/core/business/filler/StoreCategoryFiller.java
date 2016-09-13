package com.nakasato.ghtstore.core.business.filler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.filter.impl.ProductFilter;
import com.nakasato.ghstore.core.filter.impl.StoreCategoryFilter;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.StoreCategory;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghtstore.core.business.Filler;

public class StoreCategoryFiller extends Filler {

	@Override
	public String fill(AbstractDomainEntity entity) {
		ICommand command;
		try {
			if (entity != null && entity instanceof Product) {

				Product product = (Product) entity;
				if (product.getStoreCategory() != null && (product.getStoreCategory().getId() != null
						|| !StringUtils.isEmpty(product.getStoreCategory().getDescription()))) {

					StoreCategoryFilter filter = new StoreCategoryFilter();
					filter.setId(product.getStoreCategory().getId());
					filter.setDescription(product.getStoreCategory().getDescription());
					command = new FactoryCommand().build(filter, EOperation.FIND);
					List<AbstractDomainEntity> storeCategoryList = command.execute().getEntityList();

					if (!ListUtils.isListEmpty(storeCategoryList)) {
						StoreCategory storeCategory = (StoreCategory) storeCategoryList.get(0);
						product.setStoreCategory(storeCategory);
					}
				}

			} else if (entity != null && entity instanceof ProductFilter) {

				ProductFilter filter = (ProductFilter) entity;

				if (!ListUtils.isListEmpty(filter.getStoreCategoryList())) {
					for (StoreCategory category : filter.getStoreCategoryList()) {
						StoreCategoryFilter categoryFilter = new StoreCategoryFilter();
						categoryFilter.setDescription(category.getDescription());
						categoryFilter.setId(category.getId());
						command = new FactoryCommand().build(categoryFilter, EOperation.FIND);
						List<AbstractDomainEntity> storeCategoryList = command.execute().getEntityList();
						if (!ListUtils.isListEmpty(storeCategoryList)) {
							category = (StoreCategory) storeCategoryList.get(0);
						}
					}
				} else if (!filter.getCategory().isEmpty()
						&& !StringUtils.isEmpty(filter.getCategory().getDescription())) {
					filter.getCategory().setId(null);
					StoreCategoryFilter categoryFilter = new StoreCategoryFilter();
					categoryFilter.setDescription(filter.getCategory().getDescription());
					categoryFilter.setId(filter.getCategory().getId());
					command = new FactoryCommand().build(categoryFilter, EOperation.FIND);

					List<AbstractDomainEntity> storeCategoryList = command.execute().getEntityList();
					if (!ListUtils.isListEmpty(storeCategoryList)) {
						filter.setCategory((StoreCategory) storeCategoryList.get(0));
					}
				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
