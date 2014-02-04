package com.cse5236.screenaddict.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.cse5236.screenaddict.R;
import com.cse5236.screenaddict.mediaobjects.IMediaObject;
import com.cse5236.screenaddict.networking.TraktImageDownload;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MediaObjectListRowAdapter extends ArrayAdapter<IMediaObject> {
	private Context context;
	private int layoutResourceID;
	private List<IMediaObject> data = null;

	public MediaObjectListRowAdapter(Context context, int layoutResourceId, ArrayList<IMediaObject> objects) {
		super(context, layoutResourceId, objects);
		this.context = context;
		this.layoutResourceID = layoutResourceId;
		this.data = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MediaItemHolder holder = null;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(this.layoutResourceID, parent, false);
			
			holder = new MediaItemHolder();
			holder.title = (TextView)row.findViewById(R.id.media_list_item_title);
			holder.year = (TextView)row.findViewById(R.id.media_list_item_year);
			holder.poster = (ImageView)row.findViewById(R.id.media_list_item_image);
			
			row.setTag(holder);
		}
		else {
			holder = (MediaItemHolder)row.getTag();
		}
		
		IMediaObject item = this.data.get(position);
		holder.title.setText(item.getTitle());
		holder.year.setText(item.getYear());
		TraktImageDownload imgDownload = new TraktImageDownload();
		imgDownload.execute(item.getPrimaryImage());
		try {
			holder.poster.setImageBitmap(imgDownload.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}
	
	private static class MediaItemHolder
	{
		TextView title;
		TextView year;
		ImageView poster;
	}
	
	
}
