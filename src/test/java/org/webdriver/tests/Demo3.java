package org.webdriver.tests;
import java.text.ParseException;

import org.webdriver.pageobjects.HomePage;
import org.webdriver.pageobjects.SearchResultPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ctodev.autoweb.core.TestSession;
import com.intuit.ctodev.autoweb.core.WebTest;




public class Demo3 {

  
  @Test
  @WebTest
  public void testSearch() throws ParseException{
    TestSession.webdriver().get("http://www.ebay.co.uk/");
    
    HomePage homePage = new HomePage();
    SearchResultPage result = homePage.search("ipod");
    
   int totalIpod = result.getResultCount();
   
   Assert.assertTrue(totalIpod>1000,"there should be more than "+totalIpod+" on the site.");
    
  }
}
