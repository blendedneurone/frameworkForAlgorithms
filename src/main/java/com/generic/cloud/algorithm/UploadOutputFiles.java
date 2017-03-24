/**
 * 
 */
package com.generic.cloud.algorithm;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * @author rohit.chaturvedi
 *
 */
public class UploadOutputFiles {

	public void writeToS3(AmazonS3 conn, String outputBucket, String resultPath) {

		System.out.println("Connection : " + conn);
		System.out.println("OutputBucket : " + outputBucket);
		System.out.println("ResultPath : " + resultPath);

		try {
			File [] outFiles = new File(resultPath + AlgorithmUtils.OUTPUT_DIR).listFiles();
			
			for(File outFile : outFiles){
				System.out.println("Provided path : " + outFile.getAbsolutePath());		
				conn.putObject(new PutObjectRequest(outputBucket, outFile.getName(), outFile).withCannedAcl(CannedAccessControlList.PublicRead));	
			}	
			FileUtils.deleteDirectory(new File(resultPath));

		} catch (AmazonServiceException e) {
			System.out.println("Amazon Service Exception : " + e.getStackTrace());
		} catch (AmazonClientException e) {
			System.out.println(e.getMessage());
			System.out.println("Amazon Client Exception : " + e.getStackTrace());
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("IO Exception : " + e.getStackTrace());
		}
	}
}
