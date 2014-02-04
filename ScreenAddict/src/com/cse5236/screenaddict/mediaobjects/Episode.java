package com.cse5236.screenaddict.mediaobjects;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;

import android.util.Log;

public class Episode implements IMediaObject {
	private String title = "", show = "", overview = "";
	private int seasonNo, episodeNo;
	private URL url, poster;
	
	public Episode(org.json.simple.JSONObject resultObject) {
		try {
			JSONObject showJSON = (JSONObject) resultObject.get("show");
			JSONObject episodeJSON = (JSONObject) resultObject.get("episode");
			if (episodeJSON.get("title") != null) this.title = episodeJSON.get("title").toString();
			if (showJSON.get("title") != null) this.show = showJSON.get("title").toString();
			if (episodeJSON.get("season") != null) this.seasonNo += Integer.parseInt(episodeJSON.get("season").toString());
			if (episodeJSON.get("episode") != null) this.episodeNo += Integer.parseInt(episodeJSON.get("episode").toString());
			if (episodeJSON.get("overview") != null) this.overview = episodeJSON.get("overview").toString();
			
			this.url = new URL(episodeJSON.get("url").toString());
			JSONObject images = (JSONObject) showJSON.get("images");
			this.poster = new URL(images.get("poster").toString().replace(".jpg", "-138.jpg"));
			Log.i("[DEBUG]", "Screen URL: " + this.poster.toString());
		} catch (MalformedURLException ex) {
			Log.e("[EXCEPT] - Episode.class", "Bad Trakt URL", ex);
		}
	}

	@Override
	public String getTitle() { 
		return this.show; 
	}

	@Override
	public String getYear() { 
		return "S" + String.format("%02d", this.seasonNo) + "E" + String.format("%02d", this.episodeNo) + " - " + this.title;
	}

	@Override
	public URL getTraktPageURL() { return this.url; }

	@Override
	public String getSummary() { return this.overview; }

	@Override
	public URL getPrimaryImage() { return this.poster; }

}
