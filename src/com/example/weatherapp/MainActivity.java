package com.example.weatherapp;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import readers.JSONReader;
import readers.Reader;
import readers.XMLReader;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnNavigationListener {

    public static final String  LOG_TAG = "myLog";
    public static final String  BAG_TAG = "bagLog";
    public static final String  EXTRA_MESSAGE = "mCityCurrent";
 	
    public static ActionBar mActionBar;
    public static DBHelper  mDateBase;
    
    private       ProgressBar   mProgressBar;
    
    public static final CONST_DATA     CONST   = new CONST_DATA();
	
	private         TextView    mTextViewCurrentWeatherHeader,
	                            mTextViewCurrentWeatherBottomer;
	private         ImageView   mImageView;
	private         ImageView   mImageViewToday,
	                            mImageViewTomorrow,
	                            mImageViewAfterTomorrow; 
	private         TextView    mTextViewHeaderToday,
	                            mTextViewHeaderTomorrow,
	                            mTextViewHeaderAfterTomorrow,
	                            mTextViewBottomerToday,
	                            mTextViewBottomerTomorrow,
	                            mTextViewBottomerAfterTomorrow;
	
	  public    static   String      CityCurrent = CONST.CITY_MINSK;
	private         String      mFormatCurrent = CONST.FORMAT_XML;
	
	
	public static ArrayAdapter<String> mActionBarListAdapter ;
	private Dictionary
	         <String,Drawable > mCodeIconContainer;
   
    private ArrayList
    <Hashtable<String, String>> mHashtables;
    private ArrayList
                <WeatherParams> mWeatherObjects;
    public static ArrayList<String> Cities = new ArrayList<String>();
	
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
         mImageView              = (ImageView) findViewById(R.id.image_view_weather_icon);
         mImageViewToday         = (ImageView) findViewById(R.id.image_view_today);
         mImageViewTomorrow      = (ImageView) findViewById(R.id.image_view_tomorrow);
         mImageViewAfterTomorrow = (ImageView) findViewById(R.id.image_view_after_tomorrow); 
         mTextViewHeaderToday    = (TextView)  findViewById(R.id.text_view_header_today);
         mTextViewHeaderTomorrow = (TextView)  findViewById(R.id.text_view_header_tomorrow);
         mTextViewHeaderAfterTomorrow = (TextView) findViewById(R.id.text_view_header_after_tomorrow);
         mTextViewBottomerToday    = (TextView) findViewById(R.id.text_view_bottomer_today);
         mTextViewBottomerTomorrow = (TextView) findViewById(R.id.text_view_bottomer_tomorrow);
         mTextViewBottomerAfterTomorrow = (TextView) findViewById(R.id.text_view_bottomer_after_tomorrow);
         
         mProgressBar = (ProgressBar) findViewById(R.id.progressBar_loading);
         
         mCodeIconContainer      = (new ImageContainer(MainActivity.this)).getContainer(); 
         mHashtables             =  new ArrayList<Hashtable<String,String>>();
         mWeatherObjects         =  new ArrayList<WeatherParams>();
         
         mDateBase = new DBHelper(getApplicationContext());
        
         
         initializeActionBar();
    }
	
    private void initializeActionBar() {
    	 
    	mActionBar = MainActivity.this.getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        Cities = mDateBase.getCitiesList();
        mActionBarListAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item,Cities);
        mActionBar.setTitle("");
        mActionBarListAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        mActionBar.setListNavigationCallbacks(mActionBarListAdapter, this);    
    	
        mActionBar.setCustomView(R.layout.action_bar); 
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
       
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
         task.execute (( new Request( CityCurrent,
         		                 mFormatCurrent)).create() );
    }

    class GettingDataTask extends AsyncTask<String, Void, Void> {
    	@Override
    	protected Void doInBackground(String... urls) {
    	
    	
    		String urlAddress = urls[0];
    	/*	try {
            	   HttpClient httpClient = new DefaultHttpClient();
            	   HttpPost   httpPost   = new HttpPost(urlAddress);
            	   HttpResponse response;
				   response = httpClient.execute(httpPost);
				   HttpEntity   entity   = response.getEntity();
            	   InputStream  stream   = entity.getContent();
            	   Reader reader = null;
            	   */
    		Reader reader = null;
    		if (mFormatCurrent.equals(CONST.FORMAT_XML)) {
            		   reader = new XMLReader(MainActivity.this);
            	   }
            	   else if (mFormatCurrent.equals(CONST.FORMAT_JSON)) {
            		   reader = new JSONReader(MainActivity.this);
            	   }
            	  /* mHashtables = reader.getData();
            	  
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}*/
    		mWeatherObjects = reader.getWeatherParams(CityCurrent, 3);
    		//mHashtables.add(reader.getWeatherParams("Minsk", 3));
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
    	
    	/*WeatherParams paramsCurrent = new WeatherParams();
    	paramsCurrent.set(mHashtables.get(0).get(CONST.TAG.DATE),
    			          mCityCurrent,
    			          mHashtables.get(0).get(CONST.TAG.TEMP_CUR),
		                  "-" ,"-",
		                  mHashtables.get(0).get(CONST.TAG.WIND_SPEED),
		                  mHashtables.get(0).get(CONST.TAG.WEATHER_DESC),
		                  mHashtables.get(0).get(CONST.TAG.WEATHER_CODE));
		mWeatherObjects.add(paramsCurrent);
    	
		for (int i = 1 ; i< mHashtables.size(); i++ ) {
    		WeatherParams params = new WeatherParams();
        	params.set(       mHashtables.get(i).get(CONST.TAG.DATE),
        			          mCityCurrent,"-",
        			          mHashtables.get(i).get(CONST.TAG.TEMP_MIN),
        			          mHashtables.get(i).get(CONST.TAG.TEMP_MAX),
    		                  mHashtables.get(i).get(CONST.TAG.WIND_SPEED),
    		                  mHashtables.get(i).get(CONST.TAG.WEATHER_DESC),
    		                  mHashtables.get(i).get(CONST.TAG.WEATHER_CODE));
    		mWeatherObjects.add(params);
  	    }*/
    	//	                      0       1       2           3
    	//  mWeatherObjects=  [current][today][tomorrow][afterTomorrow]
    	//
    	  setCurrentWeatherWidgets      (mWeatherObjects.get(0));
    	  setTodayWeatherWidgets        (mWeatherObjects.get(1));
    	  setTomorrowWeatherWidgets     (mWeatherObjects.get(2));
    	  setAfterTomorrowWeatherWidgets(mWeatherObjects.get(3));
		  // clear for refusing 
    	  
    	  mDateBase.addWeather(mWeatherObjects.get(1));
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
    	mTextViewHeaderToday.   setText("       Today\n"+formatDate(paramsToday.getDate()));
        mTextViewBottomerToday. setText(" "+paramsToday.getTempMinC()+CONST.DEGREE+"C.."+paramsToday.getTempMaxC()+CONST.DEGREE+"C");
    
    }
    
    private void setTomorrowWeatherWidgets(WeatherParams paramsTomorrow) {
    	mImageViewTomorrow.        setImageDrawable(mCodeIconContainer.get(paramsTomorrow.getWeatherCode()));
    	mTextViewHeaderTomorrow.   setText("   Tomorrow\n"+formatDate(paramsTomorrow.getDate()));
        mTextViewBottomerTomorrow. setText(" "+paramsTomorrow.getTempMinC()+CONST.DEGREE+"C.."+paramsTomorrow.getTempMaxC()+CONST.DEGREE+"C");
    
    }
    
    private void setAfterTomorrowWeatherWidgets(WeatherParams paramsAfterTomorrow) {
    	mImageViewAfterTomorrow.        setImageDrawable(mCodeIconContainer.get(paramsAfterTomorrow.getWeatherCode()));
    	mTextViewHeaderAfterTomorrow.   setText("       Next\n"+formatDate(paramsAfterTomorrow.getDate()));
        mTextViewBottomerAfterTomorrow. setText(" "+paramsAfterTomorrow.getTempMinC()+CONST.DEGREE+"C.."+paramsAfterTomorrow.getTempMaxC()+CONST.DEGREE+"C");
    
    }
   
    private String formatDate(String string) {
    	
    	Log.d(BAG_TAG,string+"     " +string.subSequence(5, 7).toString());
    	String day = string.substring(8, 10);
    	Log.d(BAG_TAG,day.subSequence(0, 1).toString());
    	if (day.subSequence(0, 1).equals("0")){
    		day = day.subSequence(1, 2).toString();
    	}
    	String month = CONST.MONTHS.get(string.subSequence(5, 7).toString());
    	return 
    		"       "+ day + " "+ month;
    	
    }
    
    private void setDefaultScreenForProgressBar() {
    	mTextViewCurrentWeatherHeader.setText("");
    	mTextViewCurrentWeatherBottomer.setText("");
    	mImageView.setImageBitmap(null);
    	mImageViewToday.setImageBitmap(null);
    	mImageViewTomorrow.setImageBitmap(null);
    	mImageViewAfterTomorrow.setImageBitmap(null);
    	mTextViewHeaderToday.setText("");
    	mTextViewHeaderTomorrow.setText("");
    	mTextViewHeaderAfterTomorrow.setText("");
    	mTextViewBottomerToday.setText("");
    	mTextViewBottomerTomorrow.setText("");
    	mTextViewBottomerAfterTomorrow.setText("");
    }
	
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		CityCurrent = mDateBase.getCitiesList().get(mActionBar.getSelectedNavigationIndex());
		workingNow();
		return false;
	}
	
}