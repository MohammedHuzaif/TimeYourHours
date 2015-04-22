package com.huzaif.timeyourhours;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TIMEYOURHOURS";
    private static final String TABLE_USER = "USERREGISTER";
    private static final String TABLE_TIMER="TIMEREGISTER";
 
    private static final String KEY_USERID = "id";
    private static final String KEY_EMAILID = "emailid";
    private static final String KEY_FIRSTNAME="firstname";
    private static final String KEY_LASTNAME="lastname";
    private static final String KEY_PIN="pin";
    private static final String KEY_DATECREATED = "datecreated";
    private static final String KEY_DATEEDITED = "dateedited";
    
    private static final String KEY_TIMERID="timerid";
    private static final String KEY_TIMERDATE = "timerdate";
    private static final String KEY_TASK="task";
    private static final String KEY_STARTTIME="starttime";
    private static final String KEY_STOPTIME="stoptime";
    private static final String KEY_TIMEDURATION="timeduration";
    
   public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
       String USER_REGISTER_TABLE = 
    		   	"CREATE TABLE " + TABLE_USER + "("
                + KEY_USERID + " INTEGER," 
                + KEY_EMAILID + " VARCHAR,"
                + KEY_FIRSTNAME + " VARCHAR," 
                + KEY_LASTNAME + " VARCHAR," 
                + KEY_PIN + " VARCHAR," 
                + KEY_DATECREATED + " VARCHAR," 
                + KEY_DATEEDITED + " VARCHAR" +")";
       
        db.execSQL(USER_REGISTER_TABLE); 
        
        String TIMER_TABLE = "CREATE TABLE " + TABLE_TIMER + "("
                + KEY_TIMERID + " INTEGER," 
                + KEY_USERID + " INTEGER," 
                + KEY_TIMERDATE + " DATE,"
                + KEY_TASK + " VARCHAR," 
                + KEY_STARTTIME + " DATE," 
                + KEY_STOPTIME + " DATE,"
                + KEY_TIMEDURATION + " TIME" +")";
        
        db.execSQL(TIMER_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMER);
        // Create tables again
        onCreate(db); 
    }
    
    String addUser(String email, String firstname, String lastname, String pin) {
    	int rowCount=0;
    	int rowcount=0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor allrows  = db.rawQuery("SELECT * FROM "+  TABLE_USER, null);
 	   	rowCount=allrows.getCount();
 	   	rowcount=rowCount+1;
 	   	
 	   	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String dateDB = sdf.format(new Date());
		       
        db.execSQL("INSERT INTO "+TABLE_USER+" VALUES " +
        "("+rowcount+"," +"'"+email+"','"+firstname+"','"+lastname+"','"+pin+"','"+dateDB+"','"+dateDB+"')");
        
        db.close(); // Closing database connection
        return dateDB;
    }
 
    
    
    String loginCheckDB(String emailidlogin2, String pinlogin2) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	String dbdata = null;

        Cursor cursor  = db.rawQuery("SELECT * FROM "+  TABLE_USER, null);
        		
		int iUserEMail = cursor.getColumnIndex(KEY_EMAILID);
		int iPin = cursor.getColumnIndex(KEY_PIN);
		
		for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
			if(emailidlogin2.equals(cursor.getString(iUserEMail)) && pinlogin2.equals(cursor.getString(iPin))){
				  String ID = cursor.getString(cursor.getColumnIndex(KEY_USERID));
			      //String EMAIL= cursor.getString(cursor.getColumnIndex(KEY_EMAILID));
			      //String FIRSTNAME= cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME));
			      //String LASTNAME= cursor.getString(cursor.getColumnIndex(KEY_LASTNAME));
			      //String PIN=cursor.getString(cursor.getColumnIndex(KEY_PIN));
			      //String blankspace=" ";
			      
			      //dbdata=ID.concat(blankspace.concat(EMAIL.concat(blankspace.concat(FIRSTNAME.concat(blankspace.concat(LASTNAME.concat(blankspace).concat(PIN)))))));   
				  	dbdata=ID;
			        db.close();
			        
			       return dbdata;
			}
			}
		
        db.close();
        
       return dbdata;
    }
    
    public String [] getUserProfile(String userID){
    	SQLiteDatabase db = this.getReadableDatabase();
    	String []user=new String[]{"userid","email ID","firstname","lastname"};
        Cursor cursor  = db.rawQuery("SELECT * FROM "+  TABLE_USER, null);
        		
		int iUserID = cursor.getColumnIndex(KEY_USERID);
		
		for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
			if(userID.equals(cursor.getString(iUserID))){
				  user[0] = (String) cursor.getString(cursor.getColumnIndex(KEY_USERID));
			      user[1]= cursor.getString(cursor.getColumnIndex(KEY_EMAILID));
			      user[2]= cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME));
			      user[3]= cursor.getString(cursor.getColumnIndex(KEY_LASTNAME));
			      
			      db.close();
			      return user;
			}
		}
    	
    	
    	return user;
    }
    
   public String[] getDatabase()
   {	int i=0;  
	   SQLiteDatabase db = this.getReadableDatabase();	   

	   Cursor allrows  = db.rawQuery("SELECT * FROM "+  TABLE_USER, null);
	   String [] dbdata = new String[allrows.getCount()];
	   String ID="empty";
	   try{
	   
       if(allrows.moveToFirst()){
           do{     
        	   ID = allrows.getString(0);
               String EMAIL= allrows.getString(allrows.getColumnIndex(KEY_EMAILID));
               String FIRSTNAME= allrows.getString(allrows.getColumnIndex(KEY_FIRSTNAME));
               String LASTNAME= allrows.getString(allrows.getColumnIndex(KEY_LASTNAME));
               String PIN=allrows.getString(allrows.getColumnIndex(KEY_PIN));
               String blankspace=" ";
               dbdata[i]=ID.concat(blankspace.concat(EMAIL.concat(blankspace.concat(FIRSTNAME.concat(blankspace.concat(LASTNAME.concat(blankspace).concat(PIN)))))));   
               i++;
           }
           while(allrows.moveToNext());
       	}
       db.close();
	   }
   
   	catch(Exception e){
   		System.out.println("Error encountered."+e);
       
   }
	return dbdata;
    
   }
   
   public int getRowCount(){
	   int rowcount=0;
	   SQLiteDatabase db = this.getReadableDatabase();
       Cursor allrows  = db.rawQuery("SELECT * FROM "+  TABLE_USER, null);
	   rowcount=allrows.getCount();
	   
	   return rowcount;
   }
   
   public String saveTime(String loggedUserId,String StartTime,String StopTime,String timeduration){
	   	int rowCount=0;
   		int rowcount=0;
   		
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor allrows  = db.rawQuery("SELECT * FROM "+TABLE_TIMER, null);
	   	
       rowCount=allrows.getCount();
	   rowcount=rowCount+1;

	   	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String dateDB = sdf.format(new Date());

	   
	/*   Date dt = new Date();
       long date=(dt.getTime());
       String dateDB= String.valueOf(date);
      */
	   
       db.execSQL("INSERT INTO "+TABLE_TIMER+" VALUES " +
       "("+rowcount+"," +"'"+loggedUserId+"','"+dateDB+"','Daily Hours','"+StartTime+"','"+StopTime+"','"+timeduration+"')");
       
       
       
       allrows  = db.rawQuery("SELECT * FROM "+  TABLE_TIMER, null);
	   String dbdataTimer = null;
	   String ID="empty"; 
	   //int iTimerrID = allrows.getColumnIndex(KEY_TIMERID);
	   
	   allrows.moveToLast();
	   
	   //for(allrows.moveToFirst(); !allrows.isAfterLast();allrows.moveToNext()){
	//		if(loggedUserId.equals(allrows.getString(iTimerrID))){
	   	ID = allrows.getString(0);
	   	String USERID= allrows.getString(allrows.getColumnIndex(KEY_USERID));
  	    String DATE= allrows.getString(allrows.getColumnIndex(KEY_TIMERDATE));
  	    String TASK= allrows.getString(allrows.getColumnIndex(KEY_TASK));
  	    String START_TIME= allrows.getString(allrows.getColumnIndex(KEY_STARTTIME));
  	    String STOP_TIME=allrows.getString(allrows.getColumnIndex(KEY_STOPTIME));
  	    String DURATION=allrows.getString(allrows.getColumnIndex(KEY_TIMEDURATION));       	   
       String blankspace=" ";
       dbdataTimer=ID.concat(blankspace.concat(USERID.concat(blankspace
       		.concat(DATE.concat(blankspace.concat(TASK.concat(blankspace.concat(START_TIME.concat(blankspace
       		.concat(STOP_TIME.concat(blankspace.concat(DURATION))))))))))));  
		//	}
	  // }
       
       db.close(); // Closing database connection
	   
	   return dbdataTimer;	   
   }
   
   public Cursor getTimerTable(String UserID){
	   
	   int i=0;  
	   SQLiteDatabase db = this.getReadableDatabase();	   

	   Cursor allrows;
	   
	   allrows = db.rawQuery("SELECT * FROM "+  TABLE_TIMER + " WHERE "+ KEY_USERID +" = "+ UserID, null);
	   
	   //allrows = db.rawQuery("SELECT * FROM "+  TABLE_TIMER , null);
	   
	   String [] dbdataTimer = new String[allrows.getCount()];
	  
	   /*
	   String ID="empty";   
	   
	   try{
	   
       if(allrows.moveToFirst()){
           do{     
        	   	ID = allrows.getString(0);
        	   	String USERID= allrows.getString(allrows.getColumnIndex(KEY_USERID));
	       	    String DATE= allrows.getString(allrows.getColumnIndex(KEY_TIMERDATE));
	       	    String TASK= allrows.getString(allrows.getColumnIndex(KEY_TASK));
	       	    String START_TIME= allrows.getString(allrows.getColumnIndex(KEY_STARTTIME));
	       	    String STOP_TIME=allrows.getString(allrows.getColumnIndex(KEY_STOPTIME));
	       	    String DURATION=allrows.getString(allrows.getColumnIndex(KEY_TIMEDURATION));       	   
	            String blankspace=" , ";
	            dbdataTimer[i]=ID.concat(blankspace.concat(USERID.concat(blankspace
	            		.concat(DATE.concat(blankspace.concat(TASK.concat(blankspace.concat(START_TIME.concat(blankspace
	            		.concat(STOP_TIME.concat(blankspace.concat(DURATION))))))))))));   
	            i++;
           }
           while(allrows.moveToNext());
       	}
       
   }
   
	catch(Exception e){
		System.out.println("Error encountered."+e);
   
		} */
	   db.close();
	   return allrows;
   }
   
   public int getRowCountTimer(){
	   int rowcount=0;
	   SQLiteDatabase db = this.getReadableDatabase();
       Cursor allrows  = db.rawQuery("SELECT * FROM "+  TABLE_TIMER, null);
	   rowcount=allrows.getCount();
	   
	   return rowcount;
   }

   public String[] getUserTime(String UID)
   {	int i=0;  
	   SQLiteDatabase db = this.getReadableDatabase();	   

	   Cursor allrows;
	   
	   allrows  = db.rawQuery("SELECT * FROM "+  TABLE_TIMER, null);
	   
	   String [] dbdata = new String[allrows.getCount()];
	   String ID="empty";
	   try{
	   
       if(allrows.moveToFirst()){
           do{     
        	   ID = allrows.getString(0);
               String EMAIL= allrows.getString(allrows.getColumnIndex(KEY_EMAILID));
               String FIRSTNAME= allrows.getString(allrows.getColumnIndex(KEY_FIRSTNAME));
               String LASTNAME= allrows.getString(allrows.getColumnIndex(KEY_LASTNAME));
               String PIN=allrows.getString(allrows.getColumnIndex(KEY_PIN));
               String blankspace=" , ";
               dbdata[i]=ID.concat(blankspace.concat(EMAIL.concat(blankspace.concat(FIRSTNAME.concat(blankspace.concat(LASTNAME.concat(blankspace).concat(PIN)))))));   
               i++;
           }
           while(allrows.moveToNext());
       	}
       db.close();
	   }
   
   	catch(Exception e){
   		System.out.println("Error encountered."+e);
       
   }
	return dbdata;
    
   }
   
  
	   
}