package com.nakasato.ghstore.core.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.IDAO;
import com.nakasato.ghstore.core.filter.impl.ProductFilter;
import com.nakasato.ghstore.core.filter.impl.TagFilter;
import com.nakasato.ghstore.core.table.enums.EProduct;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.StoreCategory;
import com.nakasato.ghstore.domain.Subcategory;
import com.nakasato.ghstore.domain.Tag;
import com.nakasato.ghstore.factory.impl.FactoryDAO;

public class ProductDAO extends AbstractDAO{
	public ProductDAO() {
		super(EProduct.TABLE_NAME, EProduct.PK); 
	}

	@Override
	public void save(AbstractDomainEntity entity) throws SQLException {
		
		Product product = (Product)entity;
		IDAO scDAO = null;
		IDAO subcDAO = null;
		Subcategory subC = null;
		StoreCategory storeC = null;
			
		try {
			scDAO = FactoryDAO.build(StoreCategory.class.getName());
			subcDAO = FactoryDAO.build(Subcategory.class.getName());
			
			// Verifica se existe a StoreCategory passada no produto
			List<AbstractDomainEntity> storeCList = scDAO.find(product.getStoreCategory()); 
			storeC = null;
			// Se a lista estiver nula ou vazia a passagem de parâmetros está errada, pois não haverá FK da StoreCategory
			if(!ListUtils.isListEmpty(storeCList)){
				storeC = (StoreCategory) storeCList.get(0);
				product.setStoreCategory(storeC);
			}else{
				throw new SQLException();
			}
			
			// Verifica se existe a Subcategory passada no produto		
			List<AbstractDomainEntity> subCList = subcDAO.find(product.getSubcategory()); 
			subC = null;
			// Se a lista estiver nula ou vazia significa que ainda não existe a subcategoria e portanto deve ser cadastrada
			if(!ListUtils.isListEmpty(subCList)){
				subC = (Subcategory) subCList.get(0);
				product.setSubcategory(subC);
			}else{
				if(product.getSubcategory() != null && !StringUtils.isEmpty(product.getSubcategory().getDescription())){
					product.getSubcategory().setInsertDate(new Date());
					subcDAO.save(product.getSubcategory());
				}else{
					throw new SQLException();
				}				
			}
			
			
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
		openConnection();
		PreparedStatement pst=null;
	
		try {
			connection.setAutoCommit(false);			
					
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO ").append(EProduct.TABLE_NAME).append("(")
				.append(EProduct.PRODUCT_NAME).append(",")		// 1
				.append(EProduct.DESCRIPTION).append(",")		// 2
				.append(EProduct.INSERTDATE).append(",")		// 3
				.append(EProduct.UPDATEDATE).append(",")		// 4
				.append(EProduct.PRICE).append(",")				// 5
				.append(EProduct.STOCK).append(",")				// 6
				.append(EProduct.SUBCATEGORY_FK).append(",")	// 7
				.append(EProduct.STORECATEGORY_FK).append(",")	// 8
				.append(EProduct.IMAGE)							// 9
				.append(")");
			
			
			sql.append(" values(?,?,?,?,?,?,?,?,?)");
							
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			pst.setString(index++, product.getName());				// 1
			pst.setString(index++, product.getDescription());		// 2
						
			Timestamp insertdate = new Timestamp(product.getInsertdate().getTime());
			pst.setTimestamp(index++, insertdate);					// 3
			
			Timestamp updatedate = new Timestamp(product.getUpdateDate().getTime());
			pst.setTimestamp(index++, updatedate);					// 4
			pst.setDouble(index++, product.getPrice());				// 5
			pst.setInt(index++, product.getStock());				// 6
			pst.setInt(index++, product.getSubcategory().getId());	// 7
			pst.setInt(index++, product.getStoreCategory().getId());// 8
			pst.setString(index++, product.getImage());
			pst.executeUpdate();								
			
			ResultSet rs = pst.getGeneratedKeys();
			int id=0;
			if(rs.next())
				id = rs.getInt(1);
			product.setId(id);
			product.setStoreCategory(storeC);
			product.setSubcategory(subC);
			
			connection.commit();		
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();			
		}finally{
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	
	}

	@Override
	public void update(AbstractDomainEntity entity) throws SQLException {
		openConnection();
		PreparedStatement pst=null;
		Product product = (Product)entity;		
		
		try {
			IDAO dao = FactoryDAO.build(Subcategory.class.getName());
			List<AbstractDomainEntity> listSubc = dao.find(product.getSubcategory());
			if(ListUtils.isListEmpty(listSubc)){ 
				dao.save(product.getSubcategory());				
			}else{
				Subcategory s = (Subcategory) listSubc.get(0);
				product.setSubcategory(s);
			}
		
			dao = FactoryDAO.build(StoreCategory.class.getName());
			List<AbstractDomainEntity> listSC = dao.find(product.getStoreCategory());
			if(listSubc != null && !listSC.isEmpty()){ 
				StoreCategory sc = (StoreCategory) listSC.get(0);
				product.setStoreCategory(sc);
			}
			
			
			connection.setAutoCommit(false);			
					
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE ").append(EProduct.TABLE_NAME);
			sql.append(" SET ");
			sql.append(EProduct.PRODUCT_NAME).append(" = ? ,");	 //1
			sql.append(EProduct.DESCRIPTION).append(" = ? ,");	 //2
			sql.append(EProduct.UPDATEDATE).append(" = ? ,");	 //3
			sql.append(EProduct.PRICE).append(" = ? ,");	 //4
			sql.append(EProduct.STOCK).append(" = ? ,");	 //5
			sql.append(EProduct.STORECATEGORY_FK).append(" = ? ,");	 //6
			sql.append(EProduct.SUBCATEGORY_FK).append(" = ?, ");	 //7
			sql.append(EProduct.IMAGE).append("= ?");
			sql.append(" WHERE ").append(EProduct.PK).append(" = ?");
			
			pst = connection.prepareStatement(sql.toString());
			int index = 1;
			pst.setString(index++, product.getName());
			pst.setString(index++, product.getDescription());
			Timestamp updatedate = new Timestamp(product.getUpdateDate().getTime());
			pst.setTimestamp(index++, updatedate);
			pst.setDouble(index++, product.getPrice());
			pst.setInt(index++,product.getStock());
			pst.setInt(index++, product.getStoreCategory().getId());
			pst.setInt(index++, product.getSubcategory().getId());
			pst.setString(index++, product.getImage());
			pst.setInt(index++, product.getId());
			pst.executeUpdate();			
			connection.commit();		
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				pst.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
	}

	@Override
	public List<AbstractDomainEntity> find(AbstractDomainEntity entity) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
				 
		ProductFilter filter = (ProductFilter) entity; 
		List<AbstractDomainEntity> resultList = null;
		
		StringBuilder sql = new StringBuilder();
		
		IDAO tagDAO = null;
		boolean isDescriptionEmpty = StringUtils.isEmpty(filter.getDescription());
		boolean isIdNull = (filter.getId() == null);
		boolean isStoreCategoryNull = (filter.getCategory() == null);
		boolean isStoreCategoryListNull = ListUtils.isListEmpty(filter.getStoreCategoryList());
		boolean isSubCategoryNull = (filter.getSubcategory() == null);
		boolean isSubcategoryListNull = ListUtils.isListEmpty(filter.getSubcategoryList());
		boolean isNameEmpty = StringUtils.isEmpty(filter.getName());
				
		try {
			IDAO scDAO = FactoryDAO.build(StoreCategory.class.getName());
			sql.append(" SELECT * FROM ").append(EProduct.TABLE_NAME)
			.append(" WHERE 1=1 ");
			// Montagem do Where
			if(!isIdNull){
				sql.append(" AND ");
				sql.append(EProduct.PK).append(" = ?");
			}
			if(!isNameEmpty){
				sql.append(" AND ");
				sql.append(EProduct.PRODUCT_NAME).append(" like ?");				
			}		
			
			if(!isDescriptionEmpty){
				sql.append(" AND ");
				sql.append(EProduct.DESCRIPTION).append(" like ?");				
			}		
			if(!isStoreCategoryNull && filter.getCategory().getId() != null){
				sql.append(" AND ");
				sql.append(EProduct.STORECATEGORY_FK).append("= ?");
			}
			
			if(!isStoreCategoryListNull){
				sql.append(" AND ");
				sql.append(EProduct.STORECATEGORY_FK).append(" in (");
				for(int i = 0; i < filter.getStoreCategoryList().size() - 1; i++){
					sql.append("?");
					if(!(i == (filter.getStoreCategoryList().size() - 2))){
						sql.append(", ");
					}
				}
				sql.append(") ");				
			}
			
			if(!isSubCategoryNull && filter.getSubcategory().getId() != null){
				sql.append(" AND ");
				sql.append(EProduct.SUBCATEGORY_FK).append("= ?");
			}
			
			if(!isSubcategoryListNull){
				sql.append(" AND ");
				sql.append(EProduct.SUBCATEGORY_FK).append(" in (");
				for(int i = 0; i < filter.getSubcategoryList().size(); i++){
					sql.append("?");
					if(!(i == (filter.getSubcategoryList().size() - 1))){
						sql.append(", ");
					}
				}
				sql.append(") ");				
			}
			
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			
			int index = 1;
			if(!isIdNull){
				pst.setInt(index++, filter.getId());					
			}
			if(!isNameEmpty){
				pst.setString(index++, "%"+filter.getName()+"%");
			}
			if(!isDescriptionEmpty){
				pst.setString(index++, "%"+filter.getDescription()+"%");
			}
			if(!isStoreCategoryNull && filter.getCategory().getId() != null){
				pst.setInt(index++, filter.getCategory().getId());
			}
			if(!isStoreCategoryListNull){				
				for(int i = 0; i < filter.getStoreCategoryList().size() - 1; i++){
					StoreCategory cat = filter.getStoreCategoryList().get(i);
					pst.setInt(index++, cat.getId());
				}				
			}
			
			if(!isSubCategoryNull && filter.getSubcategory().getId() != null){
				pst.setInt(index++, filter.getSubcategory().getId());
			}
			
			if(!isSubcategoryListNull){				
				for(int i = 0; i < filter.getSubcategoryList().size(); i++){
					Subcategory cat = filter.getSubcategoryList().get(i);
					pst.setInt(index++, cat.getId());
				}				
			}
			
			rs = pst.executeQuery();
			resultList = new ArrayList<>();
			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt(EProduct.PK));
				p.setName(rs.getString(EProduct.PRODUCT_NAME));
				p.setDescription(rs.getString(EProduct.DESCRIPTION));		
				p.setInsertDate(rs.getDate(EProduct.INSERTDATE));
				p.setUpdateDate(rs.getDate(EProduct.UPDATEDATE));
				p.setPrice(rs.getDouble(EProduct.PRICE));
				p.setStock(rs.getInt(EProduct.STOCK));
				p.setImage(rs.getString(EProduct.IMAGE));
				
				// Preenchendo StoreCategory do produto
				StoreCategory storeCategory = new StoreCategory();
				storeCategory.setId(rs.getInt(EProduct.STORECATEGORY_FK));
				List<AbstractDomainEntity> scList = scDAO.find(storeCategory); 
				storeCategory = (StoreCategory) scList.get(0);
				p.setStoreCategory(storeCategory);
				
				// Preenchendo subcategory do produto
				IDAO sDAO = FactoryDAO.build(Subcategory.class.getName());
				Subcategory s = new Subcategory();
				s.setId(rs.getInt(EProduct.SUBCATEGORY_FK));
				List<AbstractDomainEntity> subcategoryList= sDAO.find(s); 
				s = (Subcategory) subcategoryList.get(0);
				p.setSubcategory(s);
				
				TagFilter tagFilter = new TagFilter();
				tagFilter.setProductId(p.getId());
				tagDAO = FactoryDAO.build(TagFilter.class.getName());
				List<AbstractDomainEntity> tagResult = tagDAO.find(tagFilter);
				List<Tag> tagList = new ArrayList<>();
				for (AbstractDomainEntity a : tagResult) {
					Tag t = (Tag) a;
					tagList.add(t);
				}
				p.setTagList(tagList);
				resultList.add(p);		
			}	

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			releaseResources(connection, pst, rs);
		}
		return resultList;
		
	}

	@Override
	public List<AbstractDomainEntity> findAll() throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		List<AbstractDomainEntity> resultList = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ").append(EProduct.TABLE_NAME);
				
		try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			
			rs = pst.executeQuery();
			resultList = new ArrayList<AbstractDomainEntity>();
			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt(EProduct.PK));
				p.setName(rs.getString(EProduct.PRODUCT_NAME));
				p.setDescription(rs.getString(EProduct.DESCRIPTION));		
				p.setInsertDate(rs.getDate(EProduct.INSERTDATE));
				p.setUpdateDate(rs.getDate(EProduct.UPDATEDATE));
				p.setPrice(rs.getDouble(EProduct.PRICE));
				p.setStock(rs.getInt(EProduct.STOCK));
				p.setImage(rs.getString(EProduct.IMAGE));				
				// Preenchendo StoreCategory do produto
				IDAO scDAO = FactoryDAO.build(StoreCategory.class.getName());
				StoreCategory storeCategory = new StoreCategory();
				storeCategory.setId(rs.getInt(EProduct.STORECATEGORY_FK));
				List<AbstractDomainEntity> scList = scDAO.find(storeCategory); 
				storeCategory = (StoreCategory) scList.get(0);
				p.setStoreCategory(storeCategory);
				
				// Preenchendo subcategory do produto
				IDAO sDAO = FactoryDAO.build(Subcategory.class.getName());
				Subcategory s = new Subcategory();
				s.setId(rs.getInt(EProduct.SUBCATEGORY_FK));
				List<AbstractDomainEntity> subcategoryList= sDAO.find(s); 
				s = (Subcategory) subcategoryList.get(0);
				p.setSubcategory(s);
				
				TagFilter tagFilter = new TagFilter();
				tagFilter.setProductId(p.getId());
				IDAO tagDAO = FactoryDAO.build(TagFilter.class.getName());
				List<AbstractDomainEntity> tagResult = tagDAO.find(tagFilter);
				List<Tag> tagList = new ArrayList<>();
				for (AbstractDomainEntity a : tagResult) {
					Tag t = (Tag) a;
					tagList.add(t);
				}
				p.setTagList(tagList);
				resultList.add(p);	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			releaseResources(connection, pst, rs);
		}
		return resultList;
	}

}

