package com.startup.sharing_vicinity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;

class LinkDownloader extends AsyncTask<String, Integer, String > {

	ProgressDialog dialog = null;
	UrlDataDownloadListener urlDataDownloadListener;
	Context ctx;

	public LinkDownloader(Context c) {
		ctx = c;
	}

	public void setDataDownloadListener(UrlDataDownloadListener dataDownloadListener) {
		this.urlDataDownloadListener = dataDownloadListener;
	}

	protected String doInBackground(String... urls) {
		try {
			
			String output = "";

//			DefaultHttpClient httpClient = new DefaultHttpClient();
//
//			HttpGet request = new HttpGet(urls[0]);
//			HttpResponse response = httpClient.execute(request);
//			HttpEntity httpEntity = response.getEntity();
//			InputStream is = httpEntity.getContent();           
//			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//			String line = null;
//			
//			while ((line = reader.readLine()) != null && !isCancelled()) {
//				output += line;
//			}
//			is.close();
//			reader.close();
//
//			is = null;
//			reader = null;
//			httpEntity = null;
//			response = null;
//			request = null;
//			httpClient = null;
//			if(isCancelled())
//				return null;

			return output;
		} 

		catch (Exception e) {
			//			Log.d("Exception", e.toString());
			return null;
		}
	}


	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new ProgressDialog(ctx, android.R.style.Theme_Translucent);
		//		dialog.setCancelable(false);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.setMessage("Refreshing list..");
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.getWindow().setGravity(Gravity.CENTER);

		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(true);
		dialog.show();
	}

	protected void onPostExecute(String result) {

		if(dialog!=null){
			dialog.dismiss();
			dialog = null;
		}
		if(result != null)
			urlDataDownloadListener.dataDownloadedSuccessfully(result);
		else
			urlDataDownloadListener.dataDownloadFailed();
	}

	public static interface UrlDataDownloadListener {
		void dataDownloadedSuccessfully(String data);
		void dataDownloadFailed();
	}
}
