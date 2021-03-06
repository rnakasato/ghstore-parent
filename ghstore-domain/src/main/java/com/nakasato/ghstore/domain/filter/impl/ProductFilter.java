package com.nakasato.ghstore.domain.filter.impl;

import java.util.ArrayList;
import java.util.List;

import com.nakasato.ghstore.domain.filter.Filter;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.StoreCategory;
import com.nakasato.ghstore.domain.product.Subcategory;
import com.nakasato.ghstore.domain.product.Tag;

public class ProductFilter extends Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String exactlyName;
	private String code;
	private StoreCategory category;
	private Subcategory subcategory;
	private Double price;
	private Integer stock;
	private Integer status;
	private Product product;
	private List < Subcategory > subcategoryList;
	private List < String > selectedSubcategoryIds;
	private List < StoreCategory > storeCategoryList;
	private List < Tag > tagList;
	private List < Product > notInList;
	private Boolean activePromotion;

	private Boolean newProducts;

	private Double initialValue;
	private Double finalValue;

	private Integer maxResults;

	public ProductFilter() {
		category = new StoreCategory();
		subcategory = new Subcategory();
		subcategory.setStoreCategory( new StoreCategory() );
		subcategoryList = new ArrayList<>();
		storeCategoryList = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public StoreCategory getCategory() {
		return category;
	}

	public void setCategory( StoreCategory category ) {
		this.category = category;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory( Subcategory subcategory ) {
		this.subcategory = subcategory;
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
		this.stock = stock;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus( Integer status ) {
		this.status = status;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct( Product product ) {
		this.product = product;
	}

	public List < Subcategory > getSubcategoryList() {
		return subcategoryList;
	}

	public void setSubcategoryList( List < Subcategory > subcategoryList ) {
		this.subcategoryList = subcategoryList;
	}

	public List < StoreCategory > getStoreCategoryList() {
		return storeCategoryList;
	}

	public void setStoreCategoryList( List < StoreCategory > storeCategoryList ) {
		this.storeCategoryList = storeCategoryList;
	}

	public List < String > getSelectedSubcategoryIds() {
		return selectedSubcategoryIds;
	}

	public void setSelectedSubcategoryIds( List < String > selectedSubcategoryIds ) {
		this.selectedSubcategoryIds = selectedSubcategoryIds;
	}

	public String getCode() {
		return code;
	}

	public void setCode( String code ) {
		this.code = code;
	}

	public Double getInitialValue() {
		return initialValue;
	}

	public void setInitialValue( Double initialValue ) {
		this.initialValue = initialValue;
	}

	public Double getFinalValue() {
		return finalValue;
	}

	public void setFinalValue( Double finalValue ) {
		this.finalValue = finalValue;
	}

	public List < Tag > getTagList() {
		return tagList;
	}

	public void setTagList( List < Tag > tagList ) {
		this.tagList = tagList;
	}

	public Boolean getActivePromotion() {
		return activePromotion;
	}

	public void setActivePromotion( Boolean activePromotion ) {
		this.activePromotion = activePromotion;
	}

	public List < Product > getNotInList() {
		return notInList;
	}

	public void setNotInList( List < Product > notInList ) {
		this.notInList = notInList;
	}

	public Boolean getNewProducts() {
		return newProducts;
	}

	public void setNewProducts( Boolean newProducts ) {
		this.newProducts = newProducts;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults( Integer maxResults ) {
		this.maxResults = maxResults;
	}

	public String getExactlyName() {
		return exactlyName;
	}

	public void setExactlyName( String exactlyName ) {
		this.exactlyName = exactlyName;
	}

}
