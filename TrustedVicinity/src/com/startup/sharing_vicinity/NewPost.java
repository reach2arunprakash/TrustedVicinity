package com.startup.sharing_vicinity;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class NewPost extends Activity implements LocationListener{
	private LocationManager locationManager;
	private int lat,lng;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		Spinner buyorsell = (Spinner) findViewById(R.id.BuyOrSell);

//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//		        R.array.buy_or_sell, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
//		buyorsell.setAdapter(adapter);
		
//		Spinner itemtype = (Spinner) findViewById(R.id.ItemType);
//
//		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
//		        R.array.type_item, android.R.layout.simple_spinner_item);
//		// Specify the layout to use when the list of choices appears
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		// Apply the adapter to the spinner
//		itemtype.setAdapter(adapter);
				
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider -> use
	    // default
		/*
	    Criteria criteria = new Criteria();
	    String provider = locationManager.getBestProvider(criteria, false);
	    final Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
*/
		
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
		  .isProviderEnabled(LocationManager.GPS_PROVIDER);

		// check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to 
		// go to the settings
		if (!enabled) {
			Log.w("Not enabled","Myapp ");
		}
		
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
	    String provider = locationManager.getBestProvider(criteria, false);
        final Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        
//		Button post = (Button) findViewById(R.id.Post);
//		post.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				lat = (int) (location.getLatitude());
//			    lng = (int) (location.getLongitude());
//			    Log.w(""+Double.toString(lat),"Myapp ");
//			    Log.w(""+Double.toString(lng),"Myapp ");
//			    
//				EditText description = (EditText) findViewById(R.id.description);
//				String message = description.getText().toString();
//			}
//		});

	    // Initialize the location fields
	    if (location != null) {
	      Log.w("Provider " + provider + " has been selected.","Myapp ");
	      onLocationChanged(location);
	    } else {
		      Log.w("Provider " + provider + " has been selected.","Myapp ");
	      Log.w("Location not available","Myapp ");
	      Log.w("Location not available","Myapp ");
	    }
		
	}

	@Override
	public void onLocationChanged(Location arg0) {
		lat = (int) (arg0.getLatitude());
	    lng = (int) (arg0.getLongitude());
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
