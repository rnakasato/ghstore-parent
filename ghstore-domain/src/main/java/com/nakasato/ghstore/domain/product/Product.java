package com.nakasato.ghstore.domain.product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.DomainSpecificEntity;
import com.nakasato.ghstore.domain.enums.EStatus;

/**
 * Classe que representa a entidade de Produto
 * 
 * @author Rafael
 *
 */
public class Product extends DomainSpecificEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Integer AVAILABLE = 1;
	public static final Integer UNAVAILABLE = 2;

	private String name;
	private Double price;
	private Integer stock;
	private String image;
	private Date updateDate;
	private Integer status;
	private Double weight;
	private Boolean active;
	private StoreCategory storeCategory;
	private Subcategory subcategory;
	private List < Tag > tagList;

	private List < Promotion > promotionList;

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public StoreCategory getStoreCategory() {
		return storeCategory;
	}

	public void setStoreCategory( StoreCategory storeCategory ) {
		this.storeCategory = storeCategory;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory( Subcategory subcategory ) {
		this.subcategory = subcategory;
	}

	public Double getDiscountPrice() {
		Double value = 0D;
		Promotion promotion = getActivePromotion();
		if( promotion != null && price != null ) {
			Double discount = price * ( ( promotion.getDiscountPercentage() ) / 100 );
			value = price - discount;
		} else {
			value = price;
		}

		return value;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice( Double price ) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock( Integer stock ) {
		if( stock != null && stock > 0 ) {
			status = AVAILABLE;
		} else {
			status = UNAVAILABLE;
		}
		this.stock = stock;
	}

	public String getImage() {
		return image;
	}

	public void setImage( String image ) {
		this.image = image;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate( Date updateDate ) {
		this.updateDate = updateDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus( Integer status ) {
		this.status = status;
	}

	public String getStatusDesc() {
		String statusDesc = null;
		if( status != null ) {
			statusDesc = EStatus.getStatusDesc( status );
		}
		return statusDesc;
	}

	public List < Tag > getTagList() {
		return tagList;
	}

	public void setTagList( List < Tag > tagList ) {
		this.tagList = tagList;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight( Double weight ) {
		this.weight = weight;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive( Boolean active ) {
		this.active = active;
	}

	public List < Promotion > getPromotionList() {
		return promotionList;
	}

	public void setPromotionList( List < Promotion > promotionList ) {
		this.promotionList = promotionList;
	}

	public Promotion getActivePromotion() {
		Promotion promotion = null;
		if( promotionList != null && ! promotionList.isEmpty() ) {
			for( Promotion p: promotionList ) {
				if( p.getActive() ) {
					promotion = p;
					break;
				}
			}
		}

		return promotion;
	}

	public String getActiveDescription() {
		String desc = null;
		if( active != null ) {
			if( active ) {
				desc = "Ativo";
			} else {
				desc = "Inativo";
			}
		}
		return desc;
	}

	@Override
	public boolean isEmpty() {
		boolean isEmpty = false;
		isEmpty = ( this.getSubcategory() == null );
		if( StringUtils.isEmpty( this.getDescription() ) && this.getId() == null
				&& StringUtils.isEmpty( this.getImage() ) && StringUtils.isEmpty( this.getName() )
				&& this.getPrice() == null && this.getStatus() == null && this.getStock() == null
				&& this.getStoreCategory().isEmpty() && this.getSubcategory().isEmpty() ) {
			isEmpty = true;
		}
		return isEmpty;
	}

}
