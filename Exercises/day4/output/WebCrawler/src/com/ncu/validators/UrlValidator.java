/**
*  The UrlValidator class will check all validations of url 
*  List of validation -
*  1- EmptyUrlException - if User will give black space instead of url 
*  2- InvalidUrlException - if User will give invalid Url.
* 
* @author  knight Learning Solutions
* @version 1.0
* @since   2019-1-5
*/

package com.ncu.validators;
import com.ncu.exceptions.*;
import com.ncu.processors.GetLogger;
import com.ncu.processors.GetConfiguration;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class UrlValidator{

	Logger logger;
	Properties message,constants;

	public UrlValidator()
	{   
		// initialization of GetLogger to get logger object 
		GetLogger loggerObject=new GetLogger();
		Logger logger=loggerObject.loggerValue("UrlValidator");
		this.logger=logger;

		// initialization of configMessage to get messages 
		GetConfiguration propertyObject=new GetConfiguration();
		Properties message=propertyObject.configMessages();
		this.message=message;
		
    // calling configConstants method to get constant values 
		Properties constants=propertyObject.configConstants();
		this.constants=constants;
	}

	public boolean validator(String urlName)
	{   

		try{

			UrlValidator validatorObj = new UrlValidator();

      //Generate "EmptyUrlException" Exception if gives blank space as a Url 
			validatorObj.empltyUrlMethod(urlName);

			//Generate "InvalidUrlException" Exception if user give wrong Url
			validatorObj.invalidUrlMethod(urlName); 
		}
      //All Excetion will taken in this section 
		catch(EmptyUrlException e){
			logger.error("\n"+e+message.getProperty("emptyUrlMessage")+"\n");
			return false;
		}
		catch(InvalidUrlException e){
			this.logger.error("\n"+e+message.getProperty("invalidUrlMessage")+"\n");
			return false;
		}
		catch(Exception e){
			logger.error("\n"+e+"\n");
			return false;
		}

		return true;
	}

	/* Generate "EmptyUrlException" Exception if gives blank space as a Url  */
	private void empltyUrlMethod(String urlName) throws EmptyUrlException {	
		if (urlName == null || urlName.trim().isEmpty()) {
			throw new EmptyUrlException("");
		}
	}

	/* Generate "InvalidUrlException" Exception if user give wrong Url */
	private void invalidUrlMethod(String urlName) throws InvalidUrlException {
		String regexValue = constants.getProperty("urlRegex");		
		Pattern patObject = Pattern.compile(regexValue);
		Matcher matcher = patObject.matcher(urlName);
		boolean value = matcher.matches();
		if(!value)
		{
			throw new InvalidUrlException("");
		}
	}

}