package com.huzaif.timeyourhours;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends Activity {
	public final static String EXTRA_MESSAGE = "com.huzaif.timeyourhours.MESSAGE";
	LinearLayout l1,l2,l3;
	EditText emailidreg, firstnamereg, lastnamereg,pin1reg,pin2reg,emailidlogin11,pinlogin11;
	String emailidreg1, firstnamereg1, lastnamereg1,pin1reg1,pin2reg1,emailidlogin1,pinlogin1;
	String userID=null;
	//String loggedInUser=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home_screen);
		//Log.d("in onCreate", "startedonCreate");
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		/*
		Typeface custom_font=Typeface.createFromAsset(getAssets(), "bebas.ttf");				
		
		// loginbutton1, registerbutton, registerbutton1,loginbutton2
		//emailidlogin, passwordlogin, emailidregister, firstnameregister, lastnameregister, passwordlableregister, loginpasswordregister
		
		
		TextView tx=(TextView)findViewById(R.id.emailidlogin);
		tx.setTypeface(custom_font);
		
		tx=(TextView)findViewById(R.id.passwordlogin);
		tx.setTypeface(custom_font);
		
		tx=(TextView)findViewById(R.id.loginbutton1);
		tx.setTypeface(custom_font);
		
		tx=(TextView)findViewById(R.id.registerbutton);
		tx.setTypeface(custom_font);
		
		tx=(TextView)findViewById(R.id.emailidregister);
		tx.setTypeface(custom_font);
		
		tx=(TextView)findViewById(R.id.firstnameregister);
		tx.setTypeface(custom_font);
		
		tx=(TextView)findViewById(R.id.lastnameregister);
		tx.setTypeface(custom_font);
		
		tx=(TextView)findViewById(R.id.passwordlableregister);
		tx.setTypeface(custom_font);
		
		tx=(TextView)findViewById(R.id.loginpasswordregister);
		tx.setTypeface(custom_font);
		
		tx=(TextView)findViewById(R.id.registerbutton1);
		tx.setTypeface(custom_font);
		
		tx=(TextView)findViewById(R.id.loginbutton2);
		tx.setTypeface(custom_font); */
		
		/* l1= (LinearLayout) findViewById(R.id.tablelayoutlogin);
		l2= (LinearLayout) findViewById(R.id.tablelayoutregister);
		l1.setVisibility(View.VISIBLE);
		l2.setVisibility(View.INVISIBLE); */

		displayToast("Please enter your login credentials");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return false;
	}
	
