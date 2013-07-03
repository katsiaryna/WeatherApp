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
import android.content.Context;
import android.util.Log;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.WeatherParams;


public abstract class Reader {
    private Context context;
	
	public Reader(Context context) {
		this.context = context;
	}
	
	protected abstract IParser getParser(InputStream stream);
	
	protected abstract String makeUrl(String city, int days);
	
	protected abstract ArrayList<WeatherParams> makeWeatherParams(ArrayList<Hashtable<String,String>> hashtables);
	
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
	
//	public IWeatherInfo getWeatherInfo(String city) {
	//	return getWeatherInfo(city, 2);
	//}
	
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
	
	/*private void writeInDataBase(IWeatherInfo info) {
		Weather weather = new Weather(info.getDate(), 
									  info.getLocation(), 
									  info.getMaxTemperature(1), 
									  info.getMinTemperature(1),
									  info.getWindSpeed(1));
		DBHelper dataBase = new DBHelper(context);
		dataBase.addWeather(weather);
		dataBase.close();
	}*/
}