package data;

import java.util.Dictionary;
import java.util.Hashtable;

import android.util.Log;

public final class CONST_DATA {
    //------------------
    //format o response
	//----------------
    public final String FORMAT_XML = "xml";
    public final String FORMAT_JSON = "json";
    //----------------------------
    //quantity of days in makeUrl
    //-----------------
    public final int DAY_TODAY = 1;
    public final int DAY_TOMORROW = 2;
    public final int DAY_TWO= 3;
    public final int DAY_THREE = 4;
    //-------------------
    //titles of indexes in weatherObjects array
    //-------------------
    public final int CURRENT = 0;
    public final int TODAY = 1;
    public final int TOMORROW = 2;
    public final int AFTER_TOMORROW = 3;
    //--------------------------------
    //math const
    //---------------------------------
    public final char DEGREE = 0x00B0;
    public final float CONVERSION_TO_MS = (float) 3.6;
    
    public Dictionary<String,String> MONTHS = new Hashtable<String, String>();
    //---------------------
    //hard-hard-hard
    //-------------------
    public CONST_DATA() {
   
    	MONTHS.put("01", "Jan");
    	MONTHS.put("02", "Feb");
    	MONTHS.put("03", "Mar");
    	MONTHS.put("04", "Apr");
    	MONTHS.put("05", "May");
    	MONTHS.put("06", "Jun");
    	MONTHS.put("07", "Jul");
    	MONTHS.put("08", "Aug");
    	MONTHS.put("09", "Sep");
    	MONTHS.put("10", "Oct");
    	MONTHS.put("11", "Nov");
    	MONTHS.put("12", "Dec");
    }
    public TAG TAG = new TAG();
    //------------------------
    //necessary tags,which  response has
    //----------------------
    public class TAG{
    	
    	public final String TEMP_CUR = "temp_C";
    	public final String TEMP_MIN = "tempMinC";
        public final String TEMP_MAX = "tempMaxC";
        
        public final String DATE = "date";
       
        public final String WIND_SPEED = "windspeedKmph";
        
        public final String WEATHER_DESC = "weatherDesc";
        public final String WEATHER_DESC_CUR = "weatherDescCurrent";
        
        public final String WEATHER_CODE = "weatherCode";
        public final String WEATHER_CODE_CUR = "weatherCodeCurrent";
        public final String WEATHER_ICON_URL = "weatherIconUrl";
        
    }
    public String formatDate(String string) {
    	String day = string.substring(8, 10);
    	if (day.subSequence(0, 1).equals("0")){
    		day = day.subSequence(1, 2).toString();
    	}
    	String month = MONTHS.get(string.subSequence(5, 7).toString());
    	return 
    		("       "+ day + " "+ month);	
    }
    
}
