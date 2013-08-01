package com.moka.flickr.util;


public class FlickrApiHelper {
	
	private static final String URL = "http://api.flickr.com/services/rest/?method=flickr.photos.search";
	private static final String API_KEY = "8d0e28afe160faac7785baee156c7115";
	private static int per_page = 50;
	private static String jsonFormat = "json&nojsoncallback=1";
	private String tags = "android";
	
	public FlickrApiHelper(String tags) {
		if ("".equals(tags) || tags == null) {
			this.tags = "android";
		} else {
			this.tags = tags;
		}
	}
	
	public String createRequestURL() {
		return URL + 
			"&api_key=" + API_KEY + 
			"&tags=" + tags + 
			"&per_page=" + per_page + 
			"&format=" + jsonFormat;
	}
	
}
