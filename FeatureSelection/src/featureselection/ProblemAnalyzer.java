
package featureselection;

/**
 * This class is used to compute the required mathematical computations for the FCBF algorithm.
 * The constructor takes objects of types ResourceDescriptor and DataHolder and the functions
 * of the class operate on the data present in the two objects
 * 
 * @author Ravi Bhim,ASU.
 */
public class ProblemAnalyzer {
	
	ResourceDescriptor RD=null;
	DataHolder DH=null;
	
	/* Variables used by the 'fcbf' function */
	private double[] suList=null;
	private double[] suListDup = null;
	private int[] suOrder = null;
	private int[] valid = null;
	
	
	/**
	 * The constructor takes objects of types ResourceDescriptor and DataHolder pertaining to
	 * the feature selection problem.
	 * @param RD ResourceDescriptor object for the problem
	 * @param DH DataHolder object for the problem
	 */
	public ProblemAnalyzer(ResourceDescriptor RD, DataHolder DH)
	{
		this.RD=RD;
		this.DH=DH;
	}
	
	/**
	 * The fcbc function applies the FCBF algorithm and outputs the list of selcted features.
	 * @param delta Symmetrical Uncertainity threshold for filtering out features.
	 */
	public void fcbf(double delta)
	{
		/*
		 * STEP 1
		 * a) Initialize the 'sulist', i.e the SU values of all the features with respect
		 * 		to the class.
		 * b) Get the length of the valid attributes.
		 * c) Scan over 'suduplist' to get 'suorder', i.e indexes of features in descending 
		 * 		order of their SU values. 
		 */
		 
		 int numAttrs = RD.MD.size()-1;
		 int len=0;
		 
		 
		 /* Memory initialization */
		 suList = new double[numAttrs];
		 suListDup = new double[numAttrs];
		 
		 /* Initializing 'suList' */
		 for(int i=0;i<numAttrs;i++)
		 {
		 	/* Calculating Symmetrical Uncertainty with respect to the class */
		 	suList[i] = suListDup[i] = SU(i, numAttrs);	
		 	if(suList[i] > delta)
		 		len++;
		 }
		 
		 suOrder = new int[len];
		 valid = new int[len];
		 
		 double max;
		 int maxIndex;
		 for(int i=0; i<len; i++)
		 {
		 	max=0;
		 	maxIndex = -1;
		 	
		 	for(int j = 0; j < numAttrs; j++)
			{
				if(suListDup[j] > max)
				{
					max = suListDup[j];
					maxIndex=j;
				}
			}
		 	suOrder[i]=maxIndex;
		 	suListDup[maxIndex] =(double)0; /* Removing the max element in order to get the next 
		 										maximum element in the next iteration */
		 }
		 
		 /* Step 2: Heart of the FCBF algorithm */
		 
		 /* Initializing the valid array */
		 for(int i=0;i<len;i++)
		 	valid[i]=1;		/* All features are valid initially */
		 	
		 int fp,fq,fqd;
		 
		 fp = suOrder[0];	/* The feature with the highest SU value with respect to the class */
		 
		 while(fp != -1)
		 {
		 	fq = getNextElement(fp, len);
		 	
		 	if(fq!=-1)
		 	{
		 		while(true)
		 		{
		 			fqd=fq;
		 			if( SU(fp, fq) >= suList[fq] )
		 			{
		 				setInvalid(fq, len); // i.e valid[fq]=0
		 				fq = getNextElement(fqd,len);
		 			}
		 			else
		 				fq = getNextElement(fq,len);
		 				
		 			if(fq == -1)
		 				break;
		 		}
		 	}
		 	fp=getNextElement(fp,len);
		 }
		 
		 System.out.println("Printing the valid feature indexes(with their SU values with respect to the class");
		 for(int i=0;i<len;i++)
		 if(valid[i]!=0)
		 	System.out.println( ((MetaStructure)RD.MD.elementAt(suOrder[i])).name +" "+  suList[suOrder[i]] );
		 
		 System.out.println("\n\nDone.");
	} // end of fcbf()
	
	/**
	 * This function returns the next valid element(attribute index) in 'suOrder' after 'fp'
	 * @param fp
	 * @param len
	 * @return Returns the next valid attribute index, or -1 if there are none. 
	 */
	private int getNextElement(int fp, int len)
	{
		int fpIndex=0;
		
		for(int i=0;i<len;i++)
			if(suOrder[i]==fp)
			{
				fpIndex=i;
				break;
			}
			
		for(int i=fpIndex+1; i<len;i++)
			if(valid[i] == 1)
				return suOrder[i];
				
		/* No valid NextElement found */
		return -1;
	}
	
