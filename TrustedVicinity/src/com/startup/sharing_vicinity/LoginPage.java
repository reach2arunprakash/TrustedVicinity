package com.startup.sharing_vicinity;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginPage extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);
		
		Button btnEnter;
		btnEnter = (Button) findViewById(R.id.button1);
		
		OnClickListener oclBtnOk = new OnClickListener() {
		       @Override
		       public void onClick(View v) {
		    	   
		    	   EditText username = (EditText)findViewById(R.id.editText1);
		    	   
		    	   SharedPreferences generalPrefs = getSharedPreferences("general", 0);
		    	    Editor editor = generalPrefs.edit();
					editor.putString("username", username.getText().toString());
					editor.commit();
					
					System.out.println("Querying for "+username);
					ParseQuery<ParseObject> query = ParseQuery.getQuery("User");//.whereEqualTo("username", username);
					System.out.println("About to run Query");
					try {
						query.getFirst();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		    	   
		   		Intent i = new Intent(getApplicationContext(), MainPageActivity.class);
				startActivity(i);
		       }
		     };
		 
		     // assign click listener to the OK button (btnOK)
		     btnEnter.setOnClickListener(oclBtnOk);
	}
}
