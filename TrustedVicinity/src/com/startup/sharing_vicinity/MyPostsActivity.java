package com.startup.sharing_vicinity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.Toast;

public class MyPostsActivity extends BaseActivity {

	private ListView myPostsListview;
	private MyPostsListAdapter myPostsListAdapter;
	static List<NewsItemInfo> myPostsInfoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(myPostsListview);
		//		newsItemInfoList = dataManager.getFullNewsFeedList();
		System.out.println("before posts");
		runMyPostsUrlDownloadTask();
		myPostsInfoList = new ArrayList<NewsItemInfo>();
		myPostsListview = (ListView) findViewById(R.id.newsfeedlistview);

		myPostsListview.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				showAnimation = false;
				return false;
			}
		});

		myPostsListAdapter = new MyPostsListAdapter(appContext,R.layout.myposts_list_item);
		myPostsListview.setAdapter(myPostsListAdapter);
	}
	
	@Override
	protected void refreshDisplayList(boolean isSearch) {
		myPostsInfoList.clear();
		myPostsInfoList.addAll(dataManager.getMyPosts());
		myPostsListAdapter.notifyDataSetChanged();
	}


}
