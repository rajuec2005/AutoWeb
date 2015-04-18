package org.webdriver.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ctodev.autoweb.core.TestSession;


public class HomePage {

  @FindBy(name="q")
  private WebElement searchField;
  @FindBy(xpath="//div[@id='sblsbb']")
  private WebElement searchButton;
  
  
  public HomePage(){
    PageFactory.initElements(TestSession.webdriver(), this);
  }
  
  public SearchResultPage search(String keyword){
    searchField.sendKeys(keyword);
    searchButton.click();
    return new SearchResultPage(keyword);
  }
  
  
  public void searchString(String keyword){
	    searchField.sendKeys(keyword);
	    searchButton.click();
	   
	  }
}
