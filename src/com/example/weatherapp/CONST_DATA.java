package com.example.weatherapp;

import java.util.Dictionary;
import java.util.Hashtable;

public class CONST_DATA {

	
	
	public final String CITY_MINSK = "Minsk";
    public final String CITY_BREST = "Brest";
    public final String CITY_MOSCOW = "Moscow";
    public final String CITY_KIEV ="Kiev"; 
    
    public final String COUNTRY_BELARUS = "Belarus";
    public final String COUNTRY_UKRAINE = "Ukraine";
    public final String COUNTRY_RUSSIA =  "Russia";
    
    public final String FORMAT_XML = "xml";
    public final String FORMAT_JSON = "json";
    
    public final int DAY_TODAY = 1;
    public final int DAY_TOMORROW = 2;
    public final int DAY_TWO= 3;
    public final int DAY_THREE = 4;
    
    public final char DEGREE = 0x00B0;
    
    public final float CONVERSION_TO_MS = (float) 3.6;
    
    public Dictionary<String,String> MONTHS = new Hashtable<String, String>();
    
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
    
}
