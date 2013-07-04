package weather;

import java.io.Serializable;

public class WeatherParams implements Serializable {
	
	private String location;
	private String tempMinC;
	private String tempMaxC;
    private String windspeedKmph;
    private String date; 
    private String weatherDesc;
    private String weatherCode; 
    
    private String temp_C;
  
    public String getLocation()          { return location; }
    public String getTempMinC()          { return tempMinC; } 
    public String getTempMaxC()          { return tempMaxC; }
    public String getWindspeedKmph()     { return windspeedKmph; }
    public String getDate()              { return date; }
    public String getTemp_C()            { return temp_C; }
    public String getWeatherDesc()       { return weatherDesc; }
    public String getWeatherCode()       { return weatherCode; }
    
    
    public void setLocation(String location)          { this.location = location; }
    public void setTempMinC(String tempMinC)          { this.tempMinC = tempMinC; } 
    public void setTempMaxC(String tempMaxC)          { this.tempMaxC = tempMaxC; }
    public void setWindspeedKmph(String windspeedKmph){ this.windspeedKmph = windspeedKmph; }
    public void setDate(String date)                  { this.date = date; }
    public void setTemp_C(String temp_C)              { this.temp_C = temp_C;}
    public void setWeatherDesc(String weatherDesc)    { this.weatherDesc = weatherDesc;}
    public void setWeatherCode(String weatherCode)    { this.weatherCode = weatherCode;}
    
    
    public void set(String date,String location,
    		        String temp_C,String tempMinC,
    		        String tempMaxC,String windspeedKmph,
    		        String weatherDesc,String weatherCode) {
    	setDate(date);
    	setLocation(location);
    	setTemp_C(temp_C);
    	setTempMaxC(tempMaxC);
    	setTempMinC(tempMinC);
    	setWindspeedKmph(windspeedKmph);
    	setWeatherDesc(weatherDesc);
        setWeatherCode(weatherCode);
        
    }
    
}
