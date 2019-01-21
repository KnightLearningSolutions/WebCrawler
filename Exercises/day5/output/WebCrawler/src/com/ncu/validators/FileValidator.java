/**
* Created FileValidator class to check all validation of File name which user will give.
* 1- EmptyFileNameException - Generate "EmptyFileNameException" Exception if user gives blank space as a file name
* 2- FileFormatException- Generate "FileFormatException" Exception if user does not . before extension
* 3- InvalidFileException- Generate "InvalidFileException" Exception if user will give other then json file
* 4- SpecialCharacterException- Generate "SpecialCharacterException" Exception will give any special character in file name.
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
import java.io.File;

import org.apache.log4j.Logger;

public class FileValidator{

  Logger logger;
	Properties message,constants;
  String filePath;
  
	public FileValidator()
	{   
		// initialization of GetLogger to get logger object 
		GetLogger loggerObject=new GetLogger();
		Logger logger=loggerObject.loggerValue("FileValidator");
		this.logger=logger;

		// initialization of configMessage to get messages  
		GetConfiguration propertyObject=new GetConfiguration();
		Properties message=propertyObject.configMessages();
		this.message=message;

    // calling configConstants method to get constant values 
		Properties constants=propertyObject.configConstants();
		this.constants=constants;

		// calling jsonFilePath method to get path of json file . 
		String filePath=propertyObject.jsonFilePath();
		this.filePath=filePath;
	}

	public boolean validator(String fileName)
	{

		try{
			FileValidator validatorObj=new FileValidator();

      // Generate "EmptyFileNameException" Exception if user gives blank space to as a file name. 
			validatorObj.empltyFileName(fileName);

			// Generate "FileFormatException" Exception if user does not . before extension
			validatorObj.fileFormat(fileName);

			// Generate "FileFormatException" Exception if user does not . before extension
			validatorObj.checkDotFormat(fileName);

      // Generate "InvalidFileException" Exception if user will give other then json file
			validatorObj.checkExtension(fileName);

			// Generate "SpecialCharacterException" Exception will give any special character in file name.
			validatorObj.checkSpecialCharacter(fileName);

			// Method to check user given file name is not already into directory 
			Boolean fileExists= new File(filePath+fileName).exists();
			if(fileExists)
			{  
				System.out.println("\n");
			  logger.error(message.getProperty("fileExistMessage")+"\n"); 
				return false;
			}
		}
		catch(EmptyFileNameException e){
			logger.error("\n"+e+message.getProperty("emptyFileNameMessage")+"\n");
			return false;
		}
    // To catch FileFormatException .
		catch(FileFormatException e){
			logger.error("\n \n"+e+message.getProperty("extensionFormatMessage")+"\n");
			return false;
		}
    // To catch InvalidFileException 
		catch(InvalidFileException e){
			logger.error("\n \n"+e+message.getProperty("invalidFileExtension")+"\n");
			return false;
		}
		// To catch SpecialCharacterException 
		catch(SpecialCharacterException e){
			logger.error("\n \n"+e+message.getProperty("specialcharacterMessage")+"\n");
			return false;
		}
		catch(Exception e){
			logger.error("\n"+e+"\n");
			return false;
		}
		return true;
	}

	/* Generate "EmptyFileNameException" Exception if user gives blank space as a file name  */
	private void empltyFileName(String fileName) throws EmptyFileNameException {	
		if (fileName == null || fileName.trim().isEmpty()) {
			throw new EmptyFileNameException("");
		}
	}

	/* Generate "FileFormatException" Exception if user does not . before extension */
	private void fileFormat(String fileName) throws FileFormatException{
		String fileFormatGet = constants.getProperty("setDot");
		Pattern patternObject = Pattern.compile(fileFormatGet);
		Matcher matcherObject = patternObject.matcher(fileName);
		Boolean value = matcherObject.find();
		if(!value){
			throw new FileFormatException("");
		}
	}

	/* Generate "InvalidFileException" Exception if user will not give dot (.) in file */
	private void checkDotFormat(String fileName) throws InvalidFileException {
		String [] haveDot= fileName.split("\\.");
		if (haveDot.length<=1) {
			throw new InvalidFileException("");
		}
	}

	/* Generate "InvalidFileException" Exception if user will give other the json url */
	private void checkExtension(String fileName) throws InvalidFileException {
		String name = fileName.split("\\.")[0];
		String currentExtension = fileName.split("\\.")[1];
		String getExtenstion = constants.getProperty("setFileExtension");
		if(!getExtenstion.equals(currentExtension)){
			throw new InvalidFileException("");
		}
	}

	/* Generate "SpecialCharacterException" Exception will give any special character in filename */
	private void checkSpecialCharacter(String fileName) throws SpecialCharacterException{
		String name = fileName.split("\\.")[0];
		String regexValue = constants.getProperty("fileRegex");
		Pattern  patternGet = Pattern.compile("["+regexValue+"]");
		Matcher matcherObj = patternGet.matcher(name);
		boolean check = matcherObj.find();
		if (check == true){
			throw new SpecialCharacterException("");
		}
	}

}