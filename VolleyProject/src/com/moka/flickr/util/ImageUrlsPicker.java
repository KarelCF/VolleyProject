package com.moka.flickr.util;

import java.util.ArrayList;
import java.util.List;

import com.moka.flickr.entity.FlickrResponse.Photos.Photo;

public class ImageUrlsPicker {
    
	private static ImageUrlsPicker imageUrlsPicker = null;
	private List<String> imageUrlsList = new ArrayList<String>();
	private String[] imageUrls;
	
	private ImageUrlsPicker() {}
	
	public static ImageUrlsPicker getInstance() {
		if (imageUrlsPicker == null)
			imageUrlsPicker = new ImageUrlsPicker();
		return imageUrlsPicker;
	}
	
	public String[] pickUpUrls(List<Photo> photoList) {
		for (int i = 0; i < photoList.size(); i++)
			imageUrlsList.add(photoList.get(i).getUrl());
		imageUrls = (String[]) imageUrlsList.toArray(new String[imageUrlsList.size()]);
		return imageUrls;
	}
	
}
