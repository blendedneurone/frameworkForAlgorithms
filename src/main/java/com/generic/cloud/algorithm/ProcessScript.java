/**
 * 
 */
package com.generic.cloud.algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author rohit.chaturvedi
 *
 */
public class ProcessScript {

	public int process(String resultPath, String[] params) {
		Process process = null;
		int shellExitStatus;
		
		try {
			File file = new File(resultPath + AlgorithmUtils.OUTPUT_DIR + File.separator + AlgorithmUtils.ERROR_FILE);

			ProcessBuilder pb = new ProcessBuilder(params);

			pb.directory(new File(resultPath+ AlgorithmUtils.LIB_DIR + File.separator));
			pb.redirectErrorStream(true);
			pb.redirectOutput(file);
			process = pb.start();

			InputStream stderr = process.getInputStream();
			
			shellExitStatus = process.waitFor();
			System.out.println("Exit status" + shellExitStatus);

			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;

			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			br.close();
			isr.close();
			stderr.close();
		
			System.out.println("Waiting ...");
			System.out.println("Returned Value :" + process.exitValue());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return process.exitValue();
	}
}
