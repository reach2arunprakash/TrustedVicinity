package com.startup.sharing_vicinity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class BaseActivity extends Activity {

	protected static final String[] drawerItems = {"Profile","Selling","Buying","My Posts","Chats","Exit"};
	protected static final String[] tabItems = {"Books","Rides","Tickets"};
	public static final CharSequence UserName = "Anurag";
	public static final CharSequence PhoneNo = "9624912480124";

	protected Context appContext;
	protected DrawerLayout mDrawerLayout;
	protected ListView mDrawerView;
	protected ActionBarDrawerToggle mDrawerToggle;
	static int activeDrawerItem = 1;
	public static boolean showAnimation = true;
	DataManager dataManager;

	ProgressDialog loadingDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Parse.initialize(this, "24hUoVTzig5LnTD4mGqu1eH75sHYrNXVSSsIQEiU", "N6y4zorDVMCqqWqBU56wiZyXK4hUTtkaq6nzqsfP");

		appContext = this;
		dataManager = new DataManager();
		setContentView(R.layout.main_page_activity);
		if(activeDrawerItem==1 || activeDrawerItem ==2){
			findViewById(R.id.tabBar).setVisibility(View.VISIBLE);
		}
		else{
			findViewById(R.id.tabBar).setVisibility(View.GONE);
		}
		mDrawerView = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerView.setAdapter(new ArrayAdapter<String>(appContext, R.layout.drawer_item, drawerItems));
		mDrawerView.setItemChecked(activeDrawerItem, true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		mDrawerView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mDrawerLayout.closeDrawer(mDrawerView);
				if(activeDrawerItem!=arg2){
					if(arg2==5)
						finish();
					showAnimation = true;
					setTitle(drawerItems[arg2]);
					actionOnDrawerItemClick(arg2);
					//					refreshDisplayList(null);
				}
			}

		});

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		setTitle(drawerItems[activeDrawerItem]);

		SharedPreferences generalPrefs = getSharedPreferences("general", 0);
		if(generalPrefs.getBoolean("initial", true)){
			Editor editor = generalPrefs.edit();
			editor.putBoolean("initial", false);
			editor.commit();
			showLoginActivity();
		}
		else{
			runUrlDownloadTask();
		}
		handleIntent(getIntent());

	}

	private void showLoginActivity() {
		Intent i =new Intent(appContext, LoginPage.class);
		startActivity(i);
	}

	@Override public void onBackPressed() { 
		if(!mDrawerLayout.isDrawerOpen(mDrawerView)){ 
			mDrawerLayout.openDrawer(mDrawerView);
		}
	}

	protected void actionOnDrawerItemClick(int index) {
		activeDrawerItem = index;
		if(index==0)
			loadProfilePage();
		else if(index==1)
			loadSellingPage();
		else if(index==2)
			loadBuyingPage();
		else if(index==3)
			loadMyPostsPage();
		else if(index==4){
			loadChatsPage();
		}
	}

	private void loadSellingPage() {
		finish();
		Intent i =new Intent(appContext, MainPageActivity.class);
		startActivity(i);
	}

	private void loadBuyingPage() {
		finish();
		Intent i =new Intent(appContext, MainPageActivity.class);
		startActivity(i);
	}

	private void loadProfilePage() {
//		finish();
		Intent i =new Intent(appContext, Profile.class);
		startActivity(i);
	}

	private void loadMyPostsPage() {
		finish();
		Intent i =new Intent(appContext, MyPostsActivity.class);
		startActivity(i);
	}

	private void loadChatsPage() {
		//		finish();
		//		Intent i =new Intent(appContext, AllChatsActivity.class);
		//		startActivity(i);
	}

	private void runUrlDownloadTask(){
		loadingDialog = ProgressDialog.show(appContext, null,null);
		loadingDialog.setContentView(new ProgressBar(appContext));
		loadingDialog.setCanceledOnTouchOutside(false);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("FeedTable");
		query.orderByDescending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> Objects, ParseException e) {
				if (e == null) {
					dataManager.saveData(Objects);
					if(loadingDialog!=null)
						loadingDialog.dismiss();
					refreshDisplayList(false);
				}
				else{
					/* Write code for query failure */
				}
			}
		});
	}

	public void runMyPostsUrlDownloadTask(){

		if(loadingDialog!=null)
			loadingDialog.dismiss();
		loadingDialog = ProgressDialog.show(appContext, null,null);
		loadingDialog.setContentView(new ProgressBar(appContext));
		loadingDialog.setCanceledOnTouchOutside(false);

		SharedPreferences generalPrefs = getSharedPreferences("general", 0);
		String username = generalPrefs.getString("username", null);

		System.out.println("Querying for "+username);
		System.out.println("Yo!");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("FeedTable").whereEqualTo("username", username);
		query.orderByDescending("createdAt");

		System.out.println("About to run Query");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> Objects, ParseException e) {
				if (e == null) {
					System.out.println("Size"+Objects.size());
					dataManager.saveMyPostsData(Objects);
					System.out.println("saved!");
					if(loadingDialog!=null)
						loadingDialog.dismiss();
					refreshDisplayList(false);
				}
				else{
					System.out.println("Errored!");
				}
			}
		});


		System.out.println("got 1st");

	}

	protected void refreshDisplayList(boolean isSearch) {
		Log.d("tag", "rehrh");
		//should be over-ridden by childs
	}

	public boolean haveNetworkConnection() {

		ConnectivityManager cm = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = cm.getActiveNetworkInfo();
		if (i != null){
			if (i.isConnected())
				if (i.isAvailable())
					return true;
		}
		return false;
	}


	public void showNoInternetAlert(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(appContext, android.R.style.Theme_Translucent));
		alertDialogBuilder.setTitle("Download Failed");
		alertDialogBuilder
		.setMessage("Please check your internet connection")
		.setCancelable(false)
		.setIcon(R.drawable.ic_launcher)
		.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				//				MainActivity.this.finish();
			}
		})
		.setNegativeButton("Retry",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
				runUrlDownloadTask();
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void onResume(){
		super.onResume();
		
	}


	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerView);
		menu.findItem(R.id.refresh).setVisible(!drawerOpen);
		menu.findItem(R.id.newpost).setVisible(!drawerOpen);
		menu.findItem(R.id.search).setVisible(!drawerOpen);

		return super.onPrepareOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...
		switch (item.getItemId()) {
		case R.id.refresh:
			showAnimation = true;
			runUrlDownloadTask();
			return true;

		case R.id.newpost:
			showNewPostActivity();
			return true;

		case R.id.search:
			onSearchRequested();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	public boolean onSearchRequested() {
		startSearch(null, false, new Bundle(), false);
		return true;
	}


	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
//			Toast.makeText(appContext, query, Toast.LENGTH_LONG).show();
			DataManager.searchQuery = query;
			refreshDisplayList(true);
			//			dataManager.doMySearch(query);
			//			listAdapter.notifyDataSetChanged();
			//			if(videoInfoList.size()==0){
			//				Toast.makeText(appContext, "No matching record found", Toast.LENGTH_SHORT).show();
			//			}
		}
	}

	private void showNewPostActivity() {
		Intent intent = new Intent(appContext,NewPost.class);
		startActivity(intent);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


}
