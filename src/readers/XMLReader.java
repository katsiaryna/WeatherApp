package readers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import org.w3c.dom.Node;

import com.example.weatherapp.MainActivity;


import parsers.IParser;
import parsers.XMLParser;
import weather.WeatherParams;
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
			
			return("http://api.worldweatheronline.com/free/v1/weather.ashx?q="+city+"&format=xml"+
	    			 "&num_of_days="+String.valueOf(days)+"&key=nwahxraj9esyxkvsheunmfw6");
		
		}

}
