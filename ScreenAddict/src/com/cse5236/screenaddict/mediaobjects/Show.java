package com.cse5236.screenaddict.mediaobjects;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;

import android.util.Log;

public class Show implements IMediaObject {
	private String title = "", overview = "", year = "";
	private URL url, poster;
	
	public Show(JSONObject showJSON)
	{
		if (showJSON.get("title") != null) this.title = showJSON.get("title").toString();
		if (showJSON.get("year") != null) this.year = showJSON.get("year").toString();
		if (showJSON.get("overview") != null) this.overview = showJSON.get("overview").toString();
		try {
			this.url = new URL(showJSON.get("url").toString());
			JSONObject images = (JSONObject) showJSON.get("images");
			this.poster = new URL(images.get("poster").toString().replace(".jpg", "-138.jpg"));
		} catch (MalformedURLException ex) {
			Log.e("Show.class", "Bad Trakt URL", ex);
		}
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getYear() {
		return this.year;
	}

	@Override
	public URL getTraktPageURL() {
		return this.url;
	}

	@Override
	public String getSummary() {
		return this.overview;
	}

	@Override
	public URL getPrimaryImage() {
		return this.poster;
	}
	
	@Override
	public String toString() {
		return "Title: " + this.title + 
				", Year: " + this.year + 
				", Overview: " + this.overview + 
				", URL: " + this.url;
	}

}
