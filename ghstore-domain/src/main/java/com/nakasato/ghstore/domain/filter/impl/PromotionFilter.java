package com.nakasato.ghstore.domain.filter.impl;

import java.util.Date;
import java.util.List;

import com.nakasato.ghstore.domain.filter.Filter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;

public class PromotionFilter extends Filter {

	private List < Product > productList;
	private List < StoreCategory > categoryList;

	private Double startDiscount;
	private Double endDiscount;

	private Date startDateBegin;
	private Date startDateEnd;

	private Date endDateBegin;
	private Date endDateEnd;

	private Boolean active;

	private Boolean newPromotions;

	public List < Product > getProductList() {
		return productList;
	}

	public void setProductList( List < Product > productList ) {
		this.productList = productList;
	}

	public Double getStartDiscount() {
		return startDiscount;
	}

	public void setStartDiscount( Double startDiscount ) {
		this.startDiscount = startDiscount;
	}

	public Double getEndDiscount() {
		return endDiscount;
	}

	public void setEndDiscount( Double endDiscount ) {
		this.endDiscount = endDiscount;
	}

	public Date getStartDateBegin() {
		return startDateBegin;
	}

	public void setStartDateBegin( Date startDateBegin ) {
		this.startDateBegin = startDateBegin;
	}

	public Date getStartDateEnd() {
		return startDateEnd;
	}

	public void setStartDateEnd( Date startDateEnd ) {
		this.startDateEnd = startDateEnd;
	}

	public Date getEndDateBegin() {
		return endDateBegin;
	}

	public void setEndDateBegin( Date endDateBegin ) {
		this.endDateBegin = endDateBegin;
	}

	public Date getEndDateEnd() {
		return endDateEnd;
	}

	public void setEndDateEnd( Date endDateEnd ) {
		this.endDateEnd = endDateEnd;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive( Boolean active ) {
		this.active = active;
	}

	public List < StoreCategory > getCategoryList() {
		return categoryList;
	}

	public void setCategoryList( List < StoreCategory > categoryList ) {
		this.categoryList = categoryList;
	}

	public Boolean getNewPromotions() {
		return newPromotions;
	}

	public void setNewPromotions( Boolean newPromotions ) {
		this.newPromotions = newPromotions;
	}

}
