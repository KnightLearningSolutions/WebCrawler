/**
* The MaxLengthException class will generate    
* exception if user will more then 15 length of string 
*
* @author  knight Learning Solutions
* @version 1.0
* @since   2019-1-5 
*/

package com.ncu.exceptions;

public class MaxLengthException extends Exception{  
 public MaxLengthException(String s){  
  super(s);  
 }  
} 