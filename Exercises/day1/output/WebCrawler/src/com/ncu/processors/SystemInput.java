/**
*  The SystemInput is class to take Url , 
*  search word , and page number count from user 
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2019-1-5 
*/

package com.ncu.processors;

import java.util.Scanner;
import java.util.Properties;
import java.util.InputMismatchException;

import org.apache.log4j.Logger;

public class SystemInput
{ 
	Logger logger;
	Properties message,constants;

	//Creating Contructor to Initialization Logger and Configs
	public SystemInput()
	{
		// initialization of GetLogger to get logger object 
		GetLogger loggerObject=new GetLogger();
		Logger logger=loggerObject.loggerValue("SystemInput");
		this.logger=logger;

		// initialization of configMessage to get messages 
		GetConfiguration propertyObject=new GetConfiguration();
		Properties message=propertyObject.configMessages();
		this.message=message;

		// calling configConstants method to get constant values 
		Properties constants=propertyObject.configConstants();
		this.constants=constants;
	}

  /* This method will take one url from user and it will called to 
  UrlValidator to Check Vaditation of Url */
  public void inputUrl()
  {
	  // Creating object of current class object to use in this method only.
  	SystemInput systemInputObj=new SystemInput();

    // Taking Url as a input from user.
  	System.out.println("\n");
  	logger.info("Please Enter Your Url :-");
  	Scanner scan = new Scanner(System.in);
  	String urlName = scan.nextLine();
    
   /* Calling searchValue only for Testing Purpose this line will delete further session  */
   systemInputObj.searchValue(urlName);

 }

  // This method will take Search Word from user , which user want to search on url
 public void searchValue(String urlName)
 {
		// Creating object of current class object to use in this method only.
   SystemInput systemInputObj=new SystemInput();

    // Taking Url as a input from user.
   System.out.println("\n");
   logger.info("Enter Your String Which You Want to Search :- ");
   Scanner scan = new Scanner(System.in);
   String searchValue= scan.nextLine();

   /* Calling searchValue only for Testing Purpose this line will delete further session  */
   systemInputObj.numberOfPages(urlName,searchValue);

 } 

  // This method will take how number of pages do you want to search. 
 public void numberOfPages(String urlName,String searchValue)
 {
		// Creating object of current class object to use in this method only.
   SystemInput systemInputObj=new SystemInput();

		// Using Try Catch if to catch exception if user give string value as number of page to search
   try{
		// Taking number of pages as a input from user.
    System.out.println("\n");
    logger.info("How Many Pages Do You Want To Search :- ");
    Scanner scan = new Scanner(System.in);
    int numberOfPage= scan.nextInt();

    /* Calling searchValue only for Testing Purpose this line will delete further session  */
    systemInputObj.writeOutputInFile();

  }catch(InputMismatchException e){
    logger.error("\n"+e+message.getProperty("validIntegerInput")+"\n");
    systemInputObj.numberOfPages(urlName,searchValue);
  }
}

  // writeOutputInFile Method to write output into json file .// URLCrawler urlCrawlerObj
public void writeOutputInFile()
{ 
		// Creating object of current class object to use in this method only.
 SystemInput systemInputObj=new SystemInput();

    // Taking Url as a input from user.
 System.out.println("\n");
 logger.info("Please Enter Your Json File Name :- ");
 Scanner scan = new Scanner(System.in);
 String fileName= scan.nextLine();
 
 /* Calling searchValue only for Testing Purpose this line will delete further session  */
 systemInputObj.exits();

}

	// Implementation of Exit from System
public void exits(){

		// Creating object of current class object to use in this method only.
  SystemInput systemInputObj=new SystemInput(); 

  	// Ask user to exit from system or Continue process.
  System.out.println("\n");
  logger.info(message.getProperty("exitMessage"));
  Scanner exitObject = new Scanner(System.in);
  String userPermisson= exitObject.nextLine();
  String setPermisson = constants.getProperty("endProcess");
  if(setPermisson.equalsIgnoreCase(userPermisson)){
   logger.info("Thanks for Using Our System .......!");
   System.exit(0);
 }else{
		// Continue our process Again.
   systemInputObj.inputUrl();
 } 
}
} 