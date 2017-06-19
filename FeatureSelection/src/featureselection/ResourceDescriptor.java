
package featureselection;


import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * This class is used to store the contents of the 	'stem'.rc file
 * 
 * @author Ravi.Bhim, ASU.
 */
public class ResourceDescriptor {
	String fileName=null;
	int numClasses;		/* Number of classes */
	Vector classLabels=null;	/* Class Labels */
	int numAttr;		/* Number of attributes */
	Vector MD=null; /* MD -- Metadata about the attributes */

	/**
 	* The constructor takes the filename of the 'stem'.rc file
 	* as its arguement. 
 	* @param filename Name of the 'stem'.rc file
 	* 
 	*/	
	public ResourceDescriptor(String fileName)
	{
		this.fileName=fileName;
		MD = new Vector();
		readResourceFile();
		
		addClassInfo();
	}
	
	/**
	 * This routine reads from the resource file and store
	 * them.
	 */
	private void readResourceFile()
	{
		try{
			BufferedReader in= new BufferedReader(new FileReader(fileName));
			String classInfo = in.readLine(); 
			
			/* Removing the period at the end of the line */
			classInfo=classInfo.substring(0,classInfo.length()-1);
			StringTokenizer tokens= new StringTokenizer(classInfo,",");
			numClasses = Integer.parseInt(tokens.nextToken());
			
			/* Initializing and storing the Vector 'classLabels' */
			classLabels = new Vector(numClasses);
			for(int i=0;i<numClasses; i++)
				classLabels.add(tokens.nextToken());
			
			/** DEBUG
			for(int i=0;i<numClasses;i++)
				System.out.println("Class:" + (String)classLabels.elementAt(i));
				*/
			/* Scanning the number of attributes */
			numAttr = Integer.parseInt(in.readLine());
			
			/* Scanning the attribute information */
			String attrLine;
			int dash;	/* For storing the position of the delimiter '-' */
			String attrName,values;
			Vector attrValues=null;
			int numVals;	/* Number of values a attribute can take */
			StringTokenizer valueTokens=null;
			while((attrLine=in.readLine())!=null)
			{
				dash=attrLine.indexOf(" - ");
				attrName=attrLine.substring(0,dash);
				values=attrLine.substring(dash+3,attrLine.length()-1);
				valueTokens=new StringTokenizer(values,",");
				numVals = valueTokens.countTokens();
				attrValues = new Vector(valueTokens.countTokens());
				
				while(valueTokens.hasMoreTokens())
					attrValues.addElement(valueTokens.nextToken());
				
				//System.out.println(attrName + attrValues );
				
				MD.add(new MetaStructure(attrName,numVals,attrValues));	
			}
			
			/* Closing the file handle on the 'stem'.rc file */
			in.close();
			
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
		
	}
	
	/**
	 * This function outputs the metadata information. i,e. the contents of the
	 * vector MD
	 */
	public void printMetaData()
	{
		System.out.println("Printing METADATA information(MD)");
		System.out.println("Number of Attributes");
		for(int i=0;i<MD.size();i++)
			((MetaStructure)MD.elementAt(i)).printMetaStructure();
		System.out.println("******************************");
	}
	
	/**
	 * This function adds the class information to the metadata(MD) at the end. 
	 * This way the metadata represenation would be consistent with that of the 
	 * actual data representation.
	 */
	public void addClassInfo()
	{
		MetaStructure classob = new MetaStructure("CLASS",numClasses,classLabels);
		MD.add(classob);
	}
	
	

}
