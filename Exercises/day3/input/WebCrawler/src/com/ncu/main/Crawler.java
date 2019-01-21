/**
*  The Crawler program implements an application that
*  take a url of website and search words as a input and The Crawler will go to 
*  that web page and collect all of the words on the page as well as all of the URLs on the page. 
*  If the word isn't found on that page, it will go to the next page and repeat 
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2019-1-5 
*/

package com.ncu.main;

import com.ncu.processors.*;
import java.util.Scanner;
import java.io.File;

import org.apache.log4j.Logger;


public class Crawler
{

  // This main method will start all processess from here .
	public static void main(String[] args)
	{ 
    // Installation of logger object 
		GetLogger loggerObject=new GetLogger();
		Logger logger=loggerObject.loggerValue("Crawler");

		logger.info("=======================================");
		logger.info("||        Welcome NCU Students       ||");
		logger.info("=======================================");
    
    // Creating object of SystemInput to take inputs from user.
		SystemInput inputObject=new SystemInput();
		inputObject.inputUrl();

	}
}


