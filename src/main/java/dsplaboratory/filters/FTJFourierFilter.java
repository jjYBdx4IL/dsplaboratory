/*
 * Created on Jan 30, 2005
 *
 */
package dsplaboratory.filters;

import dsplaboratory.DataChunk;
import dsplaboratory.Filter;
import dsplaboratory.SingleDataChunk;
import dsplaboratory.VariableDataChunk;

import java.math.*;

/**
 * @author Irina
 */
public class FTJFourierFilter implements Filter {

	private int N;
	private double Ft, Fe, Ft1, Ft2;//trebuie luati dintr-un text box
	private double n[],h[];
	
	public double sinc(double x)
	{
		if (x != 0)
	        return Math.sin(x) / x;
		return 1;
	}
	
	public FTJFourierFilter(int FeValue, int FtValue, int NValue)
	{
		Fe = FeValue;
		Ft = FtValue;
		N = NValue;
		
	    n = new double[N];
	    h = new double[N];
				
		double k = - (N - 1) / 2.0;
		for (int i = 0; i < N; i++)
		{
			n[i] = k;
			k++;
		}
		
		for (int i = 0; i < N; i++)
			h[i] = 2 * Ft / Fe * sinc(n[i] * 2 *  Math.PI * Ft / Fe);
	}
	
	public void start() 
	{
		// do nothing
	}

	public DataChunk process(DataChunk d) 
	{

		int i, k;
		double[] x = new double[d.getSize()];
        double[] y = new double[d.getSize()];
       
		for (i = 0; i < d.getSize(); i++)
		{	
			y[i] = 0;
		    x[i] = d.getElement(i);
		}
		/*
		*trebuie tratat cazul in care N > d.getSize()
		*/
        for (i = 0; i < d.getSize(); i++)
        {
        	for (k = 0; k < N; k++)
            {
        		if((i - k) < 0)
                {
        			y[i] += 0;
                }
                else
                {
                	y[i] += h[k] * x[i - k];
                }

             }
        }
        
        return new VariableDataChunk(y);
	}

	/* 
	 * 
	 */
	public void stop() {
		// TODO Auto-generated method stub

	}
	

}
