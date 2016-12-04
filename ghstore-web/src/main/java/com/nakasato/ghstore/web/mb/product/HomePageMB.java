package com.nakasato.ghstore.web.mb.product;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.util.SaveDirectory;
import com.nakasato.ghstore.domain.carrier.HomeProductsCarrier;
import com.nakasato.ghstore.domain.gallery.GalleryItem;
import com.nakasato.ghstore.domain.product.Product;
import com.nakasato.ghstore.domain.product.group.MostSoldProduct;
import com.nakasato.ghstore.domain.user.Customer;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;
import com.nakasato.ghstore.web.mb.user.LoginMB;

@ManagedBean( name = "homePageMB" )
@ViewScoped
public class HomePageMB extends BaseMB {

	@ManagedProperty( value = "#{loginMB}" )
	private LoginMB loginMB;

	private List < GalleryItem > galleryList;
	private HomeProductsCarrier carrier;

	private Product selectedProduct;
	private MostSoldProduct selectedRecommended;
	private MostSoldProduct selectedMostSold;

	@PostConstruct
	public void init() {
		initGalleryList();
		initHomeProducts();
	}

	@Override
	public void clearFilter() {

	}

	private void initGalleryList() {
		try {
			ICommand commandFind = FactoryCommand.build( new GalleryItem(), EOperation.FINDALL );
			galleryList = commandFind.execute().getEntityList();
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	private void initHomeProducts() {
		try {
			carrier = new HomeProductsCarrier();
			Customer customer = ( Customer ) loginMB.getLoggedUser();
			carrier.setCustomer( customer );
			ICommand commandFind = FactoryCommand.build( carrier, EOperation.FIND );
			commandFind.execute();

		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}

	public String getImagePath( GalleryItem item ) {
		return getImagePath( item.getImage() );
	}

	public String getImagePath( Product product ) {
		return getImagePath( product.getImage() );
	}

	public String getImagePath( String imagePath ) {
		String path;
		if( StringUtils.isNotEmpty( imagePath ) ) {
			path = SaveDirectory.REQUEST_IMG_DIR + imagePath;
		} else {
			path = "default.jpg";
		}
		return path;
	}

	public LoginMB getLoginMB() {
		return loginMB;
	}

	public void setLoginMB( LoginMB loginMB ) {
		this.loginMB = loginMB;
	}

	public List < GalleryItem > getGalleryList() {
		return galleryList;
	}

	public void setGalleryList( List < GalleryItem > galleryList ) {
		this.galleryList = galleryList;
	}

	public HomeProductsCarrier getCarrier() {
		return carrier;
	}

	public void setCarrier( HomeProductsCarrier carrier ) {
		this.carrier = carrier;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct( Product selectedProduct ) {
		this.selectedProduct = selectedProduct;
	}

	public MostSoldProduct getSelectedRecommended() {
		return selectedRecommended;
	}

	public void setSelectedRecommended( MostSoldProduct selectedRecommended ) {
		this.selectedRecommended = selectedRecommended;
	}

	public MostSoldProduct getSelectedMostSold() {
		return selectedMostSold;
	}

	public void setSelectedMostSold( MostSoldProduct selectedMostSold ) {
		this.selectedMostSold = selectedMostSold;
	}

}
