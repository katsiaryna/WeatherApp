package readers;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import org.w3c.dom.Node;

import parsers.IParser;
import parsers.JSONParser;
import parsers.XMLParser;

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
	}

	@Override
	protected IParser getParser(InputStream stream) {
		return new JSONParser(stream);
	}

	@Override
	protected String makeUrl(String city, int days) {
		return("http://api.worldweatheronline.com/free/v1/weather.ashx?q="+city+"&format=json"+
   			 "&num_of_days="+String.valueOf(days)+"&key=nwahxraj9esyxkvsheunmfw6");
	}
	
}
