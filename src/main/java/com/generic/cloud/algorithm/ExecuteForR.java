/**
 * 
 */
package com.generic.cloud.algorithm;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author rohit.chaturvedi
 *
 */
public class ExecuteForR {

	private int exitVal;	

	/**
	 * @param outputs 
	 * @param inputs 
	 * @param map 
	 * @param args
	 * @throws IOException
	 */
	public void invoke(String resultPath, Map<String, String> downloadsMap, String script, String[] inputs) throws IOException {

		AlgorithmUtils.createOutputDir(resultPath);
		invokeScriptFromR(resultPath, downloadsMap, script, inputs);
	}

	private void invokeScriptFromR(String resultPath, Map<String, String> downloadsMap, String script, String[] inputs) {

		File shFile = new File(resultPath+ AlgorithmUtils.RUN_DIR +"/run.sh");
		shFile.setExecutable(true);

		//sh file + R script + inputs + outDest
		String [] params = new String [1 + 1 +inputs.length + 1];

		for(int i=0; i< params.length; i++){
			if(i==0)
				params[i] = shFile.getAbsolutePath();
			else if(i==1 && downloadsMap.containsKey(script)){
				params[i] =  downloadsMap.get(script);
				downloadsMap.remove(script);
			}else if(i == (params.length -1))
				params[i] = resultPath + AlgorithmUtils.OUTPUT_DIR;	
			else{
				for(String in : inputs){
					if(downloadsMap.containsKey(in)){
						params[i] = downloadsMap.get(in);
						downloadsMap.remove(in);
						break;
					}else{
						params[i] = in;
					}
				}
			}
			System.out.println(i +" The value in the params is " + params[i]);
		}

		ProcessScript process= new ProcessScript();
		exitVal = process.process(resultPath, params);
	}

	public int getExitVal() {
		return exitVal;
	}
}
