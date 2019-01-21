/**
*  URLCrawler class will generate HTTP request and collecting the links.
*  as well this class will it will find search word .
*
* @author  knight Learning Solutions
* @version 1.0
* @since    2019-1-5 
*/

package com.ncu.processors;

import java.util.List;
import java.net.*;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.LinkedList;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class URLCrawler{

  // Storing Url in list . 
  private List<String> links = new LinkedList<String>();
  String urlToSearch,searchWord,getUrl;
  Logger logger;
  JSONObject jsonObject;
  JSONArray jsonArryObject;
  Date beforeStart,afterStart;
  long diffInMilisecond;
  int maxCount=0;
  String recomendUrl;
  int maxGetWord;
  Properties message,constants;
  URLCrawler crawlerObject;

  public URLCrawler(){
    // initialization of GetLogger to get logger object 
    GetLogger loggerObject=new GetLogger();
    Logger logger=loggerObject.loggerValue("SearchText");
    this.logger=logger;

    // initialization of GetLogger to get logger object 
    JSONArray jsonArryObject = new JSONArray();
    this.jsonArryObject=jsonArryObject;

    // initialization of configMessage to get messages 
    GetConfiguration propertyObject=new GetConfiguration();
    Properties message=propertyObject.configMessages();
    this.message=message;

    // calling configConstants method to get constant values 
    Properties constants=propertyObject.configConstants();
    this.constants=constants;
  }

  public void crawl(String urlToSearch,String searchWord)
  {
  	JSONObject jsonObject = new JSONObject();
  	this.jsonObject=jsonObject;
  	this.searchWord=searchWord;
  	this.urlToSearch=urlToSearch;
    
    URLCrawler crawlerObject = new URLCrawler();
    this.crawlerObject=crawlerObject;

  	InputStream streamObject = null;
  	BufferedReader readerObject;

  	String line;
  	int linkCount=0;
  	int lastIndex = 0;
  	int wordCount = 0;

  	try 
  	{
  		URL url = new URL(urlToSearch);
  		logger.info("Visiting Url :- "+url);
  		jsonObject.put("Url",url);

      // Creating Connection with Url.
  		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
  		connection.setRequestMethod("GET");
  		connection.connect();
  		int code = connection.getResponseCode();
  		logger.info("Response of Url is :- "+code);
  		jsonObject.put("Url Status",code);
  		this.getUrl=urlToSearch;

  		if(code==200)
  		{
  			streamObject = url.openStream(); 
  			readerObject = new BufferedReader(new InputStreamReader(streamObject));

        // Reading html Page line by line 
  			while ((line = readerObject.readLine()) != null) {

          // Counting Search Word Of User On Page.
  				while((lastIndex = line.indexOf(searchWord, lastIndex)) != -1) {
  					wordCount++;
  					lastIndex += searchWord.length() - 1; 
  				}

         // Finding Most Recomanded Url 
  				if(wordCount>this.maxCount){
  					this.maxCount=wordCount;
  					this.recomendUrl=this.getUrl;
  				}

         // Finding Links on page.
  				if(line.contains("href="))
  				{
  					String htmlRegex = constants.getProperty("HTML_A_TAG_PATTERN");
  					Pattern patternTag = Pattern.compile(htmlRegex);
  					Matcher matcherTag = patternTag.matcher(line);
          // Getting Link value from href 
  					while (matcherTag.find()){
                    String href = matcherTag.group(1); // href
                    String linkText = matcherTag.group(2); // link text
                    if(!href.contains("https:") && !href.contains("http:"))
                    {
                    	Pattern que = Pattern.compile("\"([^\"]*)\"");
                    	Matcher qm = que.matcher(href);
                    	while (qm.find()) {
                    		this.links.add(qm.group(1));
                    		linkCount=linkCount+1;
                    	}
                    }
                  }
                }
              }
              // Getting All Available Link On Of Page
              logger.info("Total Links Available On Page :- "+linkCount);
              jsonObject.put("Links_Available",linkCount);

              logger.info("Total "+searchWord+" Available On Page - "+wordCount);
              jsonObject.put("Word_Count",wordCount);
              jsonArryObject.add(jsonObject);
              wordCount=0;
              linkCount=0;
              logger.info("\n-----------------------------------------------\n");
            }else{
            	logger.info(message.getProperty("urlProblem")+" "+getUrl);
            	logger.info("\n-----------------------------------------------\n");
            }
      }catch(Exception mue) 
      {
      	logger.info(message.getProperty("netWorkIssue"));
      }
    // At Last Calling Finally block to close Stream.
    finally {
      try {
          if (streamObject != null) streamObject.close();
      }catch(IOException ioe){
          System.out.println(this.getUrl);
      }
    } 
  }

// get process information at class leve.
    public void processInformation(Date beforeStart,Date afterStart,long diffInMilisecond)
      {
        this.beforeStart=beforeStart;
        this.afterStart=afterStart;
        this.diffInMilisecond=diffInMilisecond;
      }


  // to get all links from user
        public List<String> getLinks()
        {
          return this.links;
        }

      }