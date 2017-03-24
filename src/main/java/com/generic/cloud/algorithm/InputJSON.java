/**
 * 
 */
package com.generic.cloud.algorithm;

/**
 * @author rohit.chaturvedi
 *
 */
public class InputJSON {

	private String [] downloads;
	private String dependency;
	private String script;
	private String [] inputs;
	private String outputBucket;

	public String[] getDownloads() {
		return downloads;
	}
	public void setDownloads(String[] downloads) {
		this.downloads = downloads;
	}
	
	public String getDependency() {
		return dependency;
	}
	public void setDependency(String dependency) {
		this.dependency = dependency;
	}
	
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	
	public String[] getInputs() {
		return inputs;
	}
	public void setInputs(String[] inputs) {
		this.inputs = inputs;
	}

	public String getOutputBucket() {
		return outputBucket;
	}
	public void setOutputBucket(String outputBucket) {
		this.outputBucket = outputBucket;
	}
}
