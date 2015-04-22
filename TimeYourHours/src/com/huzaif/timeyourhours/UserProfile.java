package com.huzaif.timeyourhours;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;

public class UserProfile extends Activity {
	
	String emailidDB, firstnameDB, lastnameDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_user_profile);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return false;
	}
	
	public void logout(View view){
		Intent it=new Intent(this,HomeScreen.class);
		startActivity(it);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK)
        {
			Intent ik=new Intent(this,TimerActivity.class);
			startActivity(ik);
        }
		return false;
	}
	
	public void displayUserProfile(String UserID){
		
	}

}
