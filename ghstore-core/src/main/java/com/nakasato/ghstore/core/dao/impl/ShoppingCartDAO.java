package com.nakasato.ghstore.core.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import com.nakasato.ghstore.core.table.enums.EShoppingCart;
import com.nakasato.ghstore.core.table.enums.EShoppingCartItem;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.ShoppingCart;
import com.nakasato.ghstore.domain.ShoppingCartItem;

public class ShoppingCartDAO extends AbstractDAO {

	protected ShoppingCartDAO() {
		super(EShoppingCart.TABLE_NAME, EShoppingCart.PK);
	}

	@Override
	public void save(AbstractDomainEntity entity) throws SQLException {
		ShoppingCart shoppingCart = (ShoppingCart)entity;
		openConnection();
		PreparedStatement pst=null;
	
		try {
			connection.setAutoCommit(false);			
			// Salva o Carrinho de Compras
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO ").append(EShoppingCart.TABLE_NAME)
				.append("(").append(EShoppingCart.USER_ID).append(")");
			sql.append(" values(?)");
							
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			pst.setInt(index++, shoppingCart.getOwner().getId());					
			pst.executeUpdate();	
			
			ResultSet rs = pst.getGeneratedKeys();
			int id=0;
			if(rs.next())
				id = rs.getInt(1);
			shoppingCart.setId(id);
			
			// Salva os itens do carrinho de compras - Não deve entrar aqui mas pode ser utilizado em algum momento
			for(ShoppingCartItem cartItem: shoppingCart.getShoppingCartList()){
				sql = new StringBuilder();
				sql.append(" INSERT INTO ")
					.append(EShoppingCartItem.TABLE_NAME)
					.append("(").append(EShoppingCartItem.SHOPPINGCART_ID)
					.append(EShoppingCartItem.PRODUCT_ID).append(",")
					.append(EShoppingCartItem.AMOUNT).append(",")
					.append(EShoppingCartItem.INSERTDATE)
					.append(")");	
				sql.append(" values(?,?,?,?)");
				
				pst = connection.prepareStatement(sql.toString(), 
						Statement.RETURN_GENERATED_KEYS);
				index = 1;
				pst.setInt(index++, cartItem.getId());
				pst.setInt(index++, cartItem.getProduct().getId());
				pst.setInt(index++, cartItem.getAmount());
				Timestamp insertdate = new Timestamp(cartItem.getInsertdate().getTime());
				pst.setTimestamp(index++, insertdate);
				pst.executeUpdate();
				rs = pst.getGeneratedKeys();
				if(rs.next())
					cartItem.setId(rs.getInt(1));	
			}
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
		
	}

	@Override
	public List<AbstractDomainEntity> find(AbstractDomainEntity entity) throws SQLException {
		return null;
	}

	@Override
	public List<AbstractDomainEntity> findAll() throws SQLException {
		return null;
	}

}
