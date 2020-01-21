package com.revature.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class log {

	public static void appendLog(String text) throws IOException 
	{
	     
	    BufferedWriter writer = new BufferedWriter(
	                            new FileWriter("./log.log", true));
	    
	    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
	    writer.newLine(); 
	    writer.write(dateFormat.format(date) + ": " + text);
	    writer.close();
	}
}