	/**
	 * Sets the memory location in 'valid' whose corresponding 'suOrder' value is 'fq' 
	 * @param fq
	 * @param len Length of the valid feature list
	 */
	private void setInvalid(int fq,int len)
	{
		for(int i=0;i<len;i++)
		if(suOrder[i] == fq)
		{
			valid[i]=0;
			return;
		}
	}
	
	
	/**
	 * This function returns the entropy of an attribute pointed by 'index' (ranges from 0 to 
	 * numAttrs+1) 
	 * @param index Index of the attribute
	 * @return Returns the entropy of the feature/attribute.
	 */
	double entropy(int attrIndex)
	{
		double ans=0,temp;
		
		MetaStructure curIndex = (MetaStructure)RD.MD.elementAt(attrIndex);
		
		for(short i=0; i < (short)curIndex.numValues;i++)
		{
			temp = partialProb(attrIndex,i);
			if(temp != (double) 0)
				ans+= temp *(Math.log(temp)/Math.log((double)2.0));
		}
		return -ans;
	}
	
	/**
	 * This function returns the partial probability value of the attribute(mentioned by 
	 * 'attrIndex' taking the value 'attrValue'
	 * @param attrIndex Index of the attribute
	 * @param attrValue Taking this particular value
	 * @return Partial Probability
	 */
	double partialProb(int attrIndex,short attrValue)
	{
		int count=0;
		
		for(int i = 0; i < DH.numInstances; i++)
			if(DH.data[i][attrIndex] == attrValue)
				count++;
				
		if(count!=0)
			return ((double)count/(double)DH.numInstances);
		else
		return (double)0;
	}
	
	/**
	 * This function computes the conditional entropy of the attribute One (mentioned by indexOne),
	 * given the attribute Two (mentioned by indexTwo)
	 * @param indexOne	Index of attribute One
	 * @param indexTwo	Index of attribute Two
	 * @return	Conditional Probability of One given Two
	 */
	double condEntropy(int indexOne,int indexTwo)
	{
		double ans=0,temp,temp_ans,cond_temp;
		
		MetaStructure oneMS=(MetaStructure)RD.MD.elementAt(indexOne);
		MetaStructure twoMS=(MetaStructure)RD.MD.elementAt(indexTwo);
		
		for(short j=0;j<(short)twoMS.numValues;j++)
		{
			temp = partialProb(indexTwo, j);
			temp_ans=0;
			
			for(short i=0;i<(short)oneMS.numValues;i++)
			{
				cond_temp=partialCondProb(indexOne,i,indexTwo,j);
				if(cond_temp != (double)0)
					temp_ans += cond_temp *(Math.log(cond_temp)/Math.log((double)2.0));
			}
			ans+=temp*temp_ans;
		}
		return -ans;
	}
	
	/**
	 * This function computes the probability of feature/attribute One(given by indexOne) taking
	 * value 'valueOne', given feature Two(given by indexTwo) taking value 'valueTwo'
	 * @param indexOne Index of feature One
	 * @param valueOne Value of feature One
	 * @param indexTwo Index of feature Two
	 * @param valueTwo Value of feature Two
	 * @return 
	 */
	double partialCondProb(int indexOne,short valueOne,int indexTwo,short valueTwo)
	{
		int num=0,den=0;
		
		for(int i=0;i<DH.numInstances;i++)
		{	
			if(DH.data[i][indexTwo] == valueTwo)
			{
				den++;
				if(DH.data[i][indexOne] == valueOne)
					num++;
			}
		}
		
		if(den!=0)
			return (double)num/(double)den;
		else
			return (double)0;
	}


	/**
	 * This function computes the information gain of feature 'indexOne' given feature 'indexTwo'
	 * 	IG(indexOne,indexTwo) => ENTROPY(indexOne) - condEntropy(indexOne,indexTwo)
	 * @param indexOne	feature One
	 * @param indexTwo	feature Two
	 * @return Returns the Information Gain
	 */
	double informationGain(int indexOne,int indexTwo)
	{
		return entropy(indexOne) - condEntropy(indexOne, indexTwo);
	}
	
	/**
	 * This function computes the Symmetrical Uncertainity of the features pointed by 'indexOne'
	 * and 'indexTwo'
	 * 
	 * @param indexOne Feature One
	 * @param indexTwo Feature Two
	 * @return Returns Symmetrical Uncertainty
	 */
	double SU(int indexOne, int indexTwo)
	{
		double ig,e1,e2,ans;
		
		ig = informationGain(indexOne, indexTwo);
		e1 = entropy(indexOne);
		e2 = entropy(indexTwo);
		
		if((e1+e2) !=(double)0)
			return((double)2 * (ig/(e1+e2)));
		else
		return (double)1;
	}


}
