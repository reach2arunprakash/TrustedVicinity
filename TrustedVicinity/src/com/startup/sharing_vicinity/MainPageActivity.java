package com.startup.sharing_vicinity;

import java.util.List;

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
		newsItemInfoList = dataManager.getFullNewsFeedList();
		newsFeedListview = (ListView) findViewById(R.id.newsfeedlistview);

		newsFeedListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					Toast.makeText(appContext, "Coming Soon", Toast.LENGTH_LONG).show();
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

	}

	@Override
	protected void refreshDisplayList() {
		Toast.makeText(appContext, "refreshing", Toast.LENGTH_SHORT).show();
		newsItemInfoList.clear();
		newsItemInfoList.addAll(dataManager.getDataBasedOnOptions(activeDrawerItem,activeCategoryTab));
		newsFeedListAdapter.notifyDataSetChanged();
	}

	public void booksClicked(View v){
		activeCategoryTab = 0;
		Toast.makeText(appContext, "books clicked", Toast.LENGTH_SHORT).show();
		refreshDisplayList();
	}
	
	public void ridesClicked(View v){
		activeCategoryTab = 1;
		Toast.makeText(appContext, "rides clicked", Toast.LENGTH_SHORT).show();
		refreshDisplayList();
	}

	public void ticketsClicked(View v){
		activeCategoryTab = 2;
		Toast.makeText(appContext, "tickets clicked", Toast.LENGTH_SHORT).show();
		refreshDisplayList();
	}

}
