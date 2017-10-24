package liac.sfs.loader;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToBinary;

public class DataLoader
{
	private DataLoader() {}
	
	/**
	 * Carrega dataset a partir de arquivo ARFF e binariza os atributos nominais.
	 * Assume que a classe seja o ultimo atributo.
	 * 
	 * @param filename path do arquivo
	 * @return dataset
	 * @throws DataLoaderException lancado quando o arquivo nao e encontrado
	 * ou quando ocorre algum erro de IO
	 */
	public static Dataset loadARFF(String filename) throws DataLoaderException
	{
		Dataset dataset = new Dataset();
		try
		{
			ArffLoader loader = new ArffLoader();

			loader.setSource(new File(filename));
			Instances data = loader.getDataSet();
			Instances m_Intances = new Instances(data);
			
			data.setClassIndex(data.numAttributes() - 1);
			
			String[] classes = new String[data.numClasses()];
			for(int i = 0; i < data.numClasses(); i++)
				classes[i] = data.classAttribute().value(i);
			dataset.setClassesNames(classes);
			
			NominalToBinary filter = new NominalToBinary();
			filter.setInputFormat(m_Intances);
			filter.setOptions(new String[] {"-A"});
			m_Intances = Filter.useFilter(m_Intances, filter);
			
			int inputSize = m_Intances.numAttributes() - data.numClasses();
			
			dataset.setInputSize(inputSize);
			dataset.setNumClasses(data.numClasses());

			dataset.setWekaDataset(m_Intances);
		}
		catch (IOException e)
		{
			throw new DataLoaderException("Arquivo não encontrado", e.getCause());
		}
		catch (Exception e)
		{
			throw new DataLoaderException("Falha na conversão do arquivo", e.getCause());
		}
		
		return dataset;
	}
}
