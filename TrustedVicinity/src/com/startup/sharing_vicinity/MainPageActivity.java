package com.startup.sharing_vicinity;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainPageActivity extends BaseActivity {

	private ListView newsFeedListview;
	private NewsFeedListAdapter newsFeedListAdapter;
	static List<NewsItemInfo> newsItemInfoList;
	static int activeCategoryTab = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		newsItemInfoList = dataManager.getFullNewsFeedList();
		newsItemInfoList = new ArrayList<NewsItemInfo>();
		newsFeedListview = (ListView) findViewById(R.id.newsfeedlistview);

		newsFeedListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SharedPreferences generalPrefs = getSharedPreferences("general", 0);
				Editor editor = generalPrefs.edit();
				editor.putString("receiver", newsItemInfoList.get(position).getUserId());
				editor.commit();
			}
		});

		newsFeedListview.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				showAnimation = false;
				return false;
			}
		});

		newsFeedListAdapter = new NewsFeedListAdapter(appContext,R.layout.news_feed_list_item);
		newsFeedListview.setAdapter(newsFeedListAdapter);

		if(activeDrawerItem==1){
			if(MainPageActivity.activeCategoryTab==0){
				findViewById(R.id.books_tab).setBackgroundColor(getResources().getColor(R.color.blueSelected));
				findViewById(R.id.rides_tab).setBackgroundColor(getResources().getColor(R.color.blueUnSelected));
				findViewById(R.id.tickets_tab).setBackgroundColor(getResources().getColor(R.color.blueUnSelected));
			}
			else if(MainPageActivity.activeCategoryTab==1){
				findViewById(R.id.books_tab).setBackgroundColor(getResources().getColor(R.color.blueUnSelected));
				findViewById(R.id.rides_tab).setBackgroundColor(getResources().getColor(R.color.blueSelected));
				findViewById(R.id.tickets_tab).setBackgroundColor(getResources().getColor(R.color.blueUnSelected));
			}
			else{
				findViewById(R.id.books_tab).setBackgroundColor(getResources().getColor(R.color.blueUnSelected));
				findViewById(R.id.rides_tab).setBackgroundColor(getResources().getColor(R.color.blueUnSelected));
				findViewById(R.id.tickets_tab).setBackgroundColor(getResources().getColor(R.color.blueSelected));
			}
		}
		else if(activeDrawerItem==2){
			if(MainPageActivity.activeCategoryTab==0){
				findViewById(R.id.books_tab).setBackgroundColor(getResources().getColor(R.color.orangeSelected));
				findViewById(R.id.rides_tab).setBackgroundColor(getResources().getColor(R.color.orangeUnSelected));
				findViewById(R.id.tickets_tab).setBackgroundColor(getResources().getColor(R.color.orangeUnSelected));
			}
			else if(MainPageActivity.activeCategoryTab==1){
				findViewById(R.id.books_tab).setBackgroundColor(getResources().getColor(R.color.orangeUnSelected));
				findViewById(R.id.rides_tab).setBackgroundColor(getResources().getColor(R.color.orangeSelected));
				findViewById(R.id.tickets_tab).setBackgroundColor(getResources().getColor(R.color.orangeUnSelected));
			}
			else{
				findViewById(R.id.books_tab).setBackgroundColor(getResources().getColor(R.color.orangeUnSelected));
				findViewById(R.id.rides_tab).setBackgroundColor(getResources().getColor(R.color.orangeUnSelected));
				findViewById(R.id.tickets_tab).setBackgroundColor(getResources().getColor(R.color.orangeSelected));
			}

		}

	}

	@Override
	protected void refreshDisplayList(boolean isSearch) {
		Toast.makeText(appContext, "refreshing", Toast.LENGTH_SHORT).show();
		newsItemInfoList.clear();
		newsItemInfoList.addAll(dataManager.getDataBasedOnOptions(activeDrawerItem,activeCategoryTab,isSearch));
		if(newsFeedListAdapter==null || newsFeedListview==null){
			if(newsFeedListAdapter==null)
				newsFeedListAdapter = new NewsFeedListAdapter(appContext,R.layout.news_feed_list_item);
			if(newsFeedListview==null){
				newsFeedListview = (ListView) findViewById(R.id.newsfeedlistview);

				newsFeedListview.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						SharedPreferences generalPrefs = getSharedPreferences("general", 0);
						Editor editor = generalPrefs.edit();
						editor.putString("receiver", newsItemInfoList.get(position).getUserId());
						editor.commit();
					}
				});

				newsFeedListview.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						showAnimation = false;
						return false;
					}
				});
			}
			newsFeedListview.setAdapter(newsFeedListAdapter);
		}
		newsFeedListAdapter.notifyDataSetChanged();
	}

	public void openChat(View v){
		Toast.makeText(appContext, "chat clicked", Toast.LENGTH_SHORT).show();
	}

	public void booksClicked(View v){
		activeCategoryTab = 0;
		if(activeDrawerItem==1){
			findViewById(R.id.books_tab).setBackgroundColor(getResources().getColor(R.color.blueSelected));
			findViewById(R.id.rides_tab).setBackgroundColor(getResources().getColor(R.color.blueUnSelected));
			findViewById(R.id.tickets_tab).setBackgroundColor(getResources().getColor(R.color.blueUnSelected));
		}
		else{
			findViewById(R.id.books_tab).setBackgroundColor(getResources().getColor(R.color.orangeSelected));
			findViewById(R.id.rides_tab).setBackgroundColor(getResources().getColor(R.color.orangeUnSelected));
			findViewById(R.id.tickets_tab).setBackgroundColor(getResources().getColor(R.color.orangeUnSelected));
		}
		refreshDisplayList(false);
	}

	public void ridesClicked(View v){
		activeCategoryTab = 1;
		if(activeDrawerItem==1){
			findViewById(R.id.books_tab).setBackgroundColor(getResources().getColor(R.color.blueUnSelected));
			findViewById(R.id.rides_tab).setBackgroundColor(getResources().getColor(R.color.blueSelected));
			findViewById(R.id.tickets_tab).setBackgroundColor(getResources().getColor(R.color.blueUnSelected));
		}
		else{
			findViewById(R.id.books_tab).setBackgroundColor(getResources().getColor(R.color.orangeUnSelected));
			findViewById(R.id.rides_tab).setBackgroundColor(getResources().getColor(R.color.orangeSelected));
			findViewById(R.id.tickets_tab).setBackgroundColor(getResources().getColor(R.color.orangeUnSelected));
		}
		refreshDisplayList(false);
	}

	public void ticketsClicked(View v){
		activeCategoryTab = 2;
		if(activeDrawerItem==1){
			findViewById(R.id.books_tab).setBackgroundColor(getResources().getColor(R.color.blueUnSelected));
			findViewById(R.id.rides_tab).setBackgroundColor(getResources().getColor(R.color.blueUnSelected));
			findViewById(R.id.tickets_tab).setBackgroundColor(getResources().getColor(R.color.blueSelected));
		}
		else{
			findViewById(R.id.books_tab).setBackgroundColor(getResources().getColor(R.color.orangeUnSelected));
			findViewById(R.id.rides_tab).setBackgroundColor(getResources().getColor(R.color.orangeUnSelected));
			findViewById(R.id.tickets_tab).setBackgroundColor(getResources().getColor(R.color.orangeSelected));
		}
		refreshDisplayList(false);
	}

}
