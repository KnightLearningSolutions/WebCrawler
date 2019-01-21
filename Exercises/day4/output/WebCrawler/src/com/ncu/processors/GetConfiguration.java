/**
*  The GetConfiguration class use to initialization Properties
*  for different - different classes to get exceptions.properties
*  and constants.properties values;
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2019-1-5 
*/

package com.ncu.processors;

import java.io.File;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;

public class GetConfiguration
{
	// configMessages method initialization Properties object and retrun one Properties class object
	public Properties configMessages()
	{   
		Properties propertieValue=null;
		try{
			Properties propertieObj = new Properties();
			String configPath = System.getProperty("user.dir")+ File.separator + "configs/constants/exceptions.properties";
			InputStream input = new FileInputStream(configPath);
			propertieObj.load(input);
			propertieValue=propertieObj;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return propertieValue;
	}

	// configConstants method initialization Properties object and retrun one Properties class object
	public Properties configConstants()
	{   
		Properties propertieValue=null;
		try{
			Properties propertieObj = new Properties();
	    String configPath = System.getProperty("user.dir")+ File.separator + "configs/constants/constants.properties";
			InputStream input = new FileInputStream(configPath);
			propertieObj.load(input);
			propertieValue=propertieObj;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return propertieValue;
	}

	// configuration to get path of json file .
	public String jsonFilePath()
	{
		String filePathSend="";
		try{
			String filePath= System.getProperty("user.dir")+ File.separator+"jsons/";
			filePathSend=filePath;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return filePathSend;
	}
}