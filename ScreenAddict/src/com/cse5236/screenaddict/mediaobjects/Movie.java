package com.cse5236.screenaddict.mediaobjects;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;

import android.util.Log;

public class Movie implements IMediaObject {
	private String title = "", overview = "", year = "";
	private URL url, poster;
	
	public Movie(JSONObject movieJSON) {
		if (movieJSON.get("title") != null)
			this.title = movieJSON.get("title").toString();
		if (movieJSON.get("year") != null)
			this.year = movieJSON.get("year").toString();
		if (movieJSON.get("overview") != null)
			this.overview = movieJSON.get("overview").toString();
		try {
			this.url = new URL(movieJSON.get("url").toString());
			JSONObject images = (JSONObject) movieJSON.get("images");
			this.poster = new URL(images.get("poster").toString().replace(".jpg", "-138.jpg"));
			Log.i("[DEBUG]", "Poster URL: " + this.poster.toString());
		} catch (MalformedURLException ex) {
			Log.e("[EXCEPT] - Movie.class", "Bad Trakt URL", ex);
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
	public URL getPrimaryImage() {
		return this.poster;
	}

	@Override
	public String getSummary() {
		return this.overview;
	}
	
	@Override
	public String toString() {
		return "Title: " + this.title + 
				", Year: " + this.year + 
				", Overview: " + this.overview + 
				", URL: " + this.url;
	}

}
