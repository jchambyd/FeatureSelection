/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liac.sfs.loader;

import org.ejml.simple.SimpleMatrix;

import weka.core.Instances;

public class MatrixUtil
{
	private MatrixUtil() {}
	
	/**
	 * 
	 * @param x matrix de entrada
	 * @return indice do maior elemento da matriz x
	 */
	public static int maxElementIndex(SimpleMatrix x)
	{
		double data[] = x.getMatrix().getData();
		double max = data[0];
		int idx = 0;
		for(int i = 1; i < data.length; i++)
		{
			if(data[i] > max)
			{
				max = data[i];
				idx = i;
			}
		}
		
		return idx;
	}
	
	/**
	 * 
	 * @param data instancias carregadas pelo weka
	 * @return matriz correspondentes as instancias 
	 */
	public static SimpleMatrix instancesToMatrix(Instances data)
	{
		double dataset[][] = new double[data.numAttributes()][data.numInstances()];
		for(int i = 0; i < data.numInstances(); i++)
		{
			double[] B = data.instance(i).toDoubleArray();
			for(int j = 0; j < data.numAttributes(); j++)
				dataset[j][i] = B[j]; 
		}
		
		return new SimpleMatrix(dataset);
	}
	
	/**
	 * Remove um elemento da matriz
	 * 
	 * @param m matriz de entrada
	 * @param idx indice do elemento a ser removido
	 * @return nova matriz com o elemento removido
	 */
	public static SimpleMatrix removeElement(SimpleMatrix m, int idx)
	{
		double data[] = m.getMatrix().getData();
		double[] newData = new double[data.length - 1];
		System.arraycopy(data, 0, newData, 0, idx);
		System.arraycopy(data, idx + 1, newData, idx, data.length - idx - 1);
		m.getMatrix().setData(newData);
		m.getMatrix().reshape(data.length - 1, 1, true);
		return m;
	}
	
	/**
	 * Cria matriz diagonal com os elementos da matriz de entrada
	 * 
	 * @param m matriz de entrada
	 * @return matriz diagonal
	 */
	public static SimpleMatrix diag(SimpleMatrix m)
	{
		SimpleMatrix diag = new SimpleMatrix(m.getNumElements(), m.getNumElements());
		for (int l = 0; l < m.getNumElements(); l++)
			diag.set(l, l, m.get(l));
		
		return diag;
	}
	
	/**
	 * Testa se duas matrizes sao iguais
	 * 
	 * @param A matriz de entrada
	 * @param B matriz de entrada
	 * @return <true> se as matrizes A e B sao iguais,
	 * <false> caso contrario
	 */
	public static boolean equals(SimpleMatrix A, SimpleMatrix B)
	{
		if (A == null || B == null)
			return false;
		
		double[] a = A.getMatrix().getData();
		double[] b = B.getMatrix().getData();
		
		if(a.length != b.length)
			return false;
		
		for(int i = 0; i < a.length; i++)
			if(a[i] != b[i])
				return false;
		
		return true;
	}
	
	public static double[][] toDouble(SimpleMatrix m)
	{
		double data[][] = new double[m.numRows()][m.numCols()];
		for(int i = 0; i < m.numRows(); i++)
			for(int j = 0; j < m.numCols(); j++)
				data[i][j] = m.get(i, j);

		return data;	
	}
	
	public static SimpleMatrix getCorrelationMatrix(SimpleMatrix cov)
	{
		int numAtrib = cov.numCols();
		SimpleMatrix invDiag = new SimpleMatrix(numAtrib, numAtrib);
		
		//Calculating inverse the diagonal matrix of covariance matrix (sqrt)
		for (int i = 0; i < numAtrib; i++) 
			invDiag.set(i, i, 1.0 / Math.sqrt(Math.abs(Double.MIN_VALUE + cov.get(i, i))));
		
		//Calculating correlation matrix
		SimpleMatrix corrMat = invDiag.mult(cov).mult(invDiag);
		
		return corrMat;
	}
}
