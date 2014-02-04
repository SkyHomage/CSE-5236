package com.cse5236.screenaddict;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class HomeScreen extends Activity implements OnClickListener {
	private EditText editSearchString;
	private ImageButton buttonSearch;
	private Button buttonMyMovies, 
						buttonMyTV, 
						buttonActors,
						buttonMyWatchlist,
						buttonMyLists,
						buttonMyCalendar,
						buttonRefresh,
						buttonSettings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		
		editSearchString = (EditText) findViewById(R.id.edit_home_search);
		buttonSearch = (ImageButton) findViewById(R.id.button_home_search);
		buttonSearch.setOnClickListener(this);
		buttonMyMovies = (Button) findViewById(R.id.button_my_movies);
		buttonMyMovies.setOnClickListener(this);
		buttonMyTV = (Button) findViewById(R.id.button_my_tv);
		buttonMyTV.setOnClickListener(this);
		buttonActors = (Button) findViewById(R.id.button_actors);
		buttonActors.setOnClickListener(this);
		buttonMyWatchlist = (Button) findViewById(R.id.button_watchlist);
		buttonMyWatchlist.setOnClickListener(this);
		buttonMyLists = (Button) findViewById(R.id.button_my_lists);
		buttonMyLists.setOnClickListener(this);
		buttonMyCalendar = (Button) findViewById(R.id.button_watch_calendar);
		buttonMyCalendar.setOnClickListener(this);
		buttonRefresh = (Button) findViewById(R.id.button_refresh);
		buttonRefresh.setOnClickListener(this);
		buttonSettings = (Button) findViewById(R.id.button_app_settings);
		buttonSettings.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_home_search:
			// Execute a Search
			String searchString = editSearchString.getText().toString();
			Log.i("[DEBUG: HOMESCREEN]", "Search String: " + searchString);
			if (searchString != null && !searchString.equals("")) {
				Intent i = new Intent(this, SearchResultsTabbed.class);
				i.putExtra("SEARCHSTRING", searchString);
				startActivity(i);
				editSearchString.setText("");
			}
			break;
		case R.id.button_my_movies:
			popupMsg("Goto: My Movies");
			break;
		case R.id.button_my_tv:
			popupMsg("Goto: My TV");
			break;
		case R.id.button_actors:
			popupMsg("Goto: Actors");
			break;
		case R.id.button_watchlist:
			popupMsg("Goto: Watchlist");
			break;
		case R.id.button_my_lists:
			popupMsg("Goto: My Lists");
			break;
		case R.id.button_watch_calendar:
			popupMsg("Goto: Watch Calendar");
			break;
		case R.id.button_refresh:
			popupMsg("Refresh");
			break;
		case R.id.button_app_settings:
			popupMsg("Goto: App Settings");
			break;
		}
	}
	
	private void popupMsg(String msg) {
		AlertDialog msgDialog = new AlertDialog.Builder(this).create();
		msgDialog.setMessage(msg);
		msgDialog.show();
	}

}
