package com.startup.sharing_vicinity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Profile extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_page);
		Context ctx = this;
		
		Button Save = (Button) findViewById(R.id.Save);
		Save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//When User Saves
			}
		});
		
		Button Cancel = (Button) findViewById(R.id.Cancel);
		Cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// User Cancels
				finish();
			}
		});
		
		ImageView holder = (ImageView) findViewById(R.id.profile_pic);

		ThumbnailDownloader thumbnailDownloader = new ThumbnailDownloader(ctx,holder);

		try{
			thumbnailDownloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"https://lh6.googleusercontent.com/-zL2hGAaDwmU/AAAAAAAAAAI/AAAAAAAB7rc/CBYnGem3KMk/s120-c/photo.jpg");
		}
		catch(Exception e){
			if(thumbnailDownloader!=null)
				thumbnailDownloader.cancel(true);
		}
		
	}
}
