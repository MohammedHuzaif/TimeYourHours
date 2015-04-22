package com.huzaif.timeyourhours;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class StatsActivity extends Activity {

	String loggedUserId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_stats);

		Intent intent = getIntent();
		loggedUserId = intent.getExtras().getString("loggedUserId");			
		
		getData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return false;
	}
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK)
        {
			finish();
			Intent ik=new Intent(this,TimerActivity.class);
			startActivity(ik);
        }
		return super.onKeyDown(keyCode, event);
	}
	
	public void getData(){
		
		//int rowCount=dbh.getRowCountTimer();
		//String [] dbData=null, temp;
		//String str, delimiter = ",";
		
		DatabaseHandler dbh=new DatabaseHandler(this);
		TableLayout ll = (TableLayout) findViewById(R.id.tablelayoutstats);
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
	
   	    //	}
		
   	    /* TextView statsTextView;
		statsTextView = (TextView) findViewById(R.id.statsText);
		statsTextView.setText(START_TIME);	 */
		
		
		/*
		TableRow row1= new TableRow(this);
        TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row1.setLayoutParams(lp1);
        //TextView qty1 = new TextView(this);
        TextView sno = new TextView(this);
        TextView date = new TextView(this);
        TextView task = new TextView(this);
        TextView sttime = new TextView(this);
        TextView qtendtime = new TextView(this);
        TextView duration = new TextView(this);
     
        sno.setText(" S.NO ");
        row1.addView(sno);
        date.setText(" DATE ");
        row1.addView(date);
        task.setText(" TASK ");
        row1.addView(task);
        sttime.setText(" START TIME ");
        row1.addView(sttime);
        qtendtime.setText(" STOP TIME ");
        row1.addView(qtendtime);
        duration.setText(" DURATION ");
        row1.addView(duration);
        ll.addView(row1,0);
		
		
        for (int i=0; k <=rowCount; k++,i++) {
        	
        	
        	str=dbData[i];
    		temp = str.split(delimiter);
    	
    		//String USERID = temp[1];
       	    String DATE = temp[2];
       	    String TASK = temp[3];
       	    String START_TIME = temp[4];
       	    String STOP_TIME = temp[5];
       	    String DURATION = temp[6];
        	
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
		    }		   */
	} 
}
