package com.example.weatherapp;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

@TargetApi(11)
public class HistoryActivity extends Activity {
 
	
	private ActionBar mActionBar;
	private String    mCityCurrent;
	private ListView  mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CONST_DATA CONST = new CONST_DATA();
		setContentView(R.layout.activity_history);	
        	
		mActionBar = getActionBar();
		
		mCityCurrent = (String) getIntent().getExtras().get(MainActivity.EXTRA_MESSAGE);
		mActionBar.setTitle("              " +mCityCurrent);
		ArrayList<WeatherParams> weatherList  = MainActivity.mDateBase.getWeatherHistory(mCityCurrent);
		String string ="";
		ArrayList<String> listString = new ArrayList<String>();
	        for (int i = 0; i< weatherList.size(); i++ ) {
	        	string = weatherList.get(i).getDate()+ ":\n" 
	            +"           temperature: "+ weatherList.get(i).getTempMinC()
	            +CONST.DEGREE+"C.."+weatherList.get(i).getTempMaxC()+CONST.DEGREE+"C\n           description  : "
	            +weatherList.get(i).getWeatherDesc()+"\n           wind speed  : "+
	            weatherList.get(i).getWindspeedKmph()+" Kmph"
	            ;
	            listString.add(string);
	        }
	        mListView = (ListView) findViewById(R.id.list_view_history);
	        mListView.setAdapter(new ArrayAdapter<String>
	        (HistoryActivity.this,R.layout.item_list, listString));
	}
	 	   
}