//	public boolean onKeyDown(int keyCode, KeyEvent event){
//		return false;
//	}
	
	public void showregister(View view){

		//InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

		setContentView(R.layout.activity_home_screen);
		
		l2= (LinearLayout) findViewById(R.id.tablelayoutregister);
		l2.setVisibility(View.VISIBLE);
		
		l3= (LinearLayout) findViewById(R.id.tablelayoutlogin);
		l3.setVisibility(View.GONE);
		
		displayToast("Please enter your details to register as a new user");
	}	
	
	public void showLogin(View view){

		//InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

		setContentView(R.layout.activity_home_screen);
		
		l2= (LinearLayout) findViewById(R.id.tablelayoutregister);
		l2.setVisibility(View.GONE);
		
		l3= (LinearLayout) findViewById(R.id.tablelayoutlogin);
		l3.setVisibility(View.VISIBLE);
		
		displayToast("Please enter your credentials");
	}	

	public void logincheck(View view){
		//Boolean checkUser=false;
		
		try{
			
		emailidlogin11=(EditText) findViewById(R.id.emailidlogin);
		pinlogin11=(EditText) findViewById(R.id.passwordlogin);
		
		emailidlogin1=emailidlogin11.getText().toString();
		pinlogin1=pinlogin11.getText().toString();
		
		if (emailidlogin1.equals("") ){
			displayToast("Enter Username");
  		return;
    	}
		
    	if (pinlogin1.equals("") ){
    		displayToast("Enter PIN");
  		return;
    	}
		
		
		/*
		TextView t1=(TextView) findViewById(R.id.printemail);
		//TextView t2=(TextView) findViewById(R.id.printfirstname);
		//TextView t3=(TextView) findViewById(R.id.printlastname);
		TextView t4=(TextView) findViewById(R.id.printpin1);
		//TextView t5=(TextView) findViewById(R.id.printpin2);
		
		t1.setText(emailidlogin1);
		//t2.setText(firstnamereg1);
		//t3.setText(lastnamereg1);
		t4.setText(pinlogin1);
		//t5.setText(pin2reg1); */
		
		//displayToast("Under Construction...!!!");
		
		DatabaseHandler dblogin = new DatabaseHandler(this);		
		userID=dblogin.loginCheckDB(emailidlogin1,pinlogin1);
		
		if(userID==null){
			displayToast("Unsucessful... :( ");
		}else{
			//displayToast("Success.... :) ");
			displayToast("DB ROW = ");
			Intent i=new Intent(this,TimerActivity.class);
			i.putExtra("loggedInUser", userID);
			startActivity(i);
//			displayToast("Men at Work...!!!");
		}
		
		}catch(Exception e){
			String error=e.toString();
			Dialog d=new Dialog(this);
			d.setTitle("Unsuccessfull");
			d.setCanceledOnTouchOutside(true);
			TextView tv=new TextView(this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();			
		}/*finally{
			Dialog d=new Dialog(this);
			d.setTitle("userID");
			d.setCanceledOnTouchOutside(true);
			TextView tv=new TextView(this);
			tv.setText(" User Details: "+ userID);
			d.setContentView(tv);
			d.show();
		} */
		
	}

	
	
	
	public void registerNewUser(View view){

		//InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		//inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		
		Boolean notempty=true;
		Boolean pinmatch=false;
		//Boolean pinnull=false;
		
		emailidreg=(EditText) findViewById(R.id.emailidregister);
		firstnamereg=(EditText) findViewById(R.id.firstnameregister);
		lastnamereg=(EditText) findViewById(R.id.lastnameregister);
		pin1reg=(EditText) findViewById(R.id.loginpasswordregister);
		pin2reg=(EditText) findViewById(R.id.confirmloginpasswordregister);
		
		emailidreg1 = emailidreg.getText().toString();
		firstnamereg1=firstnamereg.getText().toString();
		lastnamereg1=lastnamereg.getText().toString();
		pin1reg1=pin1reg.getText().toString();
		pin2reg1=pin2reg.getText().toString();
		
/*
		TextView t1=(TextView) findViewById(R.id.printemail);
		TextView t2=(TextView) findViewById(R.id.printfirstname);
		TextView t3=(TextView) findViewById(R.id.printlastname);
		TextView t4=(TextView) findViewById(R.id.printpin1);
		TextView t5=(TextView) findViewById(R.id.printpin2);
		
		t1.setText(emailidreg1);
		t2.setText(firstnamereg1);
		t3.setText(lastnamereg1);
		t4.setText(pin1reg1);
		t5.setText(pin2reg1); 
		*/
		
				
		//Toast.makeText(getApplicationContext(),"Please enter your new login credentials",Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(),"Under Construction...!!!",Toast.LENGTH_SHORT).show();
		
		if(emailidreg1.equals("")){
			displayToast("Enter Company Email ID");
			notempty=false;
			return;
		}
		 
		if(firstnamereg1.equals("")){
			displayToast("Enter First Name");
			notempty=false;
			return;
		}
		
		if( lastnamereg1.equals("")){
			displayToast("Enter Last Name");
			notempty=false;
			return;
		}
		
		if(  pin1reg1.equals("") ){
			displayToast("Enter Login PIN");
			notempty=false;
			return;
		}
		
		if( pin2reg1.equals("")){
			displayToast("Confirm PIN");
			notempty=false;
			return;
		}
		
		if(pin1reg1.equals(pin2reg1)){
			pinmatch=true;
		}else 
			displayToast("PIN's Do not match");

		
		//Toast.makeText(getApplicationContext(),"notempty: "+notempty,Toast.LENGTH_LONG).show();
			if(notempty==true && pinmatch==true){
					DatabaseHandler dbregister = new DatabaseHandler(this);
			        // Register New User
					String dateDB=dbregister.addUser(emailidreg1,firstnamereg1, lastnamereg1,pin1reg1);
					displayToast("pinmatch: "+pinmatch+" "+"notempty: "+notempty);
					displayToast("Values entered...."+dateDB);
					setContentView(R.layout.activity_home_screen);
					l1= (LinearLayout) findViewById(R.id.tablelayoutlogin);
					l2= (LinearLayout) findViewById(R.id.tablelayoutregister);
					l1.setVisibility(View.VISIBLE);
					l2.setVisibility(View.INVISIBLE);
			}else{
				displayToast("pinmatch: "+pinmatch+" "+"notempty: "+notempty);
				displayToast("Error on page....");				
			} 
	}
	
	
	public void getdatabase(View view)
	{
		String loggedUserId="1";
		Intent i=new Intent(this,DatabaseData.class);
		i.putExtra("loggedUserId",loggedUserId);
		startActivity(i);
				
	}
	
	
	public void displayToast(String msg){
		Toast toast= Toast.makeText(getApplicationContext(), 
				msg, Toast.LENGTH_SHORT);  
				toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
	}
}
