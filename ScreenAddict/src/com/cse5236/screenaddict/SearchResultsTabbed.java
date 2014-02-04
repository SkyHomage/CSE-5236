package com.cse5236.screenaddict;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import com.cse5236.screenaddict.adapters.MediaObjectListRowAdapter;
import com.cse5236.screenaddict.mediaobjects.IMediaObject;
import com.cse5236.screenaddict.networking.TraktSearch;
import com.cse5236.screenaddict.networking.TraktSearch.SearchCategory;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SearchResultsTabbed extends FragmentActivity implements ActionBar.TabListener {

	private SearchResultsSectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results_tabbed);
		
		Bundle extras = getIntent().getExtras();
		String searchString = "";
		if (extras != null) searchString = extras.getString("SEARCHSTRING");

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SearchResultsSectionsPagerAdapter(getSupportFragmentManager(), searchString);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_non_home_screen, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_home:
			startActivity(new Intent(this, HomeScreen.class));
			break;
		case R.id.action_settings:
			break;
		}
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	public class SearchResultsSectionsPagerAdapter extends FragmentPagerAdapter {
		private String searchString;
		
		public SearchResultsSectionsPagerAdapter(FragmentManager fm, String queryString) {
			super(fm);
			this.searchString = queryString;
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new SearchResultsFragment();
			Bundle args = new Bundle();
			args.putInt(SearchResultsFragment.ARG_SECTION_NUMBER, position + 1);
			args.putString(SearchResultsFragment.ARG_SEARCH_STRING, this.searchString);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_movie_search_results).toUpperCase(l);
			case 1:
				return getString(R.string.title_show_search_results).toUpperCase(l);
			case 2:
				return getString(R.string.title_episode_results).toUpperCase(l);
			case 3:
				return getString(R.string.title_people_results).toUpperCase(l);
			}
			return null;
		}
	}

	public static class SearchResultsFragment extends Fragment {
		public static final String ARG_SECTION_NUMBER = "section_number";
		public static final String ARG_SEARCH_STRING = "search_string";
	
		public SearchResultsFragment() { }

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			String queryString = getArguments().getString(ARG_SEARCH_STRING).replaceAll(" ", "%20");
			TraktSearch search = null;
			ArrayList<IMediaObject> searchResults = new ArrayList<IMediaObject>();
			Resources res = getResources();
			String urlString = res.getString(R.string.trakt_base_url) + res.getString(R.string.trakt_search_base_path);
			
			View footer = null;
			switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
			case 1:
				// Search movies
				search = new TraktSearch(SearchCategory.MOVIE);
				urlString += res.getString(R.string.trakt_search_movies_path);
				footer = (View)inflater.inflate(R.layout.search_footer_show_all_movies, null);
				break;
			case 2:
				// Search shows
				search = new TraktSearch(SearchCategory.SHOW);
				urlString += res.getString(R.string.trakt_search_shows_path);
				footer = (View)inflater.inflate(R.layout.search_footer_show_all_shows, null);
				break;
			case 3:
				// Search episodes
				search = new TraktSearch(SearchCategory.EPISODE);
				urlString += res.getString(R.string.trakt_search_episodes_path);
				footer = (View)inflater.inflate(R.layout.search_footer_show_all_episodes, null);
				break;
			case 4:
				// Search people
				search = new TraktSearch(SearchCategory.PERSON);
				urlString += res.getString(R.string.trakt_search_people_path);
				footer = (View)inflater.inflate(R.layout.search_footer_show_all_people, null);
				break;
			}
			urlString += (res.getString(R.string.trakt_api_key) + res.getString(R.string.trakt_search_query_prefix) + queryString);
			try {
				search.execute(new URL(urlString));
				searchResults.addAll(search.get());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);
			ListView searchResultsList = (ListView) rootView.findViewById(R.id.list_search_results);
			
			MediaObjectListRowAdapter movieAdapter = new MediaObjectListRowAdapter(getActivity(), R.layout.media_list_item, searchResults);
			searchResultsList.addFooterView(footer);
			searchResultsList.setAdapter(movieAdapter);
			return rootView;
		}
	}

}
