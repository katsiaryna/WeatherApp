package com.example.weatherapp;

public class Request {

	//private final String REQUEST_KEY = "ca96b738a5132347132103";
    //private final String SERVER_URL = "http://free.worldweatheronline.com/feed/weather.ashx?";		
	 private final String REQUEST_KEY = "nwahxraj9esyxkvsheunmfw6";
     private final String SERVER_URL = "http://api.worldweatheronline.com/free/v1/weather.ashx?";		
   
	
    private String cityAndCountry;
    private String format;
    private final int numOfDays = 3;

    
    public Request(String cityAndCountry,String format){
    	this.cityAndCountry = cityAndCountry;
    	this.format = format;

    }
    
    public String create(){
    	return
    			(SERVER_URL+"q="+cityAndCountry+"&format="+format+
    			 "&num_of_days="+String.valueOf(numOfDays)+"&key="+REQUEST_KEY);
    }
}
