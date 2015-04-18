package com.intuit.ctodev.autoweb.listeners;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.Augmenter;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.ConstructorOrMethod;

import com.intuit.ctodev.autoweb.core.TestConfigReader;
import com.intuit.ctodev.autoweb.core.TestSession;
import com.intuit.ctodev.autoweb.core.WebTest;


/**
 * TestNG listener that automatically starts/closes browsers around methods.
 * 
 * @author freynaud
 * 
 */
public class WebDriverSessionListener implements IInvokedMethodListener2 {



	/**
	 * hooks before the method start, and make sure a browser has been started using the info
	 * specified in the testng.xml file.
	 */
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
		WebTest web = getWebTestAnnotation(method);
		if (web != null) {

			TestSession.setContext(TestConfigReader.getWebDriverProperties());
			TestSession.start();
		}

	}

	/**
	 * closes the browser after the method.
	 */
	public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
		WebTest web = getWebTestAnnotation(method);
		if (web != null) {
			boolean always = true;
			if (!testResult.isSuccess() || always){
				try {
					TakesScreenshot ss = (TakesScreenshot) new Augmenter().augment(TestSession.webdriver());
					File f = new File("./screenshot/"+	method.getTestMethod().getTestClass().getName()+"/"+method.getTestMethod().getMethodName()+".jpg");
				
					File screenshot = ss.getScreenshotAs(OutputType.FILE);
					//File screenshot = ((TakesScreenshot)TestSession.webdriver()).getScreenshotAs(OutputType.FILE);
					//FileUtils.copyFile(screenshot, new File("./screenshot/screenshot.jpg"));		
					FileUtils.copyFile(screenshot, f);	
				//	screenhot.renameTo(f);
					Reporter.setCurrentTestResult(testResult);
					Reporter.log("<img src='" + f.getAbsolutePath() + "' />");
				}catch (Exception e) {
					Reporter.log("tried to screenshot. Failed."+e.getMessage());
				}
			}
			TestSession.stop();
		}
	}



	private WebTest getWebTestAnnotation(IInvokedMethod method) {
		if (!method.isTestMethod()) {
			return null;
		}
		ConstructorOrMethod com = method.getTestMethod().getConstructorOrMethod();
		if (com.getMethod() == null) {
			return null;
		}
		Method m = com.getMethod();
		WebTest annotation = m.getAnnotation(WebTest.class);
		return annotation;
	}


	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
	}

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
	}

}
