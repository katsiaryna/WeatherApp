package readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import parsers.IParser;
import weather.WeatherParams;
import android.content.Context;
import android.util.Log;

import com.example.weatherapp.MainActivity;
dgffgf


public abstract class Reader {
    private Context context;
	
	public Reader(Context context) {
		this.context = context;
	}
	
	protected abstract IParser getParser(InputStream stream);
	
	protected abstract String makeUrl(String city, int days);
	
    protected ArrayList<WeatherParams> makeWeatherParams(
			ArrayList<Hashtable<String, String>> hashtables) {
		
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
	public ArrayList<WeatherParams> getWeatherParams(String city, int days) {
		InputStream stream = readDataFromUrl(makeUrl(city, days));
		IParser parser = getParser(stream);
		try {
			return makeWeatherParams(parser.getData());
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	private InputStream readDataFromUrl(String url) {
		InputStream stream = null;
		try {
        	HttpClient httpClient = new DefaultHttpClient();
        	HttpPost httpPost = new HttpPost(url);
        	HttpResponse response = httpClient.execute(httpPost);
        	HttpEntity entity = response.getEntity();
        	stream = entity.getContent();
    	} catch (Exception e) {
        	Log.d(MainActivity.LOG_TAG, "Error in http connection: " + e.toString());
        }
		return stream;
	}
}	
