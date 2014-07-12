package com.startup.sharing_vicinity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void showMainPage(View v){
		Intent i = new Intent(this, MainPageActivity.class);
		startActivity(i);
	}
	
	public void showNewPost(View v){
		Intent i = new Intent(this, NewPost.class);
		startActivity(i);
	}
}
