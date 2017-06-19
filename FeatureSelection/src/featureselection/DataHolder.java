
package featureselection;

import java.util.StringTokenizer;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


/**
 * This class is used to store the data(instances).
 * Initially, the data is stored as a Vector of Vectors(of Strings). 
 * Later, the data is converted into a two dimensional array of primitive types, for 
 * faster manipulation.
 * 
 * @author Ravi Bhim, ASU. 
 */

public class DataHolder {
	
	String fileName=null;
	Vector dataInstances =null; 	/* Vector of Vectors(of Strings) */
	int numInstances =0 ;			/* Number of Data Instances */
	short data[][] = null;			/* 2D representation of the dataInstances */
	
	
	public DataHolder(String fileName)
	{
		this.fileName = fileName;
		dataInstances = new Vector();
		readDataFile();
	}
	
	/**
	 * This function reads the data, stored in 'fileName' and stores them in 'dataInstances'
	 */
	private void readDataFile()
	{
		try{
				BufferedReader in = new BufferedReader(new FileReader(fileName));
				String instance;	/* Used to store each instance in the data file */
			
				while((instance=in.readLine())!=null)
				{
					StringTokenizer tokens =new StringTokenizer(instance,",");
				
					/* Check on the number of tokens */
					int numTokens=tokens.countTokens();
				
					if(numTokens==0)	/* Newlines or lines with no instances */
						continue;
					/* NOTE: Additional checks whether the data indeed confirms to its metadata
				 	* as specified in its ResourceDescriptor object is done at a later stage
				 	* when the data is converted into a 2-dimensional arrays of short integers.
				 	*/	
				 
				 	Vector newInstance= new Vector(numTokens);
				 	while(tokens.hasMoreTokens())
				 		newInstance.add(tokens.nextToken());
				 	
				 	/* Adding the new instance to 'dataInstances' */
				 	dataInstances.add(newInstance);

				}
				
				this.numInstances = dataInstances.size();
				
	
			}
		catch(FileNotFoundException e)
		{
			System.out.println("Resource file "+fileName+" not found.Exiting..");
			System.exit(1);
		}
		catch(Exception e)
		{
			System.out.println("Exception caught in readSourceFile :"+e);
			System.out.println("Format of "+fileName+ " might be inconsistent. Exiting...");
			System.exit(1);
		}
	}	// end of readDataFile
	
	
	/**
	* This function prints 'dataInstances'. Used for debugging purposes.
	* NOTE: This function must be called only after convertDataFormat() is called, because otherwise
	* 2D array 'data' would not have been initialized.
	*/	
	void printData()
	{
		System.out.println("Printing 'dataInstances'");
		
		for(int i=0;i<dataInstances.size();i++)
		{
			Vector curInstance = (Vector)dataInstances.elementAt(i);
			for(int j=0;j<curInstance.size();j++)
				System.out.print((String)curInstance.elementAt(j)+" ");
			System.out.println();
		}
		
		System.out.println("\nPrinting 'data'");
		for(int i=0;i<numInstances;i++)
		{
			for(int j=0;j<data[i].length;j++)
			System.out.print(data[i][j]+" ");
		System.out.println();
		}
		
		System.out.println("*************************");
	}
	
	/**
	 * This function converts the data in 'dataInstances' to a 2 dimensional array of short
	 * integers. Prior to conversion, some checks are perfomed on the consistency/validity of
	 * the data.
	 */
	public void convertDataFormat(ResourceDescriptor RD)
	{
		/* Checks */
		
		/* Check 1 -- See if the number of strings in each dataInstance is equal to RD.numAttr +1 */
		for(int i=0; i<dataInstances.size();i++)
		{
			Vector curInstance = (Vector)dataInstances.elementAt(i);
			
			if(curInstance.size() != (RD.numAttr+1))
			{
				System.out.println("Problem with the instance "+curInstance);
				System.out.println("Exiting..");
				System.exit(1);		
			}
		}
		
		System.out.println("Check 1 passed.\n");
		
		/* Conversion of the data to the 2D array format */
		data = new short[numInstances][RD.numAttr+1];
		
		for(int i=0;i<dataInstances.size();i++)
		{
			Vector curInst = (Vector)dataInstances.elementAt(i);	/* Current Instance */
			
			int index;
			for(int j=0;j<curInst.size();j++)
			{
				index=getIndex(RD.MD,j,(String)curInst.elementAt(j));
				if(index==-1)	/* Error */
				{
					String attrName = ((MetaStructure)(RD.MD.elementAt(j))).name;
					System.out.println("\nAttribute "+attrName+ " does not take the value "+(String)curInst.elementAt(j));
					System.out.println("Exiting...");
					System.exit(1);
				}
				//System.out.print(index+" ");
				data[i][j]=(short)index;
			}
			//System.out.println();
		}
	}
	
	/**
	 * This function returns the index of the attribute value 'value' as stored in the 
	 * index 'index' in the vector of MetaStructures 'MD'
	 * @param MD Vector of MetaStructures
	 * @param index Index in MD of the attribute
	 * @param value Attribute value(the label of the attribute)
	 * @return
	 */
	private int getIndex(Vector MD,int index,String value)
	{
		Vector curLabels =(Vector) ((MetaStructure)(MD.elementAt(index))).valueLabels;
		
		for(int i=0;i<curLabels.size();i++)
			if(value.equals((String)curLabels.elementAt(i)))	/* Label found */
				return i;
				
		return -1;	/* Error code in case the label 'value' is not found */
	}
	

}
