package com.nakasato.ghstore.core.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.IDAO;
import com.nakasato.ghstore.core.table.enums.ESubcategory;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.StoreCategory;
import com.nakasato.ghstore.domain.Subcategory;
import com.nakasato.ghstore.factory.impl.FactoryDAO;

public class SubcategoryDAO extends AbstractDAO{

	public SubcategoryDAO() {
		super(ESubcategory.TABLE_NAME, ESubcategory.PK);
	}

	@Override
	public void save(AbstractDomainEntity entity) throws SQLException {	
		Subcategory subcategory = (Subcategory)entity;
		IDAO scDAO = null;
		StoreCategory sc = null;
		List<AbstractDomainEntity> scList = null;
		try {
			scDAO = FactoryDAO.build(StoreCategory.class.getName());		
			scList = scDAO.find(subcategory.getStoreCategory()); 
			sc = null;
			// Se a lista estiver nula ou vazia a passagem de parâmetros está errada, pois não haverá FK da StoreCategory
			if(!ListUtils.isListEmpty(scList)){
				sc = (StoreCategory) scList.get(0);
			}else{
				throw new SQLException();
			}
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
		openConnection();
		PreparedStatement pst=null;
	
		try {
			connection.setAutoCommit(false);			
					
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO ").append(ESubcategory.TABLE_NAME)
				.append("(").append(ESubcategory.DESCRIPTION).append(",").append(ESubcategory.INSERTDATE).append(",").append(ESubcategory.STORECATEGORY_FK).append(")");
			sql.append(" values(?,?,?)");
							
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			pst.setString(index++, subcategory.getDescription());
			Timestamp time = new Timestamp(subcategory.getInsertdate().getTime());
			pst.setTimestamp(index++, time);
			pst.setInt(index++, sc.getId());
								
			pst.executeUpdate();	
			
			ResultSet rs = pst.getGeneratedKeys();
			int id=0;
			if(rs.next())
				id = rs.getInt(1);
			subcategory.setId(id);
			subcategory.setStoreCategory(sc);
			
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AbstractDomainEntity> find(AbstractDomainEntity entity) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Subcategory subcategory = (Subcategory)entity;
		List<AbstractDomainEntity> resultList = null;		
		
		StringBuilder sql = new StringBuilder();
		
		boolean isDescriptionEmpty = StringUtils.isEmpty(subcategory.getDescription());
		boolean isIdNull = (subcategory.getId() == null);
		boolean isStoreCategoryEmpty = (subcategory.getStoreCategory() == null 
				|| (subcategory.getStoreCategory().getId() == null 
				&& StringUtils.isEmpty(subcategory.getStoreCategory().getDescription())));
		
		try {
		
			IDAO scDAO = FactoryDAO.build(StoreCategory.class.getName());
			StoreCategory sc = null;
			if(!isStoreCategoryEmpty){
				List<AbstractDomainEntity> scList = scDAO.find(subcategory.getStoreCategory());
				if(!ListUtils.isListEmpty(scList)){
					sc = (StoreCategory) scList.get(0);
				}
			}
			
			sql.append(" SELECT * FROM ").append(ESubcategory.TABLE_NAME)
			.append(" WHERE 1=1 ");
			
			if(!isIdNull){
				sql.append(" AND ");
				sql.append(ESubcategory.PK).append(" = ?");
			}
			if(!isDescriptionEmpty){
				sql.append(" AND ");
				sql.append(ESubcategory.DESCRIPTION).append(" like ?");					
			}
			if(!isStoreCategoryEmpty && sc != null){
				sql.append(" AND ");
				sql.append(ESubcategory.STORECATEGORY_FK).append("= ?");
			}
			
			
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			
			int index = 1;
			if(!isIdNull){
				pst.setInt(index++, subcategory.getId());					
			}
			
			if(!isDescriptionEmpty){
				pst.setString(index++, "%"+subcategory.getDescription()+"%");
			}
			
			if(!isStoreCategoryEmpty && sc != null){
				pst.setInt(index++, sc.getId());
			}
			
			rs = pst.executeQuery();
			resultList = new ArrayList<>();
			while (rs.next()) {
				Subcategory s = new Subcategory();
				s.setId(rs.getInt(ESubcategory.PK));
				s.setDescription(rs.getString(ESubcategory.DESCRIPTION));		
				s.setInsertDate(rs.getDate(ESubcategory.INSERTDATE));
				// preenchendo o StoreCategory
				StoreCategory storeCategory = new StoreCategory();
				storeCategory.setId(rs.getInt(ESubcategory.STORECATEGORY_FK));
				List<AbstractDomainEntity> scL = scDAO.find(storeCategory); 
				storeCategory = (StoreCategory) scL.get(0);
				s.setStoreCategory(storeCategory);
				resultList.add(s);					
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
		
		List<AbstractDomainEntity> subcategoryList = new ArrayList<AbstractDomainEntity>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ").append(ESubcategory.TABLE_NAME);
				
		try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				Subcategory s = new Subcategory();
				s.setId(rs.getInt(ESubcategory.PK));
				s.setDescription(rs.getString(ESubcategory.DESCRIPTION));		
				s.setInsertDate(rs.getDate(ESubcategory.INSERTDATE));
				StoreCategory sc = new StoreCategory();
				sc.setId(rs.getInt(ESubcategory.STORECATEGORY_FK));
				s.setStoreCategory(sc);
				subcategoryList.add(s);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			releaseResources(connection, pst, rs);
		}
		return subcategoryList;
	}

}
