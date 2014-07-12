package com.startup.sharing_vicinity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
		   		Intent i = new Intent(getApplicationContext(), MainPageActivity.class);
				startActivity(i);
		       }
		     };
		 
		     // assign click listener to the OK button (btnOK)
		     btnEnter.setOnClickListener(oclBtnOk);
	}
}
