package org.webdriver.tests;
import static com.intuit.ctodev.autoweb.core.TestSession.webdriver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.grid.common.SeleniumProtocol;
import org.testng.annotations.Test;
import org.webdriver.pageobjects.HomePage;

import com.intuit.ctodev.autoweb.core.WebTest;
import com.intuit.ctodev.autoweb.utils.LogUtil;


public class Demo1 {

	


  @Test
  @WebTest(protocol = SeleniumProtocol.WebDriver)
  public void test() {
    // WebDriver protocol allow the webdriver API
	LogUtil.log("**********MyFirst Test Case*************");
	LogUtil.log("**********step1*************");
	LogUtil.log("**********step2*************");
    webdriver().get("http://www.ebay.co.uk");
    
  }


 

  // works with all the normal testng features, dependencies, invoc count etc.
 // @Test(invocationCount = 5, threadPoolSize = 5)
  @Test
  @WebTest(protocol = SeleniumProtocol.WebDriver)
  public void testParallel() throws InterruptedException {
	  LogUtil.log("**********My Second Test Case*************");
	  LogUtil.log("**********s1*************");
	  LogUtil.log("**********s2*************");
    webdriver().get("http://www.google.com");
    Thread.sleep(10000);
    HomePage homepage = new HomePage();
    homepage.searchString("cricket");
    
  }
  
  
  

}
