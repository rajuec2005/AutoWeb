package com.intuit.ctodev.autoweb.core;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



public class TestConfigReader {
	
	
	static String IS_LOCAL_GRID ;
	static String BROWSER_TYPE ;
	static String PLATFORM ;
	static String IS_EVENT_FIRING_WEB_DRIVER ;
	static String ENVIRONMENT ;
	static String USERNAME ;
	static String PASSWORD ;
	static String APP_URL ;
	static String TAKE_SCREENSHOT ;
	static String ENABLE_VIDEO_RECORDING ;
	static Properties testConfigproperties ;
	static Map<String,String> webDriverProperties = new HashMap<String,String>() ;
	//static Map<Object,Object> configProperties = new HashMap<Object,Object>() ;
	static Map<String,String> testNGParameters = new HashMap<String,String>() ;

	enum WebDriverParameters { IS_LOCAL_GRID, HUB ,BROWSER_TYPE, PLATFORM, IS_EVENT_FIRING_WEB_DRIVER, ENVIRONMENT, USERNAME, PASSWORD, APP_URL, TAKE_SCREENSHOT, ENABLE_VIDEO_RECORDING };

	static{

		try {
			String propFileName  =  "./src/test/resources/testConfig.properties" ;
			File file = new File(propFileName);
			FileInputStream fileInput;
			fileInput = new FileInputStream(file);
			testConfigproperties = new Properties();
			testConfigproperties.load(fileInput);
			fileInput.close();

			/*if (fileInput != null) {
				testConfigproperties.load(fileInput);
			} */
		}

		catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	
	
	

	/**
	 * Method to set webdriver properties
	 */
	public static void setWebDriverProperties(){
		for( WebDriverParameters param : WebDriverParameters.values()){
			setWebDriverProperty(param.toString());
	}
		
		printWebDriverProperties();
	}
	
	
	/**
	 * Method to set webDriverProperties map based on commandline params, testng params & config.properties file
	 * Preference 
	 * 1. CommandLine
	 * 2. TestNG parameters
	 * 3. testConfigproperties.properties
	 * @param key
	 */
	public static void setWebDriverProperty(String key) {
		
		if(System.getProperty(key) !=null){
			webDriverProperties.put(key, System.getProperty(key));
		}
		
		else if(testNGParameters.get(key) != null) {
			webDriverProperties.put(key, testNGParameters.get(key));
		}
		
		else if(testConfigproperties.getProperty(key)!=null){
			webDriverProperties.put(key,testConfigproperties.getProperty(key));
		}
		
		
}
	
	
	/**
	 * Method to set TestNGParameters to the map 
	 */
	public static void setTestNGParameters(Map<String,String> testNGParams){
		testNGParameters = testNGParams ;
		
	}
	
	
	/**
	 * Method to get the Web Driver Properties
	 */
	public static Map<String,String> getWebDriverProperties(){
		
		return webDriverProperties ;
	}
	
	
	/**
	 * Method to print All Webdriver properties
	 */
	
	public static void printWebDriverProperties(){
        for(Map.Entry<String, String> property : webDriverProperties.entrySet()){
            System.out.println(property.getKey() +" :: "+ property.getValue());
        }
	}
	
	
	/**Method to get IS_LOCAL_GRID value
	 * 
	 */
	public static String getISLocalGrid(){
		return webDriverProperties.get("IS_LOCAL_GRID");
	}
	
	
	/**Method to get HUB value
	 * 
	 */
	public static String getHUB(){
		return webDriverProperties.get("HUB");
	}
	
	/**Method to get BROWSER_TYPE value
	 * 
	 */
	public static String getBrowserType(){
		return webDriverProperties.get("BROWSER_TYPE");
	}
	
	/**Method to get PLATFORM value
	 * 
	 */
	public static String getPlatform(){
		return webDriverProperties.get("PLATFORM");
	}
	
	/**Method to get IS_EVENT_FIRING_WEB_DRIVER value
	 * 
	 */
	public static String getIsEventFiringWebDriver(){
		return webDriverProperties.get("IS_EVENT_FIRING_WEB_DRIVER");
	}
	
	/**Method to get ENVIRONMENT value
	 * 
	 */
	public static String getEnvironment(){
		return webDriverProperties.get("ENVIRONMENT");
	}
	
	/**Method to get USERNAME value
	 * 
	 */
	public static String getUserName(){
		return webDriverProperties.get("USERNAME");
	}
	
	/**Method to get PASSWORD value
	 * 
	 */
	public static String getPassword(){
		return webDriverProperties.get("PASSWORD");
	}
	
	/**Method to get APP_URL value
	 * 
	 */
	public static String getAppUrl(){
		return webDriverProperties.get("APP_URL");
	}
	
	/**Method to get TAKE_SCREENSHOT value
	 * 
	 */
	public static String getTakeScreenShot(){
		return webDriverProperties.get("TAKE_SCREENSHOT");
	}
	
	/**Method to get ENABLE_VIDEO_RECORDING value
	 * 
	 */
	public static String getEnableVideoRecording(){
		return webDriverProperties.get("ENABLE_VIDEO_RECORDING");
	}

}
