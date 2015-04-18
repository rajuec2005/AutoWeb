package org.webdriver.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class TestClass {
	
	
	@Test
	public void sampleTest(){
		
		WebDriver driver = new FirefoxDriver();
		driver.get("http://en.wikipedia.org/wiki/New_Zealand");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.MINUTES);
		driver.findElement(By.id("searchInput")).sendKeys("India");
		driver.findElement(By.id("searchButton")).click();
		driver.findElement(By.xpath("//a[@title='NewDelhi']")).click();
		
		
		
	}

}
