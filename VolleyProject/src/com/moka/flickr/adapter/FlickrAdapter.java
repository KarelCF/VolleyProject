package com.moka.flickr.adapter;

import com.example.volleyproject.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class FlickrAdapter extends BaseAdapter {
	
	private Context context = null;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private String[] imageUrls;
	private DisplayImageOptions options = null;
	
	public FlickrAdapter(Context context, String[] imageUrls) {
		this.context = context;
		this.imageUrls = imageUrls; 
		// 需要对imageLoader进行初始化, 否则会报错:"ImageLoader must be init with configuration before using"
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		initLoaderOptions();
	}
	
	private void initLoaderOptions() {
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(false)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}
	
	@Override
	public int getCount() {
		return imageUrls.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView flickrImageView;
		LayoutInflater inflater = LayoutInflater.from(context);
		if (convertView == null) {
			flickrImageView = (ImageView) inflater.inflate(R.layout.image_item, parent, false);
		} else {
			flickrImageView = (ImageView) convertView;
		}
		imageLoader.displayImage(imageUrls[position], flickrImageView, options);
		return flickrImageView;
	}

	
}
