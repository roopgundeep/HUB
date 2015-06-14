package com.example.drawernavigationtabs.database;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class SqlDbHelper extends SQLiteOpenHelper {

	 public static final String DATABASE_TABLE = "PHONE_CONTACTS";
	 public static final String DATABASE_INTERESTS = "INTERESTS";
	 public static final String DATABASE_FLAG = "FLAG";
	 public static final String DATABASE_REGISTER = "REGISTER";
	 public static final String DATABASE_USER = "USERS";
	 public static final String DATABASE_FBFRIENDS = "FBFRIENDS";
	 public static final String DATABASE_EVENTFLAG = "EVENTFLAG";

	 //news
	 private static final String SCRIPT_CREATE_INTERESTDATABASE="CREATE TABLE IF" +
	 		" NOT EXISTS INTERESTS (id integer primary key autoincrement, name);";
	 
	 private static final String SCRIPT_CREATE_EVENTFLAGDATABASE="CREATE TABLE IF" +
		 		" NOT EXISTS EVENTFLAG (id integer primary key autoincrement, value);";
	 
	 private static final String SCRIPT_CREATE_FACEBOOKFRIENDSDATABASE="CREATE TABLE IF" +
		 		" NOT EXISTS FBFRIENDS (id integer primary key autoincrement, facebookId, name);";
	 
	 private static final String SCRIPT_CREATE_FLAGDATABASE="CREATE TABLE IF" +
		 		" NOT EXISTS FLAG (id integer primary key autoincrement, news, deals, events);";
	 
	 private static final String SCRIPT_CREATE_REGISTERDATABASE="CREATE TABLE IF" +
		 		" NOT EXISTS REGISTER (id integer primary key autoincrement, facebook, interests, foursquare);";
	 //deals
	 private static final String SCRIPT_CREATE_DEALSDATABASE="CREATE TABLE IF" +
		 		" NOT EXISTS DEALS (id integer primary key autoincrement, title, url, price, saving, image, timestamp);";
	 //events
	 private static final String SCRIPT_CREATE_EVENTSDATABASE="CREATE TABLE IF NOT EXISTS EVENTS" +
	 		" (id integer primary key autoincrement,title , url , start_date, end_date, category,status,created,organizer_name, " +
	 		"organizer_description, organizer_long_description, venue_name, venue_address," +
	 		"venue_address_2, venue_postal_code, venue_city, " +
	 		"venue_region, venue_country, timestamp );";
	 //user
	 private static final String SCRIPT_CREATE_USERSDATABASE="CREATE TABLE IF NOT EXISTS USERS (id integer primary key autoincrement," +
	 		"name , emailId , facebookId, first_name, last_name );";
	 
	
	 public SqlDbHelper(Context context, String name, CursorFactory factory,
	   int version) {
	  super(context, name, factory, version);
	  // TODO Auto-generated constructor stub
	 
	 }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		//db.execSQL("DROP TABLE IF EXISTS " + DATABASE_INTERESTS);
		System.out.println("Roop DB query Interest DB to be created " + SCRIPT_CREATE_INTERESTDATABASE);
		db.execSQL(SCRIPT_CREATE_INTERESTDATABASE);
		System.out.println("Roop DB query Interest DB created " + SCRIPT_CREATE_INTERESTDATABASE);
		db.execSQL(SCRIPT_CREATE_USERSDATABASE);
		db.execSQL(SCRIPT_CREATE_FLAGDATABASE);
		db.execSQL(SCRIPT_CREATE_REGISTERDATABASE);
		db.execSQL(SCRIPT_CREATE_FACEBOOKFRIENDSDATABASE);
		db.execSQL(SCRIPT_CREATE_EVENTFLAGDATABASE);
		System.out.print("Database Created");
		// Log.i("DAtabase Created","Database Created");
	}
 
	 @Override
	 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	  // TODO Auto-generated method stub
	  
	  db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
	  System.out.println("Roop DB query Interest DB on updgrade " + DATABASE_INTERESTS);
	  db.execSQL("DROP TABLE IF EXISTS " + DATABASE_INTERESTS);
	  System.out.println("Roop DB query Interest DB updgraded " + DATABASE_INTERESTS);
	  db.execSQL("DROP TABLE IF EXISTS " + DATABASE_USER);
	  db.execSQL("DROP TABLE IF EXISTS " + DATABASE_FLAG);
	  db.execSQL("DROP TABLE IF EXISTS " + DATABASE_REGISTER);
	  db.execSQL("DROP TABLE IF EXISTS " + DATABASE_FBFRIENDS);
	  db.execSQL("DROP TABLE IF EXISTS " + DATABASE_EVENTFLAG);
	  onCreate(db);
	 }

}