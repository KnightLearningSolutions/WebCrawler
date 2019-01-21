/**
* Created NumberOfPagesValidator class to check all validation of User given input as a
* number pages to search .
* 1- EmpltyNumberOfPages - Generate "empltyNumberOfPages" Exception if user gives blank space to search.
* 2- MaxPageNumberException-Generate "MaxPageNumberException" Exception if user give more then 20 
*    page number to search
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

import org.apache.log4j.Logger;


public class NumberOfPagesValidator{

	Logger logger;
	Properties message,constants;

	public NumberOfPagesValidator()
	{   
		// initialization of GetLogger to get logger object 
		GetLogger loggerObject=new GetLogger();
		Logger logger=loggerObject.loggerValue("NumberOfPagesValidator");
		this.logger=logger;

		// initialization of configMessage to get messages 
		GetConfiguration propertyObject=new GetConfiguration();
		Properties message=propertyObject.configMessages();
		this.message=message;

    // calling configConstants method to get constant values 
		Properties constants=propertyObject.configConstants();
		this.constants=constants;
	}

	public boolean validator(int numberOfPage)
	{
		
		try{
			NumberOfPagesValidator validatorObj=new NumberOfPagesValidator();

			// Generate "MaxNumberOfPages" Exception if user give more then 20 length of string to search.
			validatorObj.maxNumberOfPages(numberOfPage); 
		}
		
		catch(MaxPageNumberException e){
			logger.error("\n"+e+message.getProperty("maxPageNumberMessage")+"\n");
			return false;
		}
		catch(Exception e){
			logger.error("\n"+e+"\n");
			return false;
		}
		return true;
	}

	/* Generate "MaxPageNumberException" Exception if user give more then 20 page number to search */
	
	private void maxNumberOfPages(int numberOfPage) throws MaxPageNumberException {
		String pageNumber = constants.getProperty("maxPageNumber");
		int getLength=Integer.parseInt(pageNumber);
		if(numberOfPage>getLength){
			throw new MaxPageNumberException("");
		}
	}

}