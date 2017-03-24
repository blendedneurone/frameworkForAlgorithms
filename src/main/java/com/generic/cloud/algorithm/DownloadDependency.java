/**
 * 
 */
package com.generic.cloud.algorithm;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

/**
 * @author rohit.chaturvedi
 *
 */
public class DownloadDependency {
	
	/**
	 * Size of the buffer to read/write data
	 */
	private static final int BUFFER_SIZE = 4096;

	public void downloadJarOrZip(String resultPath, String dependency, boolean isJar) {

		if(isJar){
			downloadJar(resultPath, dependency);
		}else{
			downloadZip(resultPath, dependency);
		}
	}
	
	private void downloadJar(String resultPath, String dependency) {
		
		File jarFile;
		try{
			jarFile = new File(resultPath + AlgorithmUtils.LIB_DIR + File.separator + AlgorithmUtils.generateFileName(dependency));
			URL jarUrl = new URL(dependency);
			FileUtils.copyURLToFile(jarUrl, jarFile);
			AlgorithmUtils.providePermission(jarFile);

			System.out.println("jarFile path : " + jarFile.getAbsolutePath());
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Error occured during copy of URL to File : " + e.getStackTrace());
		}
	}
	
	private void downloadZip(String resultPath, String dependency) {
		
		File zipFile;
		try{
			zipFile = new File(resultPath + AlgorithmUtils.INPUT_DIR + File.separator + AlgorithmUtils.generateFileName(dependency));
			
			FileUtils.copyURLToFile(this.getClass().getResource(AlgorithmUtils.RESOURCE_FILE), zipFile);

			AlgorithmUtils.providePermission(zipFile);

			System.out.println("zipFile path : " + zipFile.getAbsolutePath());

			unZip(zipFile.getAbsolutePath(), resultPath + AlgorithmUtils.LIB_DIR);
			zipFile.delete();

		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Error occured during copy of URL to File : " + e.getStackTrace());
		}
	}
	
	/**
	 * Extracts a zip file specified by the zipFilePath to a directory specified by
	 * destDirectory (will be created if does not exists)
	 * @param zipFilePath
	 * @param destDirectory
	 * @throws IOException
	 */
	private void unZip(String zipFilePath, String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
			AlgorithmUtils.providePermission(destDir);
		}
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipIn.getNextEntry();
		
		// iterates over entries in the zip file
		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				extractFile(zipIn, filePath);
			} else {
				// if the entry is a directory, make the directory
				File dir = new File(filePath);
				dir.mkdir();
				AlgorithmUtils.providePermission(dir);
				dir.deleteOnExit();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}
	/**
	 * Extracts a zip entry (file entry)
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		File f= new File(filePath);
		AlgorithmUtils.providePermission(f);
		f.deleteOnExit();
		FileOutputStream fo = new FileOutputStream(f);
		BufferedOutputStream bos = new BufferedOutputStream(fo);
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}
}
