package org.webdriver.pageobjects;

import java.text.NumberFormat;
import java.text.ParseException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.intuit.ctodev.autoweb.core.TestSession;

public class SearchResultPage {

  @FindBy(xpath = "//div[@id='fpcap']//span[@class='countClass']")
  private WebElement nbResults;

  @FindBy(id = "_nkw_id")
  private WebElement searchField;

  private final String searchString;

  public SearchResultPage(String searchString) {
    PageFactory.initElements(TestSession.webdriver(), this);
    this.searchString = searchString;

    String prefilled = searchField.getAttribute("value");
    Assert.assertEquals(prefilled, searchString);
  }

  public int getResultCount() throws ParseException {
    Number n = NumberFormat.getInstance().parse(nbResults.getText());
    return n.intValue();
  }

}
