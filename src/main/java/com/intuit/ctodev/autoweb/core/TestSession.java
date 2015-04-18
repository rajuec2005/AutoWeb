package com.intuit.ctodev.autoweb.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

/**
 * TestNG guarantees that each test method will run in its own thread, so we can use threadLocal to
 * store the objects that are in the test scope. Typically the webdriver/selenium driver instance.
 * For most use cases, 1 driver per test method is enough.
 * 
 * @author Rajendra
 * 
 */
public class TestSession {

  public final static String WEBDRIVER = "webdriver";
  public final static String ERROR = "error";
  private static String HUB_URL = "http://localhost:4444/wd/hub";

  private static ThreadLocal<Map<String, Object>> sessions = new InheritableThreadLocal<Map<String, Object>>();
  static DesiredCapabilities capability ;

  /**
   * 
   * @return the webdriver object bound to the current test.
   */
  public static WebDriver webdriver() {
     
      WebDriver driver = (WebDriver) get(WEBDRIVER);
      if (driver == null) {
        printListenerHelp();
      }
      return driver;
    
  }


  
  /**
   * start the browser for the current test.
   * 
   * @param protocol Selenium legacy or webdriver
   * @param browserName firefox,chrome ... for webdriver, *firefox, *googlechrome for selenium
   *        legacy.
   * @param version only relevant for webdriver.
   * @param p only relevant for webdriver.
   */
  public static void start() {
    
   // put("browserName",TestConfigReader.getBrowserType());
    URL url = getHubUrl();
   
    capability = setDesiredCapabilites() ;
      try {
        WebDriver driver = new RemoteWebDriver(url, capability);
        put(WEBDRIVER, driver);
      
      } catch (WebDriverException e) {
        put(ERROR, e);
      }

    }

  


  /**
   * terminates the current test, closing the browser.
   */
  public static void stop() {
  
      try {
        webdriver().quit();
      } catch (Exception e) {
        e.printStackTrace();
      }
    
  }

  /**
   * local for debugging or remote.
   * 
   * @return the url the grid listen on.
   */
  private static URL getHubUrl() {
    try {
      return new URL(HUB_URL);
    } catch (MalformedURLException e) {
      throw new InvalidParameterException(HUB_URL + " isn't a valid url for the hub..");
    }
  }

  /**
   * set the URL that will be used during the test. Default to a local grid.
   * 
   * @param url
   */
  public static void setHubUrl(String url) {
    HUB_URL = url;
  }




  private static Map<String, Object> getSession() {
    Map<String, Object> res = sessions.get();
    if (res == null) {
      res = new HashMap<String, Object>();
      sessions.set(res);
    }
    return res;
  }

  private static Object get(String key) {
    return getSession().get(key);
  }

  public static void put(String key, Object value) {
    getSession().put(key, value);
  }

  private static void putAll(Map<String, ? extends Object> params) {
    getSession().putAll(params);
  }

  public static void setContext(Map<String, String> params) {
    putAll(params);
  }

  private static void printListenerHelp() {
    if (get(ERROR) != null) {
      throw (WebDriverException) get(ERROR);
    } else {
      String msg =
          "There is no selenium/webdriver currently bound to this thread.\n"
              + "Have you attached the SeleniumCapability listener and annotated your"
              + " test with @WebTest ?";
      System.err.println(msg);
      throw new RuntimeException(
          "Selenium Listener Exception. Most likely resulting from a config error.");
    }



  }
  
  
  /**
   * Method to set Desired capabilities
   */
  public static DesiredCapabilities setDesiredCapabilites(){
	  
	  capability = new DesiredCapabilities();
	  String platform = TestConfigReader.getPlatform() ;
	  capability.setBrowserName(TestConfigReader.getBrowserType());
	  
      if (platform.equalsIgnoreCase("windows")){
          capability.setPlatform(Platform.WINDOWS);
      } 
      else if (platform.equalsIgnoreCase("xp")){
          capability.setPlatform(Platform.XP);
      }
      else if (platform.equalsIgnoreCase("mac")){
          capability.setPlatform(Platform.MAC);
      } 
      else if(platform.equalsIgnoreCase("linux")){
    	  capability.setPlatform(Platform.LINUX);
      }
      else
      {
          Assert.fail("Error:- Platform is not recognized. Valid Platforms are 'xp', 'windows7', 'windows8' or 'mac'");
      }
      capability.setCapability("acceptSslCerts", true);
      
      return capability ;
     
  }
  
  
  
  
}
