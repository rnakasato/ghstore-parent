package com.nakasato.ghstore.core.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.nakasato.ghstore.core.table.enums.ETag;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Tag;
import com.nakasato.ghstore.relation.Relation;

public class RelationDAO extends AbstractDAO{

	public RelationDAO(String table, String idTable) {
		super(table, idTable);
	}

	@Override
	public void save(AbstractDomainEntity entity) throws SQLException {
		openConnection();
		PreparedStatement pst = null;
		Relation relation = (Relation) entity;

		try {
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO ").append(table);
			sql.append(" values(?,?)");

			pst = connection.prepareStatement(sql.toString());
			int index = 1;
			pst.setInt(index++, relation.getIdFirstTable());
			pst.setInt(index++, relation.getIdSecondTable());
			pst.executeUpdate();

			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
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
