package com.startup.sharing_vicinity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.startup.sharing_vicinity.LinkDownloader.UrlDataDownloadListener;

public class BaseActivity extends Activity {

	public static final String NEWS_FEED_URL = "http://url to get the news feed";
	protected static final String[] drawerItems = {"Profile","Selling","Buying","My Posts"};
	protected static final String[] tabItems = {"Books","Rides","Tickets"};
	
	protected Context appContext;
	protected DrawerLayout mDrawerLayout;
	protected ListView mDrawerView;
	protected ActionBarDrawerToggle mDrawerToggle;
	private LinkDownloader linkDownloader;
	static int activeDrawerItem = 1;
	public static boolean showAnimation = true;
	DataManager dataManager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appContext = this;
		dataManager = new DataManager();
		setContentView(R.layout.main_page_activity);
		mDrawerView = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerView.setAdapter(new ArrayAdapter<String>(appContext, R.layout.drawer_item, drawerItems));
		mDrawerView.setItemChecked(activeDrawerItem, true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				//				invalidateOptionsMenu();
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				//				invalidateOptionsMenu();
			}
		};
		mDrawerView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mDrawerLayout.closeDrawer(mDrawerView);
				if(activeDrawerItem!=arg2){
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
		
		runUrlDownloadTask();
	}

	protected void actionOnDrawerItemClick(int index) {
		activeDrawerItem = index;
		if(index==0)
			loadProfilePage();
		else if(index==1)
			loadSellingPage();
		else if(index==2)
			loadBuyingPage();
		else
			loadMyPostsPage();
	}

	private void loadSellingPage() {
		refreshDisplayList();
		Toast.makeText(appContext, "loading selling Page", Toast.LENGTH_LONG).show();
	}

	private void loadBuyingPage() {
		refreshDisplayList();
		Toast.makeText(appContext, "loading buying Page", Toast.LENGTH_LONG).show();
	}

	private void loadProfilePage() {
		Toast.makeText(appContext, "loading profile Page", Toast.LENGTH_LONG).show();
	}

	private void loadMyPostsPage() {
		Toast.makeText(appContext, "loading myposts Page", Toast.LENGTH_LONG).show();
	}

	private void runUrlDownloadTask(){
		linkDownloader = new LinkDownloader(appContext);
		linkDownloader.setDataDownloadListener(new UrlDataDownloadListener()
		{
			public void dataDownloadedSuccessfully(String data) {
				dataManager.saveData(data);
				refreshDisplayList();
			}
			public void dataDownloadFailed() {
				showNoInternetAlert();
			}
		});

		try { 
			boolean internetConnected = haveNetworkConnection();
			if(internetConnected){
				linkDownloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, NEWS_FEED_URL);
			}
			else{
				showNoInternetAlert();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void refreshDisplayList() {
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

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showNewPostActivity() {
		Toast.makeText(appContext, "show new post page", Toast.LENGTH_SHORT).show();
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
