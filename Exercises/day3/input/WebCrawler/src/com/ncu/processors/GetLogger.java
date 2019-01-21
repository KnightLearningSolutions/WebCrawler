/**
*  The Getlogger class use to initialization logger 
*  for different - different classes .
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2019-1-5 
*/

package com.ncu.processors;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class GetLogger
{
	// loggerValue method initialization logger and retrun one logger object
	public Logger loggerValue(String className)
	{
		String log4jConfigFile = System.getProperty("user.dir")+ File.separator + "configs/logger/logger.properties";
		Logger logger = Logger.getLogger(className+".class");
		PropertyConfigurator.configure(log4jConfigFile);
		return logger;
	}

}