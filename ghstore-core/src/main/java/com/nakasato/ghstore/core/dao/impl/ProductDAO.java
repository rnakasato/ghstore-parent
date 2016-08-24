package com.nakasato.ghstore.core.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.filter.impl.ProductFilter;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.StoreCategory;
import com.nakasato.ghstore.domain.Subcategory;
import com.nakasato.ghstore.domain.Tag;
import com.nakasato.ghstore.factory.impl.FactoryCommand;

public class ProductDAO extends AbstractDAO<Product>{

	@Override
	public List<Product> find(AbstractDomainEntity entity) {
		ProductFilter filter = (ProductFilter) entity; 
		
		boolean isDescriptionEmpty = StringUtils.isEmpty(filter.getDescription());
		boolean isIdNull = (filter.getId() == null);
		boolean isStoreCategoryNull = (filter.getCategory() == null);
		boolean isStoreCategoryListNull = ListUtils.isListEmpty(filter.getStoreCategoryList());
		boolean isSubCategoryNull = (filter.getSubcategory() == null);
		boolean isSubcategoryListNull = ListUtils.isListEmpty(filter.getSubcategoryList());
		boolean isNameEmpty = StringUtils.isEmpty(filter.getName());
		List<Product> productList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM Product p").append(" WHERE 1=1 ");

			if (!isIdNull) {
				jpql.append(" AND p.id = :id");
			}

			if (!isDescriptionEmpty) {
				jpql.append(" AND UPPER(p.description) like :description");

			}
			if(!isStoreCategoryNull){
				if(StringUtils.isNotEmpty(filter.getCategory().getDescription())){
					jpql.append(" AND UPPER(p.storeCategory.description) like :storeCategoryDescription");				
				}
				if(filter.getCategory().getId() != null){
					jpql.append(" AND p.storeCategory.id = :storeCategoryId");				
				}
			}else if(!isStoreCategoryListNull){
				jpql.append(" AND p.storeCategory in (:storeCategoryList)");				
			}
			
			if(!isSubCategoryNull){
				if(StringUtils.isNotEmpty(filter.getSubcategory().getDescription())){
					jpql.append(" AND UPPER(p.subcategory.description) like :subcategoryDescription");				
				}
				if(filter.getSubcategory().getId() != null){
					jpql.append(" AND p.subcategory.id = :subcategoryId");				
				}
			}else if(!isSubcategoryListNull){
				jpql.append(" AND p.subcategory in (:subcategoryList)");				
			}
			
			if(!isNameEmpty){
				jpql.append(" AND p.name like :name");
			}

			Query query = session.createQuery(jpql.toString());

			
			if (!isIdNull) {
				query.setParameter("id", filter.getId());
			}

			if (!isDescriptionEmpty) {
				query.setParameter("description", "%" + filter.getDescription() + "%");
			}
			if(!isStoreCategoryNull){
				if(StringUtils.isNotEmpty(filter.getCategory().getDescription())){					
					query.setParameter("storeCategoryDescription", "%" + filter.getCategory().getDescription().toUpperCase() + "%");
				}
				if(filter.getCategory().getId() != null){
					query.setParameter("storeCategoryId", filter.getCategory().getId());	
				}
			}else if(!isStoreCategoryListNull){
				query.setParameter("storeCategoryList", filter.getStoreCategoryList());								
			}
			
			if(!isSubCategoryNull){
				if(StringUtils.isNotEmpty(filter.getSubcategory().getDescription())){
					query.setParameter("subcategoryDescription", "%" + filter.getSubcategory().getDescription().toUpperCase() + "%");	
				}
				if(filter.getSubcategory().getId() != null){
					query.setParameter("subcategoryId", filter.getSubcategory().getId());
				}
			}else if(!isSubcategoryListNull){
				query.setParameter("subcategoryList", filter.getSubcategoryList());	
			}
			
			if(!isNameEmpty){
				query.setParameter("name", "%" + filter.getName() + "%");
			}
			
			productList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			e.printStackTrace();
			cancelSession();
			throw e;
		}
		return productList;
	}

	@Override
	public List<Product> findAll() {
		List<Product> productList = null;
		try {
			openSession();

			StringBuilder jpql = new StringBuilder();
			jpql.append(" FROM Product ");

			Query query = session.createQuery(jpql.toString());

			productList = query.getResultList();

			closeSession();
		} catch (RuntimeException e) {
			cancelSession();
		}
		return productList;
	}
	
	
	public static void main(String[] args) throws Exception{
//		Product product = new Product();
//		product.setDescription("Produto de teste");
//		product.setImage("../D");
//		product.setInsertDate(new Date());
//		product.setName("PRODUTO A");
//		product.setPrice(10.90);
//		product.setStatus(new Integer(0));
//		product.setWeight(new Double(100));		
//		product.setStock(new Integer(10));
//		
//		StoreCategory sc = new StoreCategory();
//		sc.setDescription("acessórios");
//		ICommand command = new FactoryCommand().build(sc,EOperation.FIND);
//		Result result = command.execute();
//		StoreCategory sca = null;
//		if(result != null){
//			sca = (StoreCategory) result.getEntityList().get(0);			
//		}	
//		product.setStoreCategory(sca);
//		
//		Subcategory subc = new Subcategory();
//		subc.setDescription("subcategoria teste2");
//		command = new FactoryCommand().build(subc, EOperation.FIND);
//		result = command.execute();
//		Subcategory subcategory = null;
//		if (result != null && !ListUtils.isListEmpty(result.getEntityList())) {
//			subcategory = (Subcategory) result.getEntityList().get(0);		
//		}else{
//			subcategory = subc;
//			subcategory.setInsertDate(new Date());
//			subcategory.setStoreCategory(sca);
//		}
//		product.setSubcategory(subcategory);
//		
//		List<Tag> tagList = new ArrayList<>();
//		Tag t1 = new Tag();
//		t1.setDescription("TESTE1");
//		t1.setInsertDate(new Date());
//		Tag t2 = new Tag();
//		t2.setDescription("TESTE2");
//		t2.setInsertDate(new Date());
//		tagList.add(t1);
//		tagList.add(t2);
//		product.setTagList(tagList);
//		
//		product.setUpdateDate(new Date());
//		
//		command = new FactoryCommand().build(product, EOperation.SAVE);
//		command.execute();
//		System.out.println("TESTE");
		Runtime.getRuntime().exit(0);
	}
}

