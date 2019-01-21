/**
* 1- The SearchText class use to store all unique url entries. In other words, no duplicates. 
*    All the pages we visit will be unique (or at least their URL will be unique). 
*    We can enforce this idea by choosing the right data structure, in this case a set. and
* 2- This is just storing a bunch of URLs we have to visit next. When the crawler visits a page it collects 
*    all the URLs on that page and we just append them to this list
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2019-1-5 
*/

package com.ncu.processors;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import java.net.*;

public class SearchText
{
  // Using Set to contains unique entries.
  private Set<String> pagesVisited = new HashSet<String>();
  // Using List- This is just storing a bunch of URLs we have to visit next.
  private List<String> pagesToVisit = new LinkedList<String>();
  String roolUrl;
  Logger logger;
  String protocolValue,domainValue;
  int maxPageToSearch;
  Properties message;
  SystemInput systemInputObject;
  SearchText searchTextObject;

  //Creating Contructor to Initialization Logger 
  public SearchText()
  {
    // initialization of GetLogger to get logger object 
    GetLogger loggerObject=new GetLogger();
    Logger logger=loggerObject.loggerValue("SearchText");
    this.logger=logger;

    // initialization of configMessage to get messages 
    GetConfiguration propertyObject=new GetConfiguration();
    Properties message=propertyObject.configMessages();
    this.message=message;
  }
  
  // This method will set all urls into list and visited url into set.
  public URLCrawler search(String url, String searchWord, int numberOfPage)
  {   
    int maxPageToSearch = numberOfPage;
    this.maxPageToSearch=maxPageToSearch;
    
    // Creating Object Of SystemInput class ..
    SystemInput systemInputObj=new SystemInput();
    this.systemInputObject=systemInputObj;

    // Creating Object of Own Class ...
    SearchText searchTextObject = new SearchText();
    this.searchTextObject=searchTextObject;

    try{
      // creating URL class object to get content of url
      URL urlObj = new URL(url);
      String  protocolValue = urlObj.getProtocol(); //http
      this.protocolValue=protocolValue;
      String  domainValue = urlObj.getAuthority(); //example.com:80
      this.domainValue=domainValue;

    }catch(Exception e){
      logger.info(message.getProperty("notAccessibleUrl"));
      // Calling inputUrl() Method To Take Input From User. 
      systemInputObject.inputUrl();
    }

    // conside first utl as a root utl.
    this.roolUrl=this.protocolValue+"://"+this.domainValue+"/";

    // getting Time beform start Process . 
    Date beforeProcess = new Date();
    long startTime = System.currentTimeMillis( );

    // creating Object of URLCrawler class.
    URLCrawler utlCrawlerObj = new URLCrawler();
    
    try{
      while(pagesVisited.size() < maxPageToSearch){   
      	// check first time url is empty.
        String currentUrl;
        if(pagesToVisit.isEmpty()){
          currentUrl = url;
          pagesVisited.add(url);
        }
        else{
          currentUrl = this.nextUrl();
          currentUrl=this.roolUrl+currentUrl;
        }
        // Calling crawl method to get avalaible url and search word of given Url  
        utlCrawlerObj.crawl(currentUrl,searchWord); 
        // get all url page and calling getLinks method to store value in list
        pagesToVisit.addAll(utlCrawlerObj.getLinks());
        if(pagesToVisit.isEmpty()){
           logger.info(message.getProperty("noMorePage"));
         break;
        }
      }
    }catch (Exception e) {
      if(pagesVisited.size() < maxPageToSearch)
      {
        logger.info(message.getProperty("noMorePage"));
      }
    } 
    System.out.println("\n");
    logger.info("**Done** Visited " + pagesVisited.size() + " web page(s)");

    // End Time of Process ..... 
    Date afterProcess = new Date();
    long endTime = System.currentTimeMillis();

    long timedifference = searchTextObject.processTime(beforeProcess,afterProcess,startTime,endTime);
    utlCrawlerObj.processInformation(beforeProcess,afterProcess,timedifference);
    return utlCrawlerObj;
  }

  // Calculating Process Time .
  public long processTime(Date beforeProcess,Date afterProcess,long startTime,long endTime)
  {

    logger.info("___________________________ Time Duration _________________________\n");
    logger.info("Time Before Process :- "+beforeProcess);
    // Getting time after Process 
    logger.info("Time After Process :- "+afterProcess);  
    // checking time difference of start and end time of process. 
    long timedifference = endTime - startTime;
    logger.info("Duration In MilliSecond -: " + timedifference+"ms");
    logger.info("___________________________________________________________________");
    return timedifference;
  } 

  // getting url one by one from list 
  private String nextUrl()
  {
    String nextUrl;
    do{
      nextUrl = pagesToVisit.remove(0);
    } while(pagesVisited.contains(nextUrl)); 
    pagesVisited.add(nextUrl);
    return nextUrl;
  }
}