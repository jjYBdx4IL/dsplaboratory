/*
 * Created on Feb 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsplaboratory.filters;

import dsplaboratory.DataChunk;
import dsplaboratory.Filter;
import dsplaboratory.VariableDataChunk;

/**
 * @author Irina
 */
public class FOBFourierFilter implements Filter {

	private int N;
	private double Ft1, Ft2, Fe;
	private double n[],h[];
	
	public double sinc(double x)
	{
		if (x != 0)
	        return Math.sin(x) / x;
		return 1;
	}
	
	public FOBFourierFilter(int FeValue, int Ft1Value, int Ft2Value, int NValue)
	{
		Fe = FeValue;
		Ft1 = Ft1Value;
		Ft2 = Ft2Value;
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
			h[i]= 2 * Ft2 / Fe * sinc(n[i] * 2 * Math.PI * Ft2 / Fe) - 2 * Ft1 / Fe * sinc(n[i] * 2 * Math.PI * Ft1 / Fe);
	}
	
	/* (non-Javadoc)
	 * @see dsp.Filter#start()
	 */
	public void start() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see dsp.Filter#process(dsp.DataChunk)
	 */
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
	
	/* (non-Javadoc)
	 * @see dsp.Filter#stop()
	 */
	public void stop() {
		// TODO Auto-generated method stub

	}

}
