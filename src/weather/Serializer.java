package weather;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



import android.util.Log;
import android.widget.TextView;


public class Serializer {

	 public static final String LOG_TAG = "myLog";
		 
	 public byte[] serializeObject(WeatherParams weatherObject)throws IOException {
	    	
	    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    	Log.d(LOG_TAG,"serialize");
		  
	    	ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		    
		    objectOutputStream.writeObject(weatherObject);
		    objectOutputStream.flush();
		    byteArrayOutputStream.flush();
		    objectOutputStream.close();
		    byteArrayOutputStream.close();
		    return byteArrayOutputStream.toByteArray();   
	    }
	 
	 public WeatherParams deserializeObject(byte[] inputByteArray)throws IOException, ClassNotFoundException {
	    	
	    	if (inputByteArray != null) {
	    	  ByteArrayInputStream stream = new ByteArrayInputStream(inputByteArray);
	    	  Log.d(LOG_TAG,"deserialize");
	    	  ObjectInputStream objectInputStream = new ObjectInputStream(stream);
	    	   
	    	 return (WeatherParams) objectInputStream.readObject();
	    	}
	    	return null;
	    }

}
