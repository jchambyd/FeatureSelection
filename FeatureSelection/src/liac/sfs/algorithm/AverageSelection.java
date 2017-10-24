/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liac.sfs.algorithm;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author liac01
 */
public class AverageSelection extends AlgorithmSelection{
	
	/**
	 * Construct for forward selecton algorithm
	 * @param numFeatures
	 * @param costFunction
	 */
	public AverageSelection(int numFeatures, CostFunction costFunction)
	{
		this.numFeatures = numFeatures;
		this.selectFeatures = new boolean[this.numFeatures];
		this.subSetFeatures = new ArrayList();
		this.costFunction = costFunction;
	}
		
	@Override
	public ArrayList<Integer> algorithm(int numBestFeatures)
	{
		double meritFeatures[] = new double [this.numFeatures];
		Integer indices [] = new Integer[this.numFeatures];
		
		//Calculate merit for each feature
		for (int i = 0; i < this.numFeatures; i++) 
		{
			meritFeatures[i] = this.costFunction.evaluateFeature(i);
			indices[i] = i;
		}
		
		//Sorting merit of features
		Arrays.sort(indices, (final Integer o1, final Integer o2) -> Double.compare(meritFeatures[o2], meritFeatures[o1]));
		//Get only "numBestFeatures" best features
		for (int i = 0; i < numBestFeatures; i++) 
			this.subSetFeatures.add(indices[i]);
		
		return this.subSetFeatures;
	}
	
	@Override
	public ArrayList<Integer> algorithm(double threshold)
	{
		double meritFeatures[] = new double [this.numFeatures];
		Integer indices [] = new Integer[this.numFeatures];
		double average = 0.0;
		
		//Calculate merit for each feature
		for (int i = 0; i < this.numFeatures; i++) 
		{
			meritFeatures[i] = this.costFunction.evaluateFeature(i);
			indices[i] = i;
			average += meritFeatures[i] / this.numFeatures;
		}
		
		//Sorting merit of features
		Arrays.sort(indices, (final Integer o1, final Integer o2) -> Double.compare(meritFeatures[o2], meritFeatures[o1]));
		
		//Get only features with merit > to average
		for (int i = 0; i < this.numFeatures; i++)
		{
			if(meritFeatures[indices[i]] < average * threshold)
				break;
			this.subSetFeatures.add(indices[i]);
		}
		
		return this.subSetFeatures;
	}
	
	@Override
	public ArrayList<Integer> algorithm()
	{
		double meritFeatures[] = new double [this.numFeatures];
		Integer indices [] = new Integer[this.numFeatures];
		double average = 0.0;
		
		//Calculate merit for each feature
		for (int i = 0; i < this.numFeatures; i++) 
		{
			meritFeatures[i] = this.costFunction.evaluateFeature(i);
			indices[i] = i;
			average += meritFeatures[i] / this.numFeatures;
		}
		
		//Sorting merit of features
		Arrays.sort(indices, (final Integer o1, final Integer o2) -> Double.compare(meritFeatures[o2], meritFeatures[o1]));
		
		//Get only features with merit > to average
		for (int i = 0; i < this.numFeatures; i++) 
		{
			if(meritFeatures[indices[i]] < average)
				break;
			this.subSetFeatures.add(indices[i]);
		}
		
		return this.subSetFeatures;
	}
}
