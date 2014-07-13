package com.startup.sharing_vicinity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

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
		
		ImageView holder = (ImageView) findViewById(R.id.profile_pic);

		ThumbnailDownloader thumbnailDownloader = new ThumbnailDownloader(ctx,holder);

		try{
			thumbnailDownloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"https://lh6.googleusercontent.com/-zL2hGAaDwmU/AAAAAAAAAAI/AAAAAAAB7rc/CBYnGem3KMk/s120-c/photo.jpg");
		}
		catch(Exception e){
			if(thumbnailDownloader!=null)
				thumbnailDownloader.cancel(true);
		}
		
		SeekBar radius = (SeekBar) findViewById(R.id.seekBar1);
		radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				TextView range = (TextView) findViewById(R.id.textView3);
				range.setText("Post display range : "+Double.toString(seekBar.getProgress()/10.0)+"km");
			}
		});
		
		EditText username = (EditText) findViewById(R.id.user_name);
		username.setText(BaseActivity.UserName);
		
		EditText phoneno = (EditText) findViewById(R.id.phone);
		phoneno.setText(BaseActivity.PhoneNo);	
		
	}
}
