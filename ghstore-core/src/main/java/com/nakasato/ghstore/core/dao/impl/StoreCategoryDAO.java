package com.nakasato.ghstore.core.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.core.table.enums.EStoreCategory;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.StoreCategory;

public class StoreCategoryDAO extends AbstractDAO{
	
	
	
	public StoreCategoryDAO() {
		super(EStoreCategory.TABLE_NAME, EStoreCategory.PK);
	}
	
	/**
	 * O M�todo save n�o ser� utilizado porque as store_category ser�o pr� cadastradas no banco
	 */
	@Override
	public void save(AbstractDomainEntity entity) throws SQLException {

		
	}
	
	/**
	 * O M�todo save n�o ser� utilizado porque as store_category n�o poder�o ser excluidas via sistema, ser�o manipuladas diretamente no banco
	 */
	@Override
	public void delete(AbstractDomainEntity entity) {
		
	}
	;
	
	/**
	 * O M�todo save n�o ser� utilizado porque as store_category n�o poder�o ser excluidas atualizadas via sistema, ser�o manipuladas diretamente no banco
	 */
	@Override
	public void update(AbstractDomainEntity entity) throws SQLException {
		
		
	}

	
	@Override
	public List<AbstractDomainEntity> find(AbstractDomainEntity entity) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		StoreCategory storeCategory = (StoreCategory)entity;
		
		List<AbstractDomainEntity> resultList = null;
		
		StringBuilder sql = new StringBuilder();
		
		boolean isDescriptionEmpty = StringUtils.isEmpty(storeCategory.getDescription());
		boolean isIdNull = (storeCategory.getId() == null);
		
		sql.append(" SELECT * FROM ").append(EStoreCategory.TABLE_NAME)
		.append(" WHERE 1=1 ");
		
		if(!isIdNull){
			sql.append(" AND ");
			sql.append(EStoreCategory.PK).append(" = ?");
		}
		
		if(!isDescriptionEmpty){
			sql.append(" AND ");
			sql.append(EStoreCategory.DESCRIPTION).append(" like ?");
		}
		try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			
			int index = 1;
			if(!isIdNull){
				pst.setInt(index++, storeCategory.getId());
			}
			
			if(!isDescriptionEmpty){
				pst.setString(index++, "%"+storeCategory.getDescription()+"%");			
			}
			rs = pst.executeQuery();
			resultList = new ArrayList<>();
			while (rs.next()) {
				StoreCategory s = new StoreCategory();
				s.setId(rs.getInt(EStoreCategory.PK));
				s.setDescription(rs.getString(EStoreCategory.DESCRIPTION));
				s.setInsertDate(rs.getDate(EStoreCategory.INSERTDATE));
				resultList.add(s);					
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			releaseResources(connection, pst, rs);
		}
		return resultList;			
	}
	


	/**
	 * O M�todo findAll n�o ser� utilizado porque no momento n�o h� sentido para retornar todas as categorias
	 */	
	@Override
	public List<AbstractDomainEntity> findAll() throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		List<AbstractDomainEntity> storeCategoryList = new ArrayList<AbstractDomainEntity>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ").append(EStoreCategory.TABLE_NAME);
				
		try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				StoreCategory s = new StoreCategory();
				s.setId(rs.getInt(EStoreCategory.PK));
				s.setDescription(rs.getString(EStoreCategory.DESCRIPTION));		
				s.setInsertDate(rs.getDate(EStoreCategory.INSERTDATE));
				storeCategoryList.add(s);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			releaseResources(connection, pst, rs);
		}
		return storeCategoryList;
	}

		
}
