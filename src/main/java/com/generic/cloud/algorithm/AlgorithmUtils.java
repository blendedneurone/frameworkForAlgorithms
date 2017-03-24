/**
 * 
 */
package com.generic.cloud.algorithm;

import java.io.File;

/**
 * @author rohit.chaturvedi
 *
 */
public class AlgorithmUtils {
	
	public static final String SUCCESS = "Success";
	
	public static final String FAILURE = "Fail";
	
	public static final String ERROR_FILE = "errorlog.txt";
		
	public static final String TEMP_DIR = "/tmp";
	
	public static final String INPUT_DIR = "/input";
	
	public static final String LIB_DIR = "/lib";
	
	public static final String OUTPUT_DIR = "/output";
	
	public static final String RESOURCE_FILE = "portableR-master";
	
	public static final String RUN_DIR = LIB_DIR + File.separator + RESOURCE_FILE;

	public static String generateFileName(String fullName) {
		if (!fullName.isEmpty()) {
			String[] part = fullName.split(File.separator);
			if (part.length > 1) {
				return part[part.length - 1];
			}else{
				return "";
			}
		}
		return "";
	}
	
	public static void providePermission(File file){
		file.setExecutable(true);
		file.setWritable(true);
		file.setReadable(true);
	}
	
	public static void createOutputDir(String resultPath) {

		File outFile = new File(resultPath + OUTPUT_DIR);
		outFile.mkdir();
		outFile.setWritable(true);
	}
}
