/**
 * 
 */
package com.generic.cloud.algorithm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.amazonaws.services.s3.AmazonS3;

/**
 * @author rohit.chaturvedi
 *
 */
public class DownloadInputFiles {

	private String resultPath;
	private Map<String, String> downloads = new HashMap<String, String>();

	public void downloadFiles(AmazonS3 conn, String[] inputs) {

		try {
			FileUtils.cleanDirectory(new File(AlgorithmUtils.TEMP_DIR));

			this.resultPath = Files.createTempDirectory("").toString();
			System.out.println("temp path : " + this.resultPath);

			downloadFilesFromURL(inputs);

		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Error occured during copy of URL to File : " + e.getStackTrace());
		}
	}

	private void downloadFilesFromURL(String[] inputs) {

		for(String url : inputs){
			try{
				String fileName = AlgorithmUtils.generateFileName(url);
				if(!fileName.isEmpty()){
					File downloadFile = new File(this.resultPath + AlgorithmUtils.INPUT_DIR + File.separator + fileName);
					URL downloadUrl = new URL(url);
					FileUtils.copyURLToFile(downloadUrl, downloadFile);

					AlgorithmUtils.providePermission(downloadFile);
					downloadFile.deleteOnExit();
					System.out.println("input file path : " + downloadFile.getAbsolutePath());

					downloads.put(fileName, downloadFile.getAbsolutePath());
					System.out.println("key : "+ fileName +" value : "+ downloadFile.getAbsolutePath());
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.out.println("Error occured during copy of URL to File : " + e.getStackTrace());
			}
		}
	}

	public String getResultPath() {
		return resultPath;
	}

	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	public Map<String, String> getDownloads() {
		return downloads;
	}

	public void setDownloads(Map<String, String> downloads) {
		this.downloads = downloads;
	}
}
