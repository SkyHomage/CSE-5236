package com.cse5236.screenaddict.mediaobjects;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;

public class Person implements IMediaObject {
	private String name = "", biography = "", birthdate = "";
	private URL url, poster;

	public Person(JSONObject resultObject) {
		if (resultObject.get("name") != null) this.name = resultObject.get("name").toString();
		if (resultObject.get("biography") != null) this.biography = resultObject.get("biography").toString();
		if (resultObject.get("birthday") != null) this.birthdate = resultObject.get("birthday").toString();
		if (resultObject.get("url") != null)
			try {
				this.url = new URL(resultObject.get("url").toString());
				JSONObject images = (JSONObject) resultObject.get("images");
				this.poster = new URL(images.get("headshot").toString());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	public String getTitle() {
		return this.name;
	}

	@Override
	public String getYear() {
		return this.birthdate;
	}

	@Override
	public URL getTraktPageURL() {
		return this.url;
	}

	@Override
	public String getSummary() {
		return this.biography;
	}

	@Override
	public URL getPrimaryImage() {
		return this.poster;
	}

}
