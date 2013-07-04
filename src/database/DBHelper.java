package database;

import java.io.IOException;
import java.util.ArrayList;

import weather.Serializer;
import weather.WeatherParams;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper  {
	private static final int VERSION = 1;
	private static final String DATABASE_NAME = "WeatherDB.db";
	
	// Table weather
	private static final String TABLE_WEATHER = "WEATHER";
	private static final String DATE = "date";
	private static final String LOCATION = "location";
	private static final String OBJECT = "weather";
	
	// Table city
	private static final String TABLE_CITY = "CITIES";
	private static final String CITY = "city";
	
	private Serializer mSerializer;
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
		mSerializer = new Serializer();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sqlCommand = "CREATE TABLE " + TABLE_WEATHER +
					 		" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
					 		DATE + " TEXT, " +
					 		LOCATION + " TEXT, " +
					 		OBJECT + " BLOB);";
		db.execSQL(sqlCommand);
	    sqlCommand = "CREATE TABLE " + TABLE_CITY +
		 			 " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		 			 CITY + " TEXT);";
		db.execSQL(sqlCommand);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
        onCreate(db);
	}	
	
	public ArrayList<WeatherParams> getWeatherHistory(String location) {
		ArrayList<WeatherParams> weatherList = new ArrayList<WeatherParams>();
		
		String selection = LOCATION + " = ?";
		String[] selectionArgs = new String[] { location };
		Cursor cursor = query(selection, selectionArgs);
		if (cursor.moveToFirst()) {
			int objectIndex = cursor.getColumnIndex(OBJECT);
			do {
				byte[] byteArray = cursor.getBlob(objectIndex);
				try {
					weatherList.add((WeatherParams) mSerializer.deserializeObject(byteArray));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} while (cursor.moveToNext());
		} else {
			weatherList = null;
		}
		cursor.close();
		return weatherList;
	}
	
	public void addWeather(WeatherParams weather) {
		if (isExist(weather)) {
			update(weather);
		} else {
			insert(weather);
		}
	}
	
	private boolean isExist(WeatherParams weather) {
		String selection = LOCATION + " = ? AND " + DATE + " = ?"; 
		String[] selectionString  = new String[] { weather.getLocation(), weather.getDate() };
		Cursor cursor = query(selection, selectionString);
		boolean exist = cursor.moveToFirst();
		cursor.close();
		return exist;
	}
	
	private void insert(WeatherParams weather) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = getContentValues(weather);
		db.insert(TABLE_WEATHER, null, values);
		db.close();
	}
	
	private void update(WeatherParams weather) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = getContentValues(weather);
		String whereClause = LOCATION + " = ? AND " + DATE + " = ?";
		String[] whereArgs = new String[] { weather.getLocation(), weather.getDate() };
		db.update(TABLE_WEATHER, values, whereClause, whereArgs);
		db.close();
	}
	
	// Database not closed
	private Cursor query(String selection, String[] selectionArgs) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_WEATHER, null, selection, selectionArgs, null, null, null);
		return cursor;
	}
	
	private ContentValues getContentValues(WeatherParams weather) {
		ContentValues values = new ContentValues();
		values.put(DATE, weather.getDate());
		values.put(LOCATION, weather.getLocation());
		try {
			values.put(OBJECT, mSerializer.serializeObject(weather));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return values;
	}
	
	public void addCity(String city) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_CITY, null, CITY + " = ?", new String[] { city }, null, null, null);
		if (!cursor.moveToFirst()) {
			ContentValues values = new ContentValues();
			values.put(CITY, city);
			db.insert(TABLE_CITY, null, values);
		}
		cursor.close();
		db.close();
	}
	
	public ArrayList<String> getCitiesList() {
		ArrayList<String> citiesList = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_CITY, null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			int index = cursor.getColumnIndex(CITY);
			do {
				citiesList.add(cursor.getString(index));
			} while (cursor.moveToNext());
		} else {
			citiesList = null;
		}
		cursor.close();
		db.close();
		return citiesList;
	}
	
	// this method for testing project
	public void clearCity() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CITY, null, null);
		db.close();
	}
	 public void clear() {
		 SQLiteDatabase db = this.getWritableDatabase();
		 db.delete(TABLE_WEATHER, null, null);
		 db.close();
	 }
	
	// this method for testing project
	public void readAll() {
		Cursor cursor = query(null, null);
		if (cursor.moveToFirst()) {
	        // determine column's numbers
			int idIndex = cursor.getColumnIndex("_id");
			int locationIndex = cursor.getColumnIndex(LOCATION);
			int dateIndex = cursor.getColumnIndex(DATE);
			int objectIndex = cursor.getColumnIndex(OBJECT);
			
	        do {
	          // get values and write them in LOG
	          Log.d(Serializer.LOG_TAG,
	              "ID = " + cursor.getInt(idIndex) + 
	              ", location = " + cursor.getString(locationIndex) + 
	              ", date = " + cursor.getString(dateIndex) + 
	              ", object = " + cursor.getBlob(objectIndex));
	        } while (cursor.moveToNext());
	      } else
	        Log.d(Serializer.LOG_TAG, "0 rows");
	      cursor.close();
	}


}
