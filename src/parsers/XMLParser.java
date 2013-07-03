package parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.weatherapp.CONST_DATA;


public class XMLParser implements IParser {
	
	private XmlPullParser mXmlPullParser;
	
	public XMLParser(InputStream stream) {
		mXmlPullParser  = prepareXmlPullParser(getStringForParsing(stream));
	}
	
    public String getStringForParsing(InputStream stream) {
    	String stringForParsing = "";
    	try {
    	 BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
	     String line;
		 while ((line = reader.readLine()) != null) {		     
			     stringForParsing += line;
	     }
		 stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return stringForParsing;
	 }
    
	private XmlPullParser prepareXmlPullParser(String stringForParsing)  {
		//сомневаюсь в правильности ,надо чтобы вегда не возврщался нуль
		XmlPullParser xmlPullParser = null;
		try{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			
		    xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(stringForParsing));
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
			return xmlPullParser;
	}
    
	
	public ArrayList<Hashtable<String, String>> getData()
			throws XmlPullParserException, IOException {
		 Dictionary<String, String>  tag_value  = new Hashtable<String, String>();
		 CONST_DATA  CONST  = new CONST_DATA(); 
	     ArrayList<Hashtable<String, String>> hashtables = new ArrayList<Hashtable<String,String>>();
		
			   int event =   mXmlPullParser.next();
			
			   while (event != XmlPullParser.END_DOCUMENT) {
					   
				   if(event != XmlPullParser.START_TAG) {
						   event = mXmlPullParser.next();
						   continue;
				   }
				        
				   String tag = mXmlPullParser.getName();
				   if ( tag.compareTo("weather") == 0) {
						hashtables.add((Hashtable<String, String>) tag_value);
						tag_value = new Hashtable<String, String>();
					}
					   String value = "";
					   event = mXmlPullParser.next();
					   if (event == XmlPullParser.TEXT){
						   value = mXmlPullParser.getText();
						   event = mXmlPullParser.next();
					   }
					   tag_value.put(tag, value);
			   }
			   hashtables.add((Hashtable<String, String>) tag_value);
		   return hashtables;
	}
}
