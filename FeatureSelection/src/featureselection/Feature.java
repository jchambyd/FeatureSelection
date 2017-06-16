/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package featureselection;
import java.util.HashMap;
import org.ejml.simple.SimpleMatrix;

/**
 *
 * @author liac01
 */
public class Feature {
	
	private final HashMap<Double, Double> probMap;
	
	public Feature()
	{
		this.probMap = new HashMap();
	}

	public void mxCalculateProbabilities(SimpleMatrix dataVector)
	{
		int length = dataVector.numRows();

		//round input to integers

		HashMap<Double, Integer> countMap = new HashMap();

		for (int i = 0; i < length; i++) 
		{			
			Integer tmpValue = countMap.get(dataVector.get(i));
			if(tmpValue == null)
				countMap.put(dataVector.get(i), 1);
			else
				countMap.put(dataVector.get(i), tmpValue + 1);
		}
		
		countMap.entrySet().forEach((e) -> {
			probMap.put(e.getKey(), (double)e.getValue() / length);
		});
	}
}
