
package featureselection;

import java.util.Vector;
/**
 * This class is used to store the metadata about the attributes and their values.
 * 
 * @author Ravi.Bhim, ASU
 *
 */
public class MetaStructure {
	
	/**  Name of the attribute */
	public String name;			
	/** Number of values this attribute can take */
	public int numValues;
	/** Labels of the different values */		
	public Vector valueLabels;	

	/**
	 * The constructor replicates the arguements into its identifiers.
	 * @param name Name of the attribute
	 * @param numValues Number of values the attribute can take
	 * @param valueLabels Labels of the different values
	 */
	public MetaStructure(String name,int numValues,Vector valueLabels)
	{
		this.name = new String(name);
		this.numValues = numValues;
		
		/* Copying the contents of the vector */
		this.valueLabels = new Vector(valueLabels.size());
		for(int i=0;i<valueLabels.size();i++)
			this.valueLabels.add(new String((String)valueLabels.elementAt(i)));
	}
	
	/**
	 * This function outputs the content of the MetaStructure object. Used primarily for debugging purposes.
	 */
	public void printMetaStructure()
	{
		System.out.println("Attr Name: "+name);
		System.out.print("Values: ");
		for(int i=0;i<valueLabels.size();i++)
			System.out.print((String)valueLabels.elementAt(i)+" ");
		System.out.println();
	}
}
