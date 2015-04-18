package org.webdriver.tests;
import java.text.ParseException;

import org.webdriver.pageobjects.HomePage;
import org.webdriver.pageobjects.SearchResultPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ctodev.autoweb.core.TestSession;
import com.intuit.ctodev.autoweb.core.WebTest;
import static com.intuit.ctodev.autoweb.core.TestSession.webdriver;



public class Demo3 {

  
  @Test
  @WebTest
  public void testSearch() throws ParseException{
    webdriver().get("http://www.facebook.com");
    
    
  }
}
