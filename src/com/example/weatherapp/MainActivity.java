package com.example.weatherapp;

import java.util.ArrayList;
import java.util.Hashtable;

import readers.JSONReader;
import readers.Reader;
import readers.XMLReader;
import weather.WeatherParams;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import data.CONST_DATA;
import data.ImageContainer;
import database.DBHelper;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnNavigationListener {

    public static final String  LOG_TAG = "myLog";
    public static final String  BAG_TAG = "bagLog";
    public static final String  EXTRA_MESSAGE = "CityCurrent";
    public static final CONST_DATA CONST = new CONST_DATA();
    
    public static ArrayAdapter<String> actionBarListAdapter ;
    public static ArrayList<String> Cities = new ArrayList<String>();
    public static String CityCurrent;
    public static ActionBar actionBar;
    public static DBHelper  dataBase;
    
    private ProgressBar mProgressBar;
   
	private ImageView mImageView;
	private ImageView mImageViewToday,
	                  mImageViewTomorrow,
	                  mImageViewAfterTomorrow; 
	
	private TextView  mTextViewCurrentWeatherHeader,
                      mTextViewCurrentWeatherBottomer;
	private TextView  mTextViewTodayHeader,
	                  mTextViewTomorrowHeader,
	                  mTextViewAfterTomorrowHeader,
	                  mTextViewTodayBottomer,
	                  mTextViewTomorrowBottomer,
	                  mTextViewAfterTomorrowBottomer;
	
	private String mFormatCurrent = CONST.FORMAT_XML;
	private Hashtable<String,Drawable > mCodeIconContainer;
    private ArrayList <WeatherParams> mWeatherObjects;
  
	
	@TargetApi(11)
	@Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initialize();
        workingNow();     
    }
   
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
       
        menu.findItem(R.id.menu_xml).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			public boolean onMenuItemClick(MenuItem item) {
				mFormatCurrent = CONST.FORMAT_XML;
				if (!item.isChecked()) {
			        item.setChecked(true);
			        menu.findItem(R.id.menu_json).setChecked(false);
				}
				Toast.makeText(MainActivity.this,"Selected : XML mode ",Toast.LENGTH_SHORT).show();
				return false;
			}
		});
        
        
	    menu.findItem(R.id.menu_json).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
				
			public boolean onMenuItemClick(MenuItem item) {
				mFormatCurrent = CONST.FORMAT_JSON;
				if (!item.isChecked()) {
				    item.setChecked(true);
				    menu.findItem(R.id.menu_xml).setChecked(false);
				}
			    Toast.makeText(MainActivity.this,"Selected : JSON mode",Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		 
        return true;
    }
    
    
    private void workingNow() {
    	if (!CityCurrent.equals("")) {
	    	mProgressBar.setVisibility(ProgressBar.VISIBLE);
	    	setDefaultScreenForProgressBar();
	    	interchangeAsync();
        }else {
        	Toast.makeText(MainActivity.this,"Please, add location",Toast.LENGTH_LONG).show();
        }
    }
   
  
    private void initialize() {
    	 mTextViewCurrentWeatherHeader = (TextView) findViewById(R.id.text_view);
    	 mTextViewCurrentWeatherBottomer = (TextView) findViewById(R.id.text_view_wind);
    	 mTextViewCurrentWeatherHeader.setMovementMethod(new ScrollingMovementMethod());
         mImageView = (ImageView) findViewById(R.id.image_view_weather_icon);
         mImageViewToday = (ImageView) findViewById(R.id.image_view_today);
         mImageViewTomorrow = (ImageView) findViewById(R.id.image_view_tomorrow);
         mImageViewAfterTomorrow = (ImageView) findViewById(R.id.image_view_after_tomorrow); 
         mTextViewTodayHeader = (TextView)  findViewById(R.id.text_view_header_today);
         mTextViewTomorrowHeader = (TextView)  findViewById(R.id.text_view_header_tomorrow);
         mTextViewAfterTomorrowHeader = (TextView) findViewById(R.id.text_view_header_after_tomorrow);
         mTextViewTodayBottomer = (TextView) findViewById(R.id.text_view_bottomer_today);
         mTextViewTomorrowBottomer = (TextView) findViewById(R.id.text_view_bottomer_tomorrow);
         mTextViewAfterTomorrowBottomer = (TextView) findViewById(R.id.text_view_bottomer_after_tomorrow);
         
         mProgressBar = (ProgressBar) findViewById(R.id.progressBar_loading);
         
         mCodeIconContainer = (new ImageContainer(MainActivity.this)).getContainer(); 
         mWeatherObjects = new ArrayList<WeatherParams>();
         
         dataBase = new DBHelper(getApplicationContext());
       
         initializeActionBar();
    }
    
	
    private void initializeActionBar() {
    	actionBar = MainActivity.this.getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        Cities = dataBase.getCitiesList();
        CityCurrent = Cities.get(0);
        actionBarListAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item,Cities);
        actionBar.setTitle("");
        actionBarListAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(actionBarListAdapter, this);    
    	
        actionBar.setCustomView(R.layout.action_bar); 
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
       
        findViewById(R.id.button_actoinbar_database).setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent (MainActivity.this,HistoryActivity.class);
				intent.putExtra(EXTRA_MESSAGE, CityCurrent);
				startActivity(intent);	
			}
		});
        
        findViewById(R.id.button_actoinbar_update).setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			workingNow();	
			}
		});
        
        findViewById(R.id.button_actoinbar_add).setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			   Intent intent = new Intent(MainActivity.this,SearchCityActivity.class);
			   startActivity(intent);
			}
		});
    }
    
   
    private void interchangeAsync()  {
    	 GettingDataTask task = new GettingDataTask();
         task.execute();
    }
    

    class GettingDataTask extends AsyncTask<Void, Void, Void> {
    	@Override
    	protected Void doInBackground(Void...value) {
    		Reader reader = null;
    		if (mFormatCurrent.equals(CONST.FORMAT_XML)) {
            		   reader = new XMLReader(MainActivity.this);
            	   }
            	   else if (mFormatCurrent.equals(CONST.FORMAT_JSON)) {
            		   reader = new JSONReader(MainActivity.this);
            	   }
            	  
    		mWeatherObjects = reader.getWeatherParams(CityCurrent, 3);
    	    return null;
    	};
    
    	@Override
    	protected void onPostExecute(Void value) {
    		super.onPostExecute(value) ;
    		mProgressBar.setVisibility(ProgressBar.INVISIBLE);
    		setAllWidgets();	
       	}
    }
    
  
    private void setAllWidgets () {
    	
    	  //----------------------	                     
    	  //  mWeatherObjects=  [0-current][1-today][2-tomorrow][3-afterTomorrow]
    	  //-----------------------
    	  setCurrentWeatherWidgets      (mWeatherObjects.get(CONST.CURRENT));
    	  setTodayWeatherWidgets        (mWeatherObjects.get(CONST.TODAY));
    	  setTomorrowWeatherWidgets     (mWeatherObjects.get(CONST.TOMORROW));
    	  setAfterTomorrowWeatherWidgets(mWeatherObjects.get(CONST.AFTER_TOMORROW));
		  
    	  // clear for refusing 
    	  dataBase.addWeather(mWeatherObjects.get(CONST.TODAY));
    	  mWeatherObjects.clear();
       }
    
    
    private void setCurrentWeatherWidgets(WeatherParams paramsCurrent) {    
    	mTextViewCurrentWeatherHeader.setText(
    			 paramsCurrent.getTemp_C() +CONST.DEGREE+ "C\n"+
    			 paramsCurrent.getWeatherDesc());
    	mTextViewCurrentWeatherBottomer.setText(" : "+paramsCurrent.getWindspeedKmph()+" Km/h\n    "+
    			Math.round(((float)(Float.valueOf(paramsCurrent.getWindspeedKmph())/CONST.CONVERSION_TO_MS))) +" m/s");     
    	mImageView. setImageDrawable(mCodeIconContainer.get(paramsCurrent.getWeatherCode()));
		
    }
    
   
    private void setTodayWeatherWidgets(WeatherParams paramsToday) {
    	mImageViewToday.        setImageDrawable(mCodeIconContainer.get(paramsToday.getWeatherCode()));
    	mTextViewTodayHeader.   setText("       Today\n"+CONST.formatDate(paramsToday.getDate()));
        mTextViewTodayBottomer. setText(" "+paramsToday.getTempMinC()+CONST.DEGREE+"C.."+paramsToday.getTempMaxC()+CONST.DEGREE+"C");
    
    }
    
    
    private void setTomorrowWeatherWidgets(WeatherParams paramsTomorrow) {
    	mImageViewTomorrow.        setImageDrawable(mCodeIconContainer.get(paramsTomorrow.getWeatherCode()));
    	mTextViewTomorrowHeader.   setText("   Tomorrow\n"+CONST.formatDate(paramsTomorrow.getDate()));
        mTextViewTomorrowBottomer. setText(" "+paramsTomorrow.getTempMinC()+CONST.DEGREE+"C.."+paramsTomorrow.getTempMaxC()+CONST.DEGREE+"C");
    
    }
    
    
    private void setAfterTomorrowWeatherWidgets(WeatherParams paramsAfterTomorrow) {
    	mImageViewAfterTomorrow.        setImageDrawable(mCodeIconContainer.get(paramsAfterTomorrow.getWeatherCode()));
    	mTextViewAfterTomorrowHeader.   setText("       Next\n"+CONST.formatDate(paramsAfterTomorrow.getDate()));
        mTextViewAfterTomorrowBottomer. setText(" "+paramsAfterTomorrow.getTempMinC()+CONST.DEGREE+"C.."+paramsAfterTomorrow.getTempMaxC()+CONST.DEGREE+"C");
    
    }
    
   
    private void setDefaultScreenForProgressBar() {
    	mTextViewCurrentWeatherHeader.setText("");
    	mTextViewCurrentWeatherBottomer.setText("");
    	mImageView.setImageBitmap(null);
    	mImageViewToday.setImageBitmap(null);
    	mImageViewTomorrow.setImageBitmap(null);
    	mImageViewAfterTomorrow.setImageBitmap(null);
    	mTextViewTodayHeader.setText("");
    	mTextViewTomorrowHeader.setText("");
    	mTextViewAfterTomorrowHeader.setText("");
    	mTextViewTodayBottomer.setText("");
    	mTextViewTomorrowBottomer.setText("");
    	mTextViewAfterTomorrowBottomer.setText("");
    }
    
	
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		CityCurrent = dataBase.getCitiesList().get(actionBar.getSelectedNavigationIndex());
		workingNow();
		return false;
	}
	
}