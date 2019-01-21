/**
*  URLCrawler class will generate HTTP request and collecting the links.
*  as well this class will it will find search word .
*
* @author  knight Learning Solutions
* @version 1.0
* @since    2019-1-5 
*/

package com.ncu.processors;

import java.util.LinkedList;
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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class URLCrawler{

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

  public URLCrawler(){
    // initialization of GetLogger to get logger object 
    GetLogger loggerObject=new GetLogger();
    Logger logger=loggerObject.loggerValue("SearchText");
    this.logger=logger;
    // initialization of GetLogger to get logger object 
    JSONArray jsonArryObject = new JSONArray();
    this.jsonArryObject=jsonArryObject;
  }

  public void crawl(String urlToSearch,String searchWord){

    JSONObject jsonObject = new JSONObject();
    this.jsonObject=jsonObject;
    this.searchWord=searchWord;
    this.urlToSearch=urlToSearch;
    URL url;
    InputStream is = null;
    BufferedReader br;
    String line;
    int linkCount=0;
    String HTML_A_TAG_PATTERN = "(?i)<a([^>]+)>(.+?)</a>";
    int lastIndex = 0;
    int count = 0;

    try {
      URLCrawler mainObj=new URLCrawler();
      url = new URL(urlToSearch);
      logger.info("Visiting Url :- "+url);
      jsonObject.put("Url",url);

      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();
      int code = connection.getResponseCode();
      logger.info("Response of Url is :- "+code);
      jsonObject.put("Url Status",code);
      this.getUrl=urlToSearch;

      if (code==200)
      {
        is = url.openStream(); 
        br = new BufferedReader(new InputStreamReader(is));
        // reading html page one by one .
        while ((line = br.readLine()) != null) {
          // counting search word on page.
          while((lastIndex = line.indexOf(searchWord, lastIndex)) != -1) {
           count++;
           lastIndex += searchWord.length() - 1; 
         }
         // finding recomended utl
         if(count>this.maxCount){
          this.maxCount=count;
          this.recomendUrl=this.getUrl;
         }
         // finding (href) on page.
         if(line.contains("href="))
         {
          Pattern patternTag = Pattern.compile(HTML_A_TAG_PATTERN);
          Matcher matcherTag = patternTag.matcher(line);
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
          logger.info("Total Links Availbale On Page :- "+linkCount);
          jsonObject.put("Links_Availbale",linkCount);
          logger.info("Total "+searchWord+" Availbale On Page - "+count);
          jsonObject.put("Word_Count",count);
          jsonArryObject.add(jsonObject);
          count=0;
          linkCount=0;
          logger.info("\n-----------------------------------------------\n");
          }else{
            logger.info("Status not get 200  -"+this.getUrl);
          }
        }catch(MalformedURLException mue) {
        URLCrawler obj=new URLCrawler();
        obj.crawl(this.urlToSearch,this.searchWord);
        }catch(IOException ioe) {
        URLCrawler obj=new URLCrawler();
        obj.crawl(this.urlToSearch,this.searchWord);
        } finally {
        try {
        if (is != null) is.close();
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
  // write value into json file
   public void writeOutputInFile(String fileName){
      String outputFolderPath=System.getProperty("user.dir")+ File.separator+"jsons/";
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFolderPath+fileName))){
          JSONObject ObjWite = new JSONObject();
          ObjWite.put("beforeProccessTime",this.beforeStart);
          ObjWite.put("afterProccessTime",this.afterStart);
          ObjWite.put("timeDifference",this.diffInMilisecond);
          ObjWite.put("searchWord",this.searchWord);
          ObjWite.put("SearchinPath",this.jsonArryObject);
          bw.write(ObjWite.toJSONString());
          bw.flush();
          logger.info("\n(*.*) Successfully Write This Information Into "+fileName+" Json File\n");
          logger.info("You Can Get "+fileName+" On Path -: "+outputFolderPath);
        }catch (IOException e) {
          e.printStackTrace();
          System.out.println(e);
        }
    }

  // to get all links from user
  public List<String> getLinks()
  {
    return this.links;
  }

}