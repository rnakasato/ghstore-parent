package com.nakasato.ghstore.factory.impl;

import java.util.HashMap;
import java.util.Map;

import com.nakasato.ghstore.core.IDAO;
import com.nakasato.ghstore.core.dao.impl.ProductDAO;
import com.nakasato.ghstore.core.dao.impl.StoreCategoryDAO;
import com.nakasato.ghstore.core.dao.impl.SubcategoryDAO;
import com.nakasato.ghstore.core.dao.impl.TagDAO;
import com.nakasato.ghstore.core.filter.impl.ProductFilter;
import com.nakasato.ghstore.core.filter.impl.TagFilter;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.StoreCategory;
import com.nakasato.ghstore.domain.Subcategory;
import com.nakasato.ghstore.domain.Tag;

public class FactoryDAO {
		
	private static Map<String,IDAO> daoMap;
	
	private static void initMap(){
		if(daoMap == null){
			daoMap = new HashMap<>();
			daoMap.put(Product.class.getName(), new ProductDAO());
			daoMap.put(StoreCategory.class.getName(), new StoreCategoryDAO());
			daoMap.put(Subcategory.class.getName(), new SubcategoryDAO());
			daoMap.put(Tag.class.getName(), new TagDAO());
			
			// TEMPORÁRIO ATÉ ADIÇÃO DOS FILTROS
			daoMap.put(ProductFilter.class.getName(), new ProductDAO());
			daoMap.put(TagFilter.class.getName(), new TagDAO());

		}
	}
	
	/**	 
	 * @param className
	 * @return retorna instância de DAO para o Objeto
	 */
	public static IDAO build(String className) throws ClassNotFoundException{
		initMap();
		IDAO dao = daoMap.get(className);
		if(dao == null){
			throw new ClassNotFoundException();
		}
		return dao;
	}
}
