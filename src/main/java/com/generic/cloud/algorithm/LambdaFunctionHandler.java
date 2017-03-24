package com.generic.cloud.algorithm;

import org.apache.commons.io.FilenameUtils;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * @author rohit.chaturvedi
 *
 */
public class LambdaFunctionHandler implements RequestHandler<InputJSON, String> {

	private AmazonS3 conn;

	@Override
	public String handleRequest(InputJSON inputString, Context context) {
		context.getLogger().log("Input: " + inputString);

		try {
			if (inputString != null) {
				this.conn = getAmazonConnection();

				String ahca = FilenameUtils.getExtension(inputString.getScript());
				String msg = "";
				System.out.println(ahca);

				DownloadInputFiles dif = new DownloadInputFiles();
				dif.downloadFiles(conn, inputString.getDownloads());

				DownloadDependency dd =  new DownloadDependency();
				if(ahca.equalsIgnoreCase("sh")){
					dd.downloadJarOrZip(dif.getResultPath(), inputString.getDependency(), true);

					ExecuteForJar efj = new ExecuteForJar();					
					efj.invoke(dif.getResultPath(), dif.getDownloads(), inputString.getScript(), inputString.getInputs());

					if (efj.getExitVal() != 1) {
						msg = AlgorithmUtils.SUCCESS;
					} else {
						msg = AlgorithmUtils.FAILURE;
					}
				}else{
					dd.downloadJarOrZip(dif.getResultPath(), inputString.getDependency(), false);

					ExecuteForR efr = new ExecuteForR();
					efr.invoke(dif.getResultPath(), dif.getDownloads(), inputString.getScript(), inputString.getInputs());

					if (efr.getExitVal() != 1) {
						msg = AlgorithmUtils.SUCCESS;
					} else {
						msg = AlgorithmUtils.FAILURE;
					}
				}

				UploadOutputFiles uof = new UploadOutputFiles();
				uof.writeToS3(conn, inputString.getOutputBucket(), dif.getResultPath());

				return msg;
			}else {
				return AlgorithmUtils.FAILURE;		
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			e.printStackTrace();
			return AlgorithmUtils.FAILURE;
		}
	}

	private AmazonS3 getAmazonConnection() {
		return new AmazonS3Client(new DefaultAWSCredentialsProviderChain());
	}
}
