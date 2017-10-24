/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liac.sfs.core;

/**
 *
 * @author liac01
 */
public class Structure {

	private int numClasses;
	private int numAttributes;
	
	public Structure()
	{
		
	}
	
	/**
	 * @return the numClasses
	 */
	public int getNumClasses() {
		return numClasses;
	}

	/**
	 * @param numClasses the numClasses to set
	 */
	public void setNumClasses(int numClasses) {
		this.numClasses = numClasses;
	}

	/**
	 * @return the numAttributes
	 */
	public int getNumAttributes() {
		return numAttributes;
	}

	/**
	 * @param numAttributes the numAttributes to set
	 */
	public void setNumAttributes(int numAttributes) {
		this.numAttributes = numAttributes;
	}
}
