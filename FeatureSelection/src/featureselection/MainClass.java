
package featureselection;

/**
 * This class is the entry point, which illustrates the usage of the FCBF algorithm. Two data 
 * samples are given in the 'test' folder. See the source file for the usage of the other classes.
 * It is important to confine to the data formats for the .rc and .data files.
 *  
 * @author Ravi Bhimavarapu,ASU
 */

public class MainClass {

	public static void main(String[] args) {
				
		String descriptor = new String("data/lc.rc");
		ResourceDescriptor RD = new ResourceDescriptor(descriptor);

		String holder = new String("data/lc.data");
		DataHolder DH = new DataHolder(holder);
		DH.convertDataFormat(RD);

		ProblemAnalyzer PA =new ProblemAnalyzer(RD,DH);
		PA.fcbf(0);	/* delta = 0 */				
	}
}
