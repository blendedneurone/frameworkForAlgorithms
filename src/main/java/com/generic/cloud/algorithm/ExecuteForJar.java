/**
 * 
 */
package com.generic.cloud.algorithm;

import java.io.IOException;
import java.util.Map;

/**
 * @author rohit.chaturvedi
 *
 */
public class ExecuteForJar {

	private int exitVal;	
	
	public void invoke(String resultPath, Map<String, String> downloadsMap, String script, String[] inputs) throws IOException {
		AlgorithmUtils.createOutputDir(resultPath);
		invokeScriptFromJar(resultPath, downloadsMap, script, inputs);
	}

	private void invokeScriptFromJar(String resultPath, Map<String, String> downloadsMap, String script, String[] inputs) {

		//sh file + inputs + outDest
		String [] params = new String [1 +inputs.length + 1];

		for(int i=0; i< params.length; i++){
			if(i==0 && downloadsMap.containsKey(script)){
				params[i] = downloadsMap.get(script);
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
