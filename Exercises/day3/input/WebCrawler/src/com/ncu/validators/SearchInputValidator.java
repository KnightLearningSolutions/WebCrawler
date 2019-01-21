/**
* Created SearchInputValidator class to check all validation of User given Search Words.
* 1- EmptySearchInputException - if user will give blank space to search.
* 2- MaxLengthException- Generate "MaxLengthException" Exception if user give more then 15 length of string
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


public class SearchInputValidator{

	Logger logger;
	Properties message,constants;

	public SearchInputValidator()
	{   
		// initialization of GetLogger to get logger object 
		GetLogger loggerObject=new GetLogger();
		Logger logger=loggerObject.loggerValue("SearchInputValidator");
		this.logger=logger;

		// initialization of configMessage to get messages  
		GetConfiguration propertyObject=new GetConfiguration();
		Properties message=propertyObject.configMessages();
		this.message=message;

    // calling configConstants method to get constant values 
		Properties constants=propertyObject.configConstants();
		this.constants=constants;
	}

	public boolean validator(String searchValue)
	{
		
		try{
			SearchInputValidator validatorObj=new SearchInputValidator();

      // Generate "EmptySearchException" Exception if user gives blank space to search . 
			validatorObj.empltySearchInput(searchValue);

			// Generate "MaxLengthException" Exception if user give more then 15 length of string to search.
			validatorObj.checkLength(searchValue); 
		}
		catch(EmptySearchInputException e){
			logger.error("\n"+e+message.getProperty("emptySearchMessage")+"\n");
			return false;
		}

		catch(MaxLengthException e){
			this.logger.error("\n"+e+message.getProperty("searchLengthMessage")+"\n");
			return false;
		}
		catch(Exception e){
			logger.error("\n"+e+"\n");
			return false;
		}
		return true;
	}

	/* Generate "empltySearchInput" Exception if user gives blank space to search  */

	private void empltySearchInput(String searchValue) throws EmptySearchInputException {	
		if (searchValue == null || searchValue.trim().isEmpty()) {
			throw new EmptySearchInputException("");
		}
	}

	/* Generate "MaxLengthException" Exception if user give more then 15 length of string */
	
	private void checkLength(String searchValue) throws MaxLengthException {
		String lengthValue = constants.getProperty("searchLength");
		int getLength=Integer.parseInt(lengthValue);
		if(searchValue.length()>getLength){
			throw new MaxLengthException("");
		}
	}

}