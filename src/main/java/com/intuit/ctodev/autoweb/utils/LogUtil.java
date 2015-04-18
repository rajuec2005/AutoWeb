package com.intuit.ctodev.autoweb.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.testng.Reporter;

public class LogUtil {


	public static Log logger = LogFactory.getLog(LogUtil.class);




	public static void log(String message, String lv) {
		if (lv.equalsIgnoreCase("debug")) {
			log(message, Level.DEBUG);
		} else if (lv.equalsIgnoreCase("error")) {
			log(message, Level.ERROR);
		} else {
			// default is info
			log(message, Level.INFO);
		}




	}


	public static void log(String message, Level lv) {

		if (lv == Level.INFO)
		{
			logger.info(message);
			Reporter.log(message + "<br/>", false);
		}
		if (lv == Level.DEBUG)
		{
			logger.debug(message);
			Reporter.log(message + "<br/>", false);
		}
		if (lv == Level.ERROR)
		{
			logger.error(message);
			Reporter.log(message + "<br/>", false);
		}


	}	

	public static void log(String message) {
		logger.info(message);
		Reporter.log(message + "<br/>", false);
	}

}
