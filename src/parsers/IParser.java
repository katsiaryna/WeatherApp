package parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParserException;

public interface IParser {
	
   ArrayList<Hashtable<String,String>> getData() throws XmlPullParserException,IOException;

	//ArrayList<Hashtable<String, String>> getData();

}
