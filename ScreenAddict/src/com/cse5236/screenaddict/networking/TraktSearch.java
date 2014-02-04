package com.cse5236.screenaddict.networking;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.cse5236.screenaddict.mediaobjects.Episode;
import com.cse5236.screenaddict.mediaobjects.IMediaObject;
import com.cse5236.screenaddict.mediaobjects.Movie;
import com.cse5236.screenaddict.mediaobjects.Person;
import com.cse5236.screenaddict.mediaobjects.Show;

import android.os.AsyncTask;
import android.util.Log;

public class TraktSearch extends AsyncTask<URL, Integer, ArrayList<IMediaObject>> {
	public static enum SearchCategory {MOVIE, SHOW, EPISODE, PERSON};

	private SearchCategory searchType;
	
	public TraktSearch(SearchCategory searchCategory) {
		this.searchType = searchCategory;
	}
	
	@Override
	protected ArrayList<IMediaObject> doInBackground(URL... params) {
		ArrayList<IMediaObject> results = new ArrayList<IMediaObject>();
		for (URL url : params) {
			try {
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				
				String fullResponseString = "", inputLine;
				while ((inputLine = reader.readLine()) != null) {
					fullResponseString += inputLine;
				}
				Log.i("[DEBUG: execute]", fullResponseString);
				in.close();

				results.addAll(parseJSON(fullResponseString));
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return results;
	}
	
	private ArrayList<IMediaObject> parseJSON(String s) {
		ArrayList<IMediaObject> results = new ArrayList<IMediaObject>();
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(s);
			JSONArray mediaObjects = (JSONArray)obj;
			Log.i("[DEBUG: parseJSON]", "Result Count: " + mediaObjects.size());
			for (int i = 0; i < mediaObjects.size(); i++) {
				IMediaObject mediaObject = null;
				JSONObject resultObject = (JSONObject)mediaObjects.get(i);
				switch (this.searchType) {
					case MOVIE:
						mediaObject = new Movie(resultObject);
						break;
					case SHOW:
						mediaObject = new Show(resultObject);
						break;
					case EPISODE:
						mediaObject = new Episode(resultObject);
						break;
					case PERSON:
						mediaObject = new Person(resultObject);
						break;
				}
				results.add(mediaObject);
			}
		}
		catch (ParseException pe) {
			Log.i("[DEBUG: parseJSON]", "position: " + pe.getPosition());
			Log.i("[DEBUG: parseJSON]", pe.toString());
		}
		return results;
	}

}
