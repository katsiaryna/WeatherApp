package readers;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import org.w3c.dom.Node;

import parsers.IParser;

import com.example.weatherapp.CONST_DATA;
import com.example.weatherapp.WeatherParams;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

@TargetApi(11)
public class JSONReader extends Reader {

	public JSONReader(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IParser getParser(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String makeUrl(String city, int days) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ArrayList<WeatherParams> makeWeatherParams(
			ArrayList<Hashtable<String, String>> hashtables) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 public static final String  LOG_TAG = "myLog";
	 private InputStream inputStream;
	 private Dictionary<String, String> tag_value = new Hashtable<String, String>();
	 private final CONST_DATA CONST  = new CONST_DATA(); 
     private ArrayList<Hashtable<String, String>> hashtables = new ArrayList<Hashtable<String,String>>();
 	 private JsonReader reader ;
 	 private JsonToken token;
 	 private String string;
 	 private  ArrayList<String> tagChange = new ArrayList<String>();
 	
 	 
	public JSONReader(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public ArrayList<Hashtable<String, String>> getData() {
		parsing();
		return hashtables;
	}

	@Override
	public void parsing() {
		try {
			getWeatherData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
    
	public void getWeatherData() throws Exception {
    	//Log.d(LOG_TAG,"weather");
    	reader = new JsonReader(new InputStreamReader(inputStream));
    	int depth = 0;
		token = reader.peek();
		Log.d(LOG_TAG,"TOKEN" +token.toString());
		while (token != JsonToken.END_DOCUMENT) {
			switch (token) {
			case BEGIN_OBJECT:
				reader.beginObject();
				Log.d(LOG_TAG,"begin");
				depth++;
				break;
			case END_OBJECT:
				reader.endObject();
				Log.d("bagLog","end");
				depth--;
				break;
			case BEGIN_ARRAY:
				reader.beginArray();
				break;
			case END_ARRAY:
				reader.endArray();
				break;
			case NAME:
				Log.d(LOG_TAG,"name");
				createNode(depth);
				continue;
			default:
				reader.skipValue();
			}
			token = reader.peek();
		}
		hashtables.add((Hashtable<String, String>) tag_value);
		inputStream.close();
	}
	 private void createNode(int depth) throws Exception {
			
		    String tag = reader.nextName();
		    if ( tag.compareTo("date") == 0) {
				tag_value.put(CONST.TAG.WEATHER_DESC,tagChange.get(0));
				tag_value.put(CONST.TAG.WEATHER_ICON_URL,tagChange.get(1));
		    	hashtables.add
						  ((Hashtable<String, String>) tag_value);
				tag_value = new Hashtable<String, String>();
				tagChange = new ArrayList<String>();
			   }
		    
		    String value = "";
			token = reader.peek() ; 
			if (token == JsonToken.STRING) {
				value = reader.nextString();
				token = reader.peek();
			} 
			if (tag.compareTo("value") == 0) {
		        tagChange.add(value);
		    }
			tag_value.put(tag, value);
			
		}
*/

}
