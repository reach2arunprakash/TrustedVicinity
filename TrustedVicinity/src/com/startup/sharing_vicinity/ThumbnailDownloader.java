package com.startup.sharing_vicinity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ThumbnailDownloader extends AsyncTask<String, Integer, Bitmap>{

	ImageView imview;
	Context ctx;

	public ThumbnailDownloader(Context c, ImageView imview){
		this.imview = imview;
		this.ctx = c;
	}
	
	@Override
	protected Bitmap doInBackground(String... urls) {
		String filename = urls[0];
//		try {
//			return getImageBitmap(filename);
//		}
//		catch(FileNotFoundException e){
			try{
				HttpURLConnection connection = (HttpURLConnection)new URL(getThumbUrl(filename)).openConnection();

				connection.connect();
				InputStream input= connection.getInputStream();
				Bitmap bitmap = BitmapFactory.decodeStream(input);
				return bitmap;

			}
			catch (Exception f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
			}
//		} 
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result != null && imview!=null){
			imview.setImageBitmap(result);
		}
	}

	public String getThumbUrl(String imageUrl){
		return "http://img.youtube.com/vi/"+imageUrl+"/default.jpg";
	}

}
