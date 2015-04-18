package com.intuit.ctodev.autoweb.listeners;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.grid.common.GridRole;
import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.utils.GridHubConfiguration;
import org.openqa.grid.internal.utils.SelfRegisteringRemote;
import org.openqa.grid.web.Hub;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.intuit.ctodev.autoweb.core.TestConfigReader;
import com.intuit.ctodev.autoweb.core.TestSession;

public class WebDriverSetUpListener implements ISuiteListener {

  private static Hub hub;
  private static SelfRegisteringRemote remote;
  private Map<String, String> testNGParameters = new HashMap<String, String>();

  /**
   * starts a local grid with 1 node locally.
   */
  @Override
  public void onStart(ISuite suite) {
	  
	
	  setUpWebDriverProperties(suite);
	  
	  
    try {
      if (isLocalGrid()) {
        setGridURL();
        setupChromeDriver();

        GridHubConfiguration config = new GridHubConfiguration();
        config.setHost("localhost");
        config.setPort(4444);
        
        
        hub = new Hub(config);
        hub.start();
        

        RegistrationRequest req = new RegistrationRequest();
        req.setRole(GridRole.NODE);
        req.loadFromJSON("localGridNode.json");
        

        remote = new SelfRegisteringRemote(req);
        remote.startRemoteServer();
        remote.startRegistrationProcess();
        Thread.sleep(1000);
      }else{
        setGridURL();
      }

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * release resources.
   */
  @Override
  public void onFinish(ISuite suite) {
    try {
      if (isLocalGrid()) {
        remote.stopRemoteServer();
        hub.stop();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * if a "url" parameter is specified in the testng.xml file, it will use that. and expect a grid
   * to be running there. Otherwise it will start and use a local instance.
   * 
   * @param suite
   */
  private boolean isLocalGrid() {
	 
    String url = TestConfigReader.getISLocalGrid();
    if (url.equalsIgnoreCase("true")) {
      return true;
    } else {
      return false;
    }
  }

  private void setGridURL() {
    String url = TestConfigReader.getHUB();
    if (url != null) {
      TestSession.setHubUrl(url);
    }
  }

  /**
   * add the chrome driver server to the path if it was specified in the xml file.
   * 
   * @param suite
   */
  private void setupChromeDriver() {
	 String platform =  TestConfigReader.getPlatform();
    
	if (platform.equalsIgnoreCase("xp") || platform.equalsIgnoreCase("windows")){
    
        System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");
    } 
	else if (platform.equalsIgnoreCase("linux")){
    
        System.setProperty("webdriver.chrome.driver", "./lib/chromedriver_linux");
    } 
	else if (platform.equalsIgnoreCase("mac")){
    
        System.setProperty("webdriver.chrome.driver", "./lib/chromedriver_mac");
    }
  }
  
  
  /**
   * Method to set all Webdriver properties on start of the suite
   */
  public void setUpWebDriverProperties(ISuite suite){
	testNGParameters = suite.getXmlSuite().getParameters() ;
	TestConfigReader.setTestNGParameters(testNGParameters);
	TestConfigReader.setWebDriverProperties();
  }
  
  
  /**
   * Method to delete log files created by log4j before suite starts
   */
  public void deleteLogFile(){
	 
	  


  }


}
