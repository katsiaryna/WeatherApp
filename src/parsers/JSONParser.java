package parsers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.example.weatherapp.MainActivity;

import android.annotation.TargetApi;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

@TargetApi(11)
public class JSONParser implements IParser {
    
	private JsonReader mReader;
	private JsonToken mToken;
	private Hashtable<String, String> mTagValue = new Hashtable<String, String>();
	ArrayList<Hashtable<String, String>> hashtables = 
    		new ArrayList<Hashtable<String,String>>();
	//
	//two arrays contain "value"-tag,mTagChange - tmp storage values of tags "value"
	private List<String> mTagChange = new ArrayList<String>();
	private InputStream stream;
	
	
	public JSONParser(InputStream stream) {
		this.stream = stream;
		
	}
	
	public ArrayList<Hashtable<String, String>> getData()
			throws XmlPullParserException, IOException {
	   
		mReader = new JsonReader(new InputStreamReader(this.stream));
		mToken = mReader.peek();
		Log.d(MainActivity.LOG_TAG,"TOKEN" +mToken.toString());
		while (mToken != JsonToken.END_DOCUMENT) {
			switch (mToken) {
			case BEGIN_OBJECT:
				mReader.beginObject();
				Log.d(MainActivity.LOG_TAG,"begin");
				break;
			case END_OBJECT:
				mReader.endObject();
				Log.d("bagLog","end");
				break;
			case BEGIN_ARRAY:
				mReader.beginArray();
				break;
			case END_ARRAY:
				mReader.endArray();
				break;
			case NAME:
				Log.d(MainActivity.LOG_TAG,"name");
			    createTagValue();
				continue;
			default:
				mReader.skipValue();
			}
			mToken = mReader.peek();
		}
		hashtables.add(mTagValue);
		stream.close();
		return hashtables;
	}
   
	private void createTagValue() throws IOException {
		   String tag;
		   tag = mReader.nextName();
			
		    if ( tag.compareTo("date") == 0) {
				mTagValue.put(MainActivity.CONST.TAG.WEATHER_DESC,mTagChange.get(0));
				mTagValue.put(MainActivity.CONST.TAG.WEATHER_ICON_URL,mTagChange.get(1));
		    	
				hashtables.add(mTagValue);
				
				mTagValue = new Hashtable<String, String>();
				mTagChange = new ArrayList<String>();
			}
		    
		    String value = "";
			mToken = mReader.peek() ; 
			if (mToken == JsonToken.STRING) {
				value = mReader.nextString();
				mToken = mReader.peek();
			} 
			if (tag.compareTo("value") == 0) {
		        mTagChange.add(value);
		    }
			mTagValue.put(tag, value);
	 }
   
}
