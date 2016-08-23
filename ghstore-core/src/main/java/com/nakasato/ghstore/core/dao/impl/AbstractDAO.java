package com.nakasato.ghstore.core.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nakasato.ghstore.core.IDAO;
import com.nakasato.ghstore.core.util.DefaultConnection;
import com.nakasato.ghstore.domain.AbstractDomainEntity;

public abstract class AbstractDAO implements IDAO{
	
	protected Connection connection;
	protected String table;
	protected String idTable;
	protected boolean ctrlTransaction=true;
	
	public AbstractDAO( Connection connection, String table, String idTable){
		this.table = table;
		this.idTable = idTable;
		 this.connection = connection;
	}
	
	protected AbstractDAO(String table, String idTable){
		this.table = table;
		this.idTable = idTable;
	}
	
	@Override
	public void delete(AbstractDomainEntity entity) {		
		openConnection();
		PreparedStatement pst=null;		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(table);
		sb.append(" WHERE ");
		sb.append(idTable);
		sb.append("=");
		sb.append("?");	
		try {
			connection.setAutoCommit(false);
			pst = connection.prepareStatement(sb.toString());
			pst.setInt(1, entity.getId());

			pst.executeUpdate();
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
				if(ctrlTransaction)
					connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}		
	
	
	protected void openConnection(){
		try {
			
			if(connection == null || connection.isClosed()){
				connection = DefaultConnection.getConnection();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void releaseResources(Connection conn, PreparedStatement pst, ResultSet rs) throws SQLException{
		DefaultConnection.releaseResources(conn, pst, rs);		
	}
		
}
