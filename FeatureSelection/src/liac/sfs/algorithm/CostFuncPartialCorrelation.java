/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liac.sfs.algorithm;

import java.util.ArrayList;
import org.ejml.simple.SimpleMatrix;

/**
 *
 * @author liac01
 */
public class CostFuncPartialCorrelation implements CostFunction{
	
	private SimpleMatrix invCov;
	private int numAttributes;
	private int numClasses;
	private ArrayList<Double> corrClasses;
	
	public CostFuncPartialCorrelation(SimpleMatrix invCov, int nAttributes, int nClasses)
	{
		this.invCov = invCov;
		this.numAttributes = nAttributes;
		this.numClasses = nClasses;	
		this.corrClasses = new ArrayList<>();
		this.mxCalculatePartialCorrelationClasses();
	}
	
	private void mxCalculatePartialCorrelationClasses()
	{
		double sumTmp;
		
		for (int i = 0; i < this.numAttributes; i++)
		{
			sumTmp = 0;
			for (int j = 0; j < this.numClasses; j++) 
				sumTmp += Math.abs(this.invCov.get(i, this.numAttributes + j));
			this.corrClasses.add(sumTmp / this.numClasses);
		}
	}
	
	@Override
	public double evaluateSubSet(ArrayList<Integer> subset)
	{	
		double merit = 0;
		
		return merit;
	}
	
	@Override
	public double evaluateFeature(int feature)
	{
		double result = 0, stdAttr = this.invCov.get(feature, feature);
				
		for (int i = 0; i < this.numAttributes; i++) 
		{
			if(i != feature)
				result += Double.MIN_VALUE + Math.abs(this.invCov.get(feature, i)) / (Math.sqrt(stdAttr * this.invCov.get(i, i)));
		}
		//Correlation with the class
		
		return -result;
	}
}
