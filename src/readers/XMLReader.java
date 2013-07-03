package readers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import org.w3c.dom.Node;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.WeatherParams;

import parsers.IParser;
import parsers.XMLParser;
import android.content.Context;

public class XMLReader extends Reader {
	
		public XMLReader(Context context) {
			super(context);
		}
		
		@Override
		protected IParser getParser(InputStream stream) {
			return new XMLParser(stream);
		}

		@Override
		protected String makeUrl(String city, int days) {
			return "http://free.worldweatheronline.com/feed/weather.ashx?q=" + city +
				   "&format=xml&num_of_days=" + String.valueOf(days) + 
				   "&key=4a2011359a105021132003";
		}


		@Override
		protected ArrayList<WeatherParams> makeWeatherParams(
				ArrayList<Hashtable<String, String>> hashtables) {
			// TODO Auto-generated method stub
			ArrayList<WeatherParams> weatherObjects = new ArrayList<WeatherParams>();
			WeatherParams paramsCurrent = new WeatherParams();
	    	paramsCurrent.set(hashtables.get(0).get(MainActivity.CONST.TAG.DATE),
	    			          MainActivity.CityCurrent,
	    			          hashtables.get(0).get(MainActivity.CONST.TAG.TEMP_CUR),
			                  "-" ,"-",
			                  hashtables.get(0).get(MainActivity.CONST.TAG.WIND_SPEED),
			                  hashtables.get(0).get(MainActivity.CONST.TAG.WEATHER_DESC),
			                  hashtables.get(0).get(MainActivity.CONST.TAG.WEATHER_CODE));
			weatherObjects.add(paramsCurrent);
	    	
			for (int i = 1 ; i< hashtables.size(); i++ ) {
	    		WeatherParams params = new WeatherParams();
	        	params.set(       hashtables.get(i).get(MainActivity.CONST.TAG.DATE),
	        			MainActivity.CityCurrent,"-",
	        			          hashtables.get(i).get(MainActivity.CONST.TAG.TEMP_MIN),
	        			          hashtables.get(i).get(MainActivity.CONST.TAG.TEMP_MAX),
	    		                  hashtables.get(i).get(MainActivity.CONST.TAG.WIND_SPEED),
	    		                  hashtables.get(i).get(MainActivity.CONST.TAG.WEATHER_DESC),
	    		                  hashtables.get(i).get(MainActivity.CONST.TAG.WEATHER_CODE));
	    		weatherObjects.add(params);
	  	    }
			return weatherObjects;
		}
	

}
