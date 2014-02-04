package com.cse5236.screenaddict.datastorage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DatabaseHelper {
	private static final String DATABASE_NAME = "ScreenAddict.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "Movies";
	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private static final String INSERT = "insert into " + TABLE_NAME + "(title, year, summary, image, url) values (?, ?, ?, ?, ?)" ;
	
	public DatabaseHelper(Context context) {
		this.context = context;
		ScreenAddictOpenHelper openHelper = new ScreenAddictOpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		this.insertStmt = this.db.compileStatement(INSERT);
	}
	
	public long insert(String title, String year, String summary, URL primaryImageURL, URL traktPageURL) {
		this.insertStmt.bindString(1, title);
		this.insertStmt.bindString(2, year);
		this.insertStmt.bindString(3, summary);
		this.insertStmt.bindString(4, primaryImageURL.toString());
		this.insertStmt.bindString(5, traktPageURL.toString());
		return this.insertStmt.executeInsert();
	}
	
	public void deleteAll() {
		this.db.delete(TABLE_NAME, null, null);
	}
	
	public List<String> selectAll(String title, String year, String summary, URL primaryImageURL, URL traktPageURL) {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(TABLE_NAME, 
				new String[] { "title",  "year", "summary", "image", "url" }, 
				"title = '" + title + "' AND year = '" + year + "' AND summary = '" + summary + "' AND image = '" + primaryImageURL.toString() + "' AND url = '" + traktPageURL.toString() + "'", 
				null, 
				null, 
				null, 
				"title desc");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));
				list.add(cursor.getString(1));
				list.add(cursor.getString(2));
				list.add(cursor.getString(3));
				list.add(cursor.getString(4));
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	private static class ScreenAddictOpenHelper extends SQLiteOpenHelper {
		ScreenAddictOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY, title TEXT, year TEXT, summary TEXT, image TEXT, url TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Example", "Upgrading database; this will drop and recreate the tables.");
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	        onCreate(db);
		}
	}
}
