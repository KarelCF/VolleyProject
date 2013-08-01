package com.moka.flickr.volleyproject;

import java.util.List;

import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.volleyproject.R;
import com.google.gson.Gson;
import com.moka.flickr.adapter.FlickrAdapter;
import com.moka.flickr.entity.FlickrResponse;
import com.moka.flickr.entity.FlickrResponse.Photos.Photo;
import com.moka.flickr.util.FlickrApiHelper;
import com.moka.flickr.util.ImageUrlsPicker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private EditText tagsText = null;
	private Button searchBtn = null;
	private GridView imagesGridView = null;
	private FlickrAdapter adapter = null;
	private RequestQueue queue = null;
	private Gson gson = new Gson();
	private String responseJsonString = null;
	private String[] imageUrls;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tagsText = (EditText) findViewById(R.id.tagsText);
		searchBtn = (Button) findViewById(R.id.searchBtn);
		imagesGridView = (GridView) findViewById(R.id.imagesGridView);
		
		searchBtn.setOnClickListener(new SearchListener());
		imagesGridView.setOnItemClickListener(new ItemClickListener());
	}
	
	private class ItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			startBrowseImageActivity(position);
		}
	}
	
	private void startBrowseImageActivity(int position) {
		Intent intent = new Intent(MainActivity.this, ImagePagerActivity.class);
		intent.putExtra("POSITION", position);
		intent.putExtra("URLS", imageUrls);
		startActivity(intent);
		
	}
	
	private class SearchListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			hideSoftInput();
			String tags = tagsText.getText().toString();
			FlickrApiHelper helper = new FlickrApiHelper(tags);
			String requesURL = helper.createRequestURL();
			getFlickrData(requesURL);
		}
		
	}
	
	private void hideSoftInput() {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
		imm.hideSoftInputFromWindow(tagsText.getWindowToken(), 0);
	}
	
	private void getFlickrData(String requesURL) {

		queue = Volley.newRequestQueue(this);
		queue.add(new JsonObjectRequest(Method.GET, requesURL, null,
            new Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					responseJsonString = response.toString();
					FlickrResponse flickrResponse = gson.fromJson(responseJsonString, FlickrResponse.class);
					prepareFlickrAdapter(flickrResponse);
				}
            }, null));
		queue.start();
	}
	
	private void prepareFlickrAdapter(FlickrResponse flickrResponse) {
		try {
			imageUrls = getImageUrls(flickrResponse);
			adapter = new FlickrAdapter(this, imageUrls);
		} catch (Exception e) {
			Toast.makeText(MainActivity.this, "Flickr service is unavailable", Toast.LENGTH_SHORT).show();
		}
		imagesGridView.setAdapter(adapter);
	}
	
	private String[] getImageUrls(FlickrResponse flickrResponse) {
		List<Photo> photoList = flickrResponse.getPhotos().getPhotoList();
		ImageUrlsPicker picker = ImageUrlsPicker.getInstance();
		return picker.pickUpUrls(photoList);
	}

}
