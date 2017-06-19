
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
		
				if(args.length < 1 )
				{
					System.out.println("Usage: java featureselector.fcbf.MainClass 'stem' <delta> ");
					System.exit(1);
				}
				
				String descriptor = new String(args[0]+".rc");
				ResourceDescriptor RD = new ResourceDescriptor(descriptor);
		
				String holder = new String(args[0] + ".data");
				DataHolder DH = new DataHolder(holder);
				DH.convertDataFormat(RD);
				
				ProblemAnalyzer PA =new ProblemAnalyzer(RD,DH);
				if(args.length == 2)
					PA.fcbf(Double.parseDouble(args[1]));
				else
				if(args.length == 1)				
					PA.fcbf(0);	/* delta = 0 */
				else
					System.out.println("Error in usage.");
				
	}
}
