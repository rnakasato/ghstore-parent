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
import com.nakasato.ghstore.core.table.enums.EProductTag;
import com.nakasato.ghstore.core.table.enums.ETag;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.Product;
import com.nakasato.ghstore.domain.StoreCategory;
import com.nakasato.ghstore.domain.Subcategory;
import com.nakasato.ghstore.domain.Tag;
import com.nakasato.ghstore.factory.impl.FactoryDAO;

public class TagDAO extends AbstractDAO {

	public TagDAO() {
		super(ETag.PK, ETag.TABLE_NAME);
	}

	@Override
	public void save(AbstractDomainEntity entity) throws SQLException {
		openConnection();
		PreparedStatement pst = null;
		Tag tag = (Tag) entity;

		// VERIFICA SE A TAG JÁ EXISTE
		TagFilter filter = new TagFilter();
		filter.setDescription(tag.getDescription());
		List<AbstractDomainEntity> foundTags = find(filter);
		for (AbstractDomainEntity abstractDomainEntity : foundTags) {
			Tag t = (Tag) abstractDomainEntity;
			if(t.getDescription().equals(tag.getDescription())){
				tag.setId(t.getId());
				return;
			}
		}
		//

		try {
			openConnection();
			connection.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO ").append(ETag.TABLE_NAME).append("(").append(ETag.DESCRIPTION).append(",") // 1
					.append(ETag.INSERTDATE) // 2
					.append(")");

			sql.append(" values(?,?)");

			pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			pst.setString(index++, tag.getDescription()); // 1

			Timestamp insertdate = new Timestamp(new Date().getTime());
			pst.setTimestamp(index++, insertdate); // 2
			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			int id = 0;
			if (rs.next())
				id = rs.getInt(1);
			tag.setId(id);
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
		save(entity);
	}

	@Override
	public List<AbstractDomainEntity> find(AbstractDomainEntity entity) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		TagFilter filter = (TagFilter) entity;

		List<AbstractDomainEntity> resultList = null;

		StringBuilder sql = new StringBuilder();

		boolean isDescriptionEmpty = StringUtils.isEmpty(filter.getDescription());
		boolean isProductIdNull = (filter.getProductId() == null);

		try {
			sql.append(" SELECT t.* FROM ").append(ETag.TABLE_NAME).append(" t ");

			if (!isProductIdNull) {
				sql.append(" INNER JOIN ").append(EProductTag.TABLE_NAME).append(" pt ");
				sql.append(" ON t.").append(ETag.PK).append(" = ");
				sql.append(" pt.").append(EProductTag.IDTAG);

				sql.append(" INNER JOIN ").append(EProduct.TABLE_NAME).append(" p ");
				sql.append(" ON p.").append(EProduct.PK).append(" = ");
				sql.append(" pt.").append(EProductTag.IDPRODUCT);
			}
			sql.append(" WHERE 1=1 ");
			// Montagem do Where
			if (!isDescriptionEmpty) {
				sql.append(" AND t.");
				sql.append(ETag.DESCRIPTION).append(" like ? ");
			}
			if (!isProductIdNull) {
				sql.append(" AND p.");
				sql.append(EProductTag.IDPRODUCT).append(" = ?");
			}

			openConnection();
			pst = connection.prepareStatement(sql.toString());

			int index = 1;
			if (!isDescriptionEmpty) {
				pst.setString(index++, "%" + filter.getDescription() + "%");
			}
			if (!isProductIdNull) {
				pst.setInt(index++, filter.getProductId());
			}

			rs = pst.executeQuery();
			resultList = new ArrayList<>();
			while (rs.next()) {
				Tag t = new Tag();
				t.setId(rs.getInt(ETag.PK));
				t.setDescription(rs.getString(ETag.DESCRIPTION));
				t.setInsertDate(rs.getDate(ETag.INSERTDATE));
				resultList.add(t);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		sql.append("SELECT * FROM ").append(ETag.TABLE_NAME);

		try {
			openConnection();
			pst = connection.prepareStatement(sql.toString());

			rs = pst.executeQuery();
			resultList = new ArrayList<AbstractDomainEntity>();
			while (rs.next()) {
				Tag t = new Tag();
				t.setId(rs.getInt(ETag.PK));
				t.setDescription(rs.getString(ETag.DESCRIPTION));
				t.setInsertDate(rs.getDate(ETag.INSERTDATE));
				resultList.add(t);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			releaseResources(connection, pst, rs);
		}
		return resultList;
	}

	public static void main(String[] args) throws SQLException {
		TagFilter filter = new TagFilter();
		filter.setProductId(1);
		filter.setDescription("e2");
		TagDAO dao = new TagDAO();
		List<AbstractDomainEntity> tagList = dao.find(filter);
		for (AbstractDomainEntity abstractDomainEntity : tagList) {
			Tag t = (Tag) abstractDomainEntity;
			System.out.println("Tag: " + t.getDescription());
		}

	}

}
