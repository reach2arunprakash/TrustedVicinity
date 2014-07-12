package com.startup.sharing_vicinity;

import java.io.IOException;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewPost extends Activity implements LocationListener{
	private LocationManager locationManager;
	private double lat,lng;
	private Location location = null;
	private Context context = this;
	// The minimum distance to change Updates in meters
		private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

		// The minimum time between updates in milliseconds
		private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
		Spinner buyorsell, itemtype;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.w("NewPost OnCreate ","Myapp ");
		setContentView(R.layout.new_feed);
		buyorsell = (Spinner) findViewById(R.id.BuyOrSell);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.buy_or_sell, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		buyorsell.setAdapter(adapter);
		
		itemtype = (Spinner) findViewById(R.id.ItemType);

		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
		        R.array.type_item, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		itemtype.setAdapter(adapter);

		locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider -> use
	    // default
		

		Log.w("Before LocaitonManager ","Myapp ");

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER,
				MIN_TIME_BW_UPDATES,
				MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		Criteria criteria = new Criteria();
		location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
		try{
			Log.w("After LocaitonManager ","Myapp ");
//			double lat = location.getLatitude();
//			double lon = location.getLongitude();
		}
		finally {
			Log.w("Finally ","Myapp ");			
		}
		Log.w("After lat and lng ","Myapp ");
//		ParseGeoPoint currentLoc = new ParseGeoPoint(lat, lon);
		
//		ParseObject LocationTable = new ParseObject("Location");
//		LocationTable.put("location", currentLoc);
//		LocationTable.saveInBackground();

		Button post = (Button) findViewById(R.id.Post);
		post.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lat = (double) (location.getLatitude());
			    lng = (double) (location.getLongitude());
			    Log.w(""+Double.toString(lat),"Myapp ");
			    Log.w(""+Double.toString(lng),"Myapp ");

				EditText description = (EditText) findViewById(R.id.description);
				String message = description.getText().toString();
				
				
				Parse.initialize(context, "24hUoVTzig5LnTD4mGqu1eH75sHYrNXVSSsIQEiU", "N6y4zorDVMCqqWqBU56wiZyXK4hUTtkaq6nzqsfP");
				ParseGeoPoint location = new ParseGeoPoint(lat,lng);
								
				ParseObject query = new ParseObject("FeedTable");
				Log.w("apple "+buyorsell.getSelectedItem().toString()+" "+itemtype.getSelectedItem().toString(),"Myapp ");
				Log.w(""+location+" "+description.getText().toString(),"Myapp ");
				query.put("type",buyorsell.getSelectedItem().toString());
				query.put("tags",itemtype.getSelectedItem().toString());
				query.put("location",location);
				query.put("description", description.getText().toString());
				try {
					query.save();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//send message !!
				
				
			}
		});
		
	}

	@Override
	public void onLocationChanged(Location arg0) {
		lat = (int) arg0.getLatitude();
	    lng = (int) (arg0.getLongitude());
	    Log.w(" "+lat,"Myapp ");
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
