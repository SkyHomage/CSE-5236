package com.cse5236.screenaddict.networking;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class TraktImageDownload extends AsyncTask<URL, Integer, Bitmap> {

	@Override
	protected Bitmap doInBackground(URL... params) {
		Bitmap bitmap = null;
		InputStream stream = null;
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inSampleSize = 1;
		
		try {
			stream = (InputStream) params[0].getContent();
			bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
			stream.close();
		} catch (Exception e) {
			//TODO
			e.printStackTrace();
		}
		return bitmap;
	}

}
