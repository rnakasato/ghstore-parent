package com.nakasato.ghstore.web.mb.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;

import com.nakasato.core.util.enums.EOperation;
import com.nakasato.ghstore.core.ICommand;
import com.nakasato.ghstore.core.application.Result;
import com.nakasato.ghstore.core.util.ImageUtils;
import com.nakasato.ghstore.core.util.ListUtils;
import com.nakasato.ghstore.core.util.SaveDirectory;
import com.nakasato.ghstore.domain.gallery.GalleryItem;
import com.nakasato.ghstore.factory.impl.FactoryCommand;
import com.nakasato.ghstore.web.mb.BaseMB;

@ManagedBean( name = "galleryMB" )
@ViewScoped
public class GalleryMB extends BaseMB {

	// lista de itens que já estão cadastrados
	private List < GalleryItem > itemList;

	// lista de itens que serão adicionados
	private List < GalleryItem > newItemList;

	private GalleryItem selectedItem;

	private String newImage;

	@PostConstruct
	public void init() {
		if( newItemList == null ) {
			newItemList = new ArrayList<>();
		}
		newImage = "";
		listGallery();
	}

	public void listGallery() {
		try {
			ICommand commandFind = FactoryCommand.build( new GalleryItem(), EOperation.FINDALL );
			itemList = commandFind.execute().getEntityList();
		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doNewUpload( FileUploadEvent event ) {

		String fileName = event.getFile().getFileName();
		try {
			ImageUtils.copyImage( event.getFile().getFileName(), event.getFile().getInputstream() );

			GalleryItem item = new GalleryItem();
			item.setImage( fileName );
			newItemList.add( item );

			addMessage( fileName + " carregado com sucesso!" );
		} catch( IOException e ) {
			addMessage( fileName + " não carregado" );
			e.printStackTrace();
		}
	}

	public void doUpdateUpload( FileUploadEvent event ) {
		String fileName = event.getFile().getFileName();
		try {
			ImageUtils.copyImage( event.getFile().getFileName(), event.getFile().getInputstream() );
			newImage = fileName;
			addMessage( fileName + " carregado com sucesso!" );
		} catch( IOException e ) {
			addMessage( fileName + " não carregado" );
			e.printStackTrace();
		}
	}

	public void save() {
		StringBuilder msgBuilder = new StringBuilder();
		try {
			if( ListUtils.isNotEmpty( newItemList ) ) {

				for( GalleryItem galleryItem: newItemList ) {
					ICommand commandSave;
					commandSave = FactoryCommand.build( galleryItem, EOperation.SAVE );
					Result result = commandSave.execute();
					if( StringUtils.isNotEmpty( result.getMsg() ) ) {
						msgBuilder.append( result.getMsg() );
					}

				}
				if( msgBuilder.length() > 0 ) {
					addMessage( msgBuilder.toString() );
				} else {
					newItemList = new ArrayList<>();
					listGallery();
				}
			}
		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void update() {

		try {
			if( selectedItem != null && StringUtils.isNotEmpty( newImage ) ) {
				selectedItem.setImage( newImage );
				ICommand commandUpdate;
				commandUpdate = FactoryCommand.build( selectedItem, EOperation.UPDATE );
				Result result = commandUpdate.execute();

				listGallery();

			} else {
				addMessage( "Selecione a imagem para alterar" );
			}
		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete( GalleryItem item ) {
		try {

			ICommand commandDelete;
			commandDelete = FactoryCommand.build( item, EOperation.DELETE );
			Result result = commandDelete.execute();
			listGallery();
		} catch( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getImagePath( GalleryItem item ) {
		return getImagePath( item.getImage() );
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

	public List < GalleryItem > getItemList() {
		return itemList;
	}

	public void setItemList( List < GalleryItem > itemList ) {
		this.itemList = itemList;
	}

	public List < GalleryItem > getNewItemList() {
		return newItemList;
	}

	public void setNewItemList( List < GalleryItem > newItemList ) {
		this.newItemList = newItemList;
	}

	public GalleryItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem( GalleryItem selectedItem ) {
		newImage = selectedItem.getImage();
		this.selectedItem = selectedItem;
	}

	@Override
	public void clearFilter() {

	}

	public String getNewImage() {
		return newImage;
	}

	public void setNewImage( String newImage ) {
		this.newImage = newImage;
	}

}
