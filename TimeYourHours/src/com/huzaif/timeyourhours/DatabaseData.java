package com.huzaif.timeyourhours;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class DatabaseData extends Activity {

	String loggedUserId; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_database_data);
		
		Intent intent = getIntent();
		loggedUserId = intent.getExtras().getString("loggedUserId");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.database_data, menu);
		return false;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK)
        {
			Intent ik=new Intent(this,HomeScreen.class);
			startActivity(ik);
        }
		return false;
	}

	public void getDatabaseUserTable(View view)
	{	
		DatabaseHandler db=new DatabaseHandler(this);
		String dbDt=" ";
		int rowCount=db.getRowCount();
		
		TextView dbdata=(TextView) findViewById(R.id.getData1);
		
		String [] dbData = db.getDatabase();
		
		for(int i=0;i<rowCount;i++){
			dbDt=dbDt.concat(dbData[i].concat("\n"));
			}
		dbdata.setText(dbDt);
	}
	
	public void getDatabaseTimerTable(View view){ //getTimerTable		
		
		DatabaseHandler dbh=new DatabaseHandler(this);
		TableLayout ll = (TableLayout) findViewById(R.id.tablelayoutTimerTable);
		Cursor dBData;
		int k=1;

		dBData=dbh.getTimerTable(loggedUserId);
				
		if(dBData.moveToFirst()){
	           do{     
	        	   	//ID = dBData.getString(0);
	        	   	//String USERID= dBData.getString(dBData.getColumnIndex("timerid"));
		       	    String DATE= dBData.getString(dBData.getColumnIndex("timerdate"));
		       	    String TASK= dBData.getString(dBData.getColumnIndex("task"));
		       	    String START_TIME= dBData.getString(dBData.getColumnIndex("starttime"));
		       	    String STOP_TIME=dBData.getString(dBData.getColumnIndex("stoptime"));
		       	    String DURATION=dBData.getString(dBData.getColumnIndex("timeduration"));  
		       	    
		            TableRow row= new TableRow(this);
			        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
			        row.setLayoutParams(lp);		        
			        
			        TextView snod = new TextView(this);
			        TextView dated = new TextView(this);
			        TextView taskd = new TextView(this);
			        TextView sttimed = new TextView(this);
			        TextView qtendtimed = new TextView(this);
			        TextView durationd = new TextView(this);
			        
			        snod.setText(Integer.toString(k));
			        row.addView(snod);
			        dated.setText(DATE);
			        row.addView(dated);
			        taskd.setText(TASK);
			        row.addView(taskd);
			        sttimed.setText(START_TIME);
			        row.addView(sttimed);
			        qtendtimed.setText(STOP_TIME);
			        row.addView(qtendtimed);
			        durationd.setText(DURATION);
			        row.addView(durationd);
			        ll.addView(row,k);
		       	    
		       	    k++;
	           }while(dBData.moveToNext());
       	}

		
	} 
	
	public void gotoHome(View view){
		Intent i=new Intent(this,HomeScreen.class);
		startActivity(i);
	}
}
