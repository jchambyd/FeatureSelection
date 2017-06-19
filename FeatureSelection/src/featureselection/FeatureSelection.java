/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package featureselection;

/**
 *
 * @author liac01
 */
public class FeatureSelection {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
	}
	
	private void mxLoadDataset()
	{
		try
		{
			Dataset dataset = DataLoader.loadARFF("data/iris.arff");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
}
