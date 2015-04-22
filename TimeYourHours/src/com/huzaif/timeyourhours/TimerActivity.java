package com.huzaif.timeyourhours;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TimerActivity extends Activity {
		
	final Context context = this;
	private Handler mHandler = new Handler(); 
	private long startTime; 
	private long elapsedTime; 
	private final int REFRESH_RATE = 100; 
	private String hours,minutes,seconds,milliseconds,timeduration=null,loggedUserId=null,StartTime=null,StopTime=null,ST=null;
	private long secs,mins,hrs; 
	private boolean stopped = false; 
	ToggleButton tg;
	//private final String userId=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_timer);
	
		tg=(ToggleButton) findViewById(R.id.pauseButton);
		Intent mIntent = getIntent();
		loggedUserId=mIntent.getStringExtra("loggedInUser");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timer, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		
		case R.id.userprofile:
			userprofile();
			break;
		
		case R.id.statsactivity:
			statsscreen();
			break;
			
		//case R.id.guide:
			//guide();
			//break;
		
		case R.id.logout_lable:
			logout();
			break;
			}
	return false;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		return false;
	}	
	
			public void startClick (View view){ 
				showStopButton(); 

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
				String currentDateandTime = sdf.format(new Date());
				StartTime=currentDateandTime;
				
				SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
				 ST= sdf1.format(new Date());
				
				
				timerMethod();
				
				
			} 
			
			private Runnable startTimer = new Runnable() { 
				public void run() { 
					elapsedTime = System.currentTimeMillis() - startTime; 
					updateTimer(elapsedTime); 
					mHandler.postDelayed(this,REFRESH_RATE); 
					} 
				};
				
				public void timerMethod(){
					if(stopped){ 
						startTime = System.currentTimeMillis() - elapsedTime; 
						} else{ 
							startTime = System.currentTimeMillis(); 
							} 
					mHandler.removeCallbacks(startTimer); 
					mHandler.postDelayed(startTimer, 0);
				}
		
			
			public void pauseClick (View view){ 
				//hideStopButton(); 

				((TextView)findViewById(R.id.timeDuration)).setVisibility(View.GONE);
				if((tg.isChecked()))
					{
						mHandler.removeCallbacks(startTimer); 
						stopped = true;						
					}
					else
					{
						timerMethod();
						
					}
				} 
			
			
			public void stopClick (View view){	
				hours=null;
				minutes=null;
				seconds=null;
				
				mHandler.removeCallbacks(startTimer); 				
				stopped = false;
				hideStopButton();
				((TextView)findViewById(R.id.timer)).setText("00:00:00"); 
				((TextView)findViewById(R.id.timerMs)).setText(".0"); 
				((TextView)findViewById(R.id.timeDuration)).setVisibility(View.VISIBLE);
				
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String currentDateandTime = sdf.format(new Date());
				StopTime=currentDateandTime;
				
				
				DatabaseHandler dtb=new DatabaseHandler(this);
				String rowDB=dtb.saveTime(loggedUserId,ST,StopTime,timeduration);
				
				//Toast.makeText(getApplicationContext(),loggedUserId,Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(),ST,Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(),StopTime,Toast.LENGTH_SHORT).show();
				//Toast.makeText(getApplicationContext(),timeduration,Toast.LENGTH_SHORT).show();
				//Toast.makeText(getApplicationContext(),rowDB,Toast.LENGTH_LONG).show();
				ST=null;
				
				}
	
			
			
			private void showStopButton(){ 
				((Button)findViewById(R.id.startButton)).setVisibility(View.GONE); 
				((Button)findViewById(R.id.pauseButton)).setVisibility(View.VISIBLE);
				((Button)findViewById(R.id.stopButton)).setVisibility(View.VISIBLE);
				((TextView)findViewById(R.id.timeDuration)).setVisibility(View.GONE);
				} 
			
			private void hideStopButton(){ 
				((Button)findViewById(R.id.startButton)).setVisibility(View.VISIBLE); 
				((Button)findViewById(R.id.pauseButton)).setVisibility(View.GONE); 
				((Button)findViewById(R.id.stopButton)).setVisibility(View.GONE); 
				} 
			
			
			private void updateTimer (float time){
				secs = (long)(time/1000); 
				mins = (long)((time/1000)/60); 
				hrs = (long)(((time/1000)/60)/60); 
				
				/* Convert the seconds to String * and format to ensure it has * a leading zero when required */ 
				
				secs = secs % 60; 
				seconds=String.valueOf(secs); 
				if(secs == 0){ 
					seconds = "00"; 
					} 
				
				if(secs <10 && secs > 0){ 
					seconds = "0"+seconds; 
					} 
				
				/* Convert the minutes to String and format the String */ 
				
				mins = mins % 60; 
				minutes=String.valueOf(mins); 
				
				if(mins == 0){ 
					minutes = "00"; 
					} 
				
				if(mins <10 && mins > 0){ 
					minutes = "0"+minutes; 
					} 
				
				/* Convert the hours to String and format the String */ 
				
				hours=String.valueOf(hrs); 
				
				if(hrs == 0){ 
					hours = "00"; 
					} 
				
				if(hrs <10 && hrs > 0){ 
					hours = "0"+hours; 
					} 
				
				/* Although we are not using milliseconds on the timer in this example * I included the code in the event that you wanted to include it on your own */ 
				
				milliseconds = String.valueOf((long)time); 
				
				if(milliseconds.length()==2){ 
					milliseconds = "0"+milliseconds; 
					} 
				
				if(milliseconds.length()<=1){ 
					milliseconds = "00"; 
					} 
				
				milliseconds = milliseconds.substring(milliseconds.length()-3, milliseconds.length()-2);
				
				/* Setting the timer text to the elapsed time */ 
				

				timeduration=hours.concat(":".concat(minutes.concat(":".concat(seconds.concat(".".concat(milliseconds))))));
				
				((TextView)findViewById(R.id.timeDuration)).setText(timeduration); 
				((TextView)findViewById(R.id.timer)).setText(hours + ":" + minutes + ":" + seconds);
				((TextView)findViewById(R.id.timerMs)).setText("." + milliseconds); 
				
				//createNotification(timeduration);
				
				
			} 

			
			@SuppressWarnings("deprecation")
			private void createNotification(String timedur) {

			    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			    int unique_id = 145558;
			    Intent nintent = new Intent();
			    nintent.setClass(this,TimerActivity.class);
			    PendingIntent pin = PendingIntent.getActivity(getApplicationContext(),0, nintent, 0);
			    String title = "Notification";
			    String body = timedur;
			    Notification n = new Notification(R.drawable.ic_launcher, body,
			            System.currentTimeMillis());
			    n.contentIntent = pin;
			    n.setLatestEventInfo(getApplicationContext(), title, body, pin);

			    n.defaults = Notification.DEFAULT_ALL;
			    nm.notify(unique_id, n);

			}
			
			
	public void logout(){
		Intent it=new Intent(this,HomeScreen.class);
		startActivity(it);
	}
	
	public void userprofile(){
		/*
		Intent it=new Intent(this,UserProfile.class);
		startActivity(it);
		*/
		
		
		Toast.makeText(getApplicationContext(),"UserId"+loggedUserId,Toast.LENGTH_LONG).show();
		
		
		String [] userProfile=new String[]{"userid","email ID","firstname","lastname"};
		
		DatabaseHandler dblogin = new DatabaseHandler(this);
		userProfile=dblogin.getUserProfile(loggedUserId);
		
		String displayText="   USER ID : "+userProfile[0]+"\n\n   EMAIL ID : "+userProfile[1]+"\n\n   NAME : "+userProfile[2]+" "+userProfile[3]+"\n\n";
			
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
 
			alertDialogBuilder.setTitle(" USER PROFILE ");
 
			alertDialogBuilder
				.setMessage(displayText)
				.setCancelable(false)
				.setPositiveButton("Edit",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						Toast.makeText(getApplicationContext(),"To Edit User Details Screen",Toast.LENGTH_LONG).show();
					}
				  })
				.setNegativeButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
	}
	
	public void statsscreen(){
		
		Intent it=new Intent(this,StatsActivity.class);
		it.putExtra("loggedUserId",loggedUserId);
		startActivity(it); 
		
		/*
		int i=0; 
		Cursor dBData;
		DatabaseHandler dbh=new DatabaseHandler(this);
		String [] dbD=null;
		String ID="empty";
		dBData=dbh.getTimerTable(loggedUserId);
		
		if(dBData.moveToFirst()){
	           do{     
	        	   	//ID = dBData.getString(0);
	        	   	String USERID= dBData.getString(dBData.getColumnIndex("timerid"));
		       	    String DATE= dBData.getString(dBData.getColumnIndex("timerdate"));
		       	    String TASK= dBData.getString(dBData.getColumnIndex("task"));
		       	    String START_TIME= dBData.getString(dBData.getColumnIndex("starttime"));
		       	    String STOP_TIME=dBData.getString(dBData.getColumnIndex("stoptime"));
		       	    String DURATION=dBData.getString(dBData.getColumnIndex("timeduration"));       	   
		            String blankspace=" , ";
		            dbD[i]=ID.concat(blankspace.concat(USERID.concat(blankspace
		            		.concat(DATE.concat(blankspace.concat(TASK.concat(blankspace.concat(START_TIME.concat(blankspace
		            		.concat(STOP_TIME.concat(blankspace.concat(DURATION))))))))))));   
		            i++;
	           }
	           while(dBData.moveToNext());
	       	}
		
		String dbDt="";
		int rowCount=dbh.getRowCountTimer();
		for(int j=0;j<rowCount;j++){
			dbDt=dbDt.concat(dbD[j].concat("\n"));
			}
		
		Toast.makeText(getApplicationContext(),dbDt,Toast.LENGTH_LONG).show();

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
 
			alertDialogBuilder.setTitle(" Your Time Statistics ");
 
			alertDialogBuilder
				.setMessage(dbDt)
				.setCancelable(false)
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						
					}
				  })
				.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				}); 
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show(); */
		
		
	}
	
	//public void guide(){
	//	Intent it=new Intent(this,GuideActivity.class);
	//	startActivity(it);
	//}
	
	public void openSubMenu(View view){
		openOptionsMenu();
	}
}
