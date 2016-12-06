package com.nakasato.ghstore.core.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;

import com.nakasato.ghstore.core.hibernate.HibernateUtil;
import com.nakasato.ghstore.core.hibernate.SessionThreadLocal;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghstore.domain.filter.impl.ProductFilter;
import com.nakasato.ghstore.domain.filter.impl.PromotionFilter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.Promotion;

public class PromotionDAO extends AbstractDAO < Promotion > {

	@Override
	public List < Promotion > find( AbstractDomainEntity entity ) throws Exception {
		PromotionFilter filter = ( PromotionFilter ) entity;

		List < Promotion > promotionList = null;
		try {

			StringBuilder jpql = new StringBuilder();
			jpql.append( " SELECT DISTINCT (pm) FROM Promotion pm " );
			jpql.append( " LEFT JOIN pm.productList p " );
			jpql.append( " LEFT JOIN p.storeCategory sc " );
			jpql.append( " WHERE 1=1 " );

			if( StringUtils.isNotEmpty( filter.getDescription() ) ) {
				jpql.append( " AND pm.description like :description" );
			}

			if( filter.getId() != null ) {
				jpql.append( " AND pm.id = :id" );
			}

			if( ListUtils.isNotEmpty( filter.getProductList() ) ) {
				jpql.append( " AND p in (:productList)" );
			}

			if( ListUtils.isNotEmpty( filter.getCategoryList() ) ) {
				jpql.append( " AND sc in (:categoryList)" );
			}

			if( filter.getStartDiscount() != null && filter.getEndDiscount() != null ) {
				jpql.append( " AND pm.discount BETWEEN :startDiscount AND :endDiscount" );
			}

			if( filter.getStartDateBegin() != null && filter.getStartDateEnd() != null ) {
				jpql.append( " AND pm.startDate BETWEEN :startDateBegin AND :startDateEnd " );
			}

			if( filter.getEndDateBegin() != null && filter.getEndDateEnd() != null ) {
				jpql.append( " AND pm.endDate BETWEEN :endDateBegin AND :endDateEnd " );
			}

			if( filter.getActive() != null ) {
				jpql.append( " AND pm.active = :active " );
			}

			if( filter.getNewPromotions() != null ) {
				if( filter.getNewPromotions() ) {
					jpql.append( " AND pm.startDate >= :today " );
				} else {
					jpql.append( " AND pm.startDate < :today " );
				}
			}

			if( StringUtils.isNotEmpty( filter.getDescription() ) ) {
				jpql.append( " AND UPPER(pm.description) like :description" );
			}

			jpql.append( " ORDER BY pm.startDate asc " );

			Query query = session.createQuery( jpql.toString() );
			
			if( StringUtils.isNotEmpty( filter.getDescription() ) ) {
				query.setParameter( "description", "%" + filter.getDescription().toUpperCase() + "%" );
			}

			if( filter.getId() != null ) {
				
			}

			if( ListUtils.isNotEmpty( filter.getProductList() ) ) {
				query.setParameter( "productList", filter.getProductList() );
			}

			if( ListUtils.isNotEmpty( filter.getCategoryList() ) ) {
				query.setParameter( "categoryList", filter.getCategoryList() );
			}

			if( filter.getStartDiscount() != null && filter.getEndDiscount() != null ) {
				query.setParameter( "startDiscount", filter.getStartDiscount() );
				query.setParameter( "endDiscount", filter.getEndDiscount() );
			}

			if( filter.getStartDateBegin() != null && filter.getStartDateEnd() != null ) {
				query.setParameter( "startDateBegin", filter.getStartDateBegin() );
				query.setParameter( "startDateEnd", filter.getStartDateEnd() );
			}

			if( filter.getEndDateBegin() != null && filter.getEndDateEnd() != null ) {
				query.setParameter( "endDateBegin", filter.getEndDateBegin() );
				query.setParameter( "endDateEnd", filter.getEndDateEnd() );
			}

			if( filter.getActive() != null ) {
				query.setParameter( "active", filter.getActive() );
			}

			if( StringUtils.isNotEmpty( filter.getDescription() ) ) {
				query.setParameter( "description", filter.getDescription().toUpperCase() );
			}

			if( filter.getNewPromotions() != null ) {
				query.setParameter( "today", new Date() );
			}

			promotionList = query.getResultList();

		} catch( RuntimeException e ) {
			e.printStackTrace();
			throw e;
		}
		return promotionList;
	}

	@Override
	public List < Promotion > findAll() throws Exception {
		List < Promotion > promotionList = null;
		try {

			StringBuilder jpql = new StringBuilder();
			jpql.append( " FROM Promotion pm" );
			jpql.append( " ORDER BY pm.startDate asc " );

			Query query = session.createQuery( jpql.toString() );

			promotionList = query.getResultList();

		} catch( RuntimeException e ) {

		}
		return promotionList;
	}

	public static void main( String[] args ) {
		List < Product > productList = null;
		ProductDAO dao = new ProductDAO();
		dao.setSession( SessionThreadLocal.getSession() );
		ProductFilter ft = new ProductFilter();
		// productList = dao.find( ft );

		List < Product > promotionProducts = new ArrayList<>();

		if( productList != null && ! productList.isEmpty() ) {
			int count = 0;
			for( Product product: productList ) {
				promotionProducts.add( product );
				count ++ ;
				if( count == 10 ) {
					break;
				}
			}
		}

		// PromotionDAO promoDAO = new PromotionDAO();
		// promoDAO.setSession( SessionThreadLocal.getSession() );
		// List < Promotion > promoList;
		// try {
		// promoList = promoDAO.findAll();
		//
		// for( Promotion promotion: promoList ) {
		// promoDAO.delete( promotion );
		// }
		// SessionThreadLocal.commit();
		// } catch( Exception e ) {
		// SessionThreadLocal.rollback();
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }finally {
		// SessionThreadLocal.closeSession();
		// HibernateUtil.shutdown();
		// System.exit( 0 );
		// }

		// Promotion promotion = new Promotion();
		// promotion.setActive( false );
		// promotion.setDescription( " Promoção teste " );
		// promotion.setDiscountPercentage( 10D );
		// promotion.setStartDate( new Date() );
		// promotion.setEndDate( new Date() );
		// promotion.setProductList( promotionProducts );
		// promotion.setYearly( false );
		//
		// PromotionDAO promoDAO = new PromotionDAO();
		// promoDAO.setSession( SessionThreadLocal.getSession() );
		// try {
		// promoDAO.save( promotion );
		// SessionThreadLocal.commit();
		// } catch( Exception e ) {
		// SessionThreadLocal.rollback();
		// e.printStackTrace();
		// } finally {
		// SessionThreadLocal.closeSession();
		// HibernateUtil.shutdown();
		// }
		// System.exit( 0 );

	}

}
