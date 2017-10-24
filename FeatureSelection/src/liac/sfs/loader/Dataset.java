package liac.sfs.loader;

import org.ejml.simple.SimpleMatrix;
import weka.core.Instances;

public class Dataset
{
	private Instances wekaDataset;
	private SimpleMatrix dataset;
	private int inputSize;
	private int numClasses;
	private String[] classesNames;
	
	public Instances getWekaDataset()
	{
		return wekaDataset;
	}
	
	public void setWekaDataset(Instances wekaDataset)
	{
		this.wekaDataset = wekaDataset;
		this.dataset = MatrixUtil.instancesToMatrix(wekaDataset);
	}
	
	public SimpleMatrix getDataset()
	{
		return dataset;
	}

	public void setDataset(SimpleMatrix dataset)
	{
		this.dataset = dataset;
	}

	public int getInputSize()
	{
		return inputSize;
	}

	public void setInputSize(int inputSize)
	{
		this.inputSize = inputSize;
	}

	public int getNumClasses()
	{
		return numClasses;
	}

	public void setNumClasses(int numClasses)
	{
		this.numClasses = numClasses;
	}
	
	public SimpleMatrix getDataRange()
	{
		SimpleMatrix min = new SimpleMatrix(dataset.numRows(), 1);
		min.set(Double.POSITIVE_INFINITY);
		SimpleMatrix max = new SimpleMatrix(dataset.numRows(), 1);
		max.set(Double.NEGATIVE_INFINITY);
		
		for(int i = 0; i < dataset.numCols(); i++)
		{
			for(int j = 0; j < dataset.numRows(); j++)
			{
				double value =	dataset.get(j, i);
				if(value < min.get(j, 0))
					min.set(j, 0, value);
				if(value > max.get(j, 0)) 
					max.set(j, 0, value);
			}
		}
		
		return max.minus(min);
	}
	
	public int size()
	{
		return wekaDataset.numInstances();
	}
	
	public int getNumAttributes()
	{
		return wekaDataset.numAttributes();
	}
	
	public void setClassesNames(String[] classesNames)
	{
		this.classesNames = classesNames;
	}
	
	public String[] getClassesNames()
	{
		return classesNames;
	}
}
