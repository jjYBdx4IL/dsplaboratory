/*
 * Created on Jan 12, 2005
 *
 */

package dsp.basicinput.inputs;

import dsp.DataChunk;

/**
 * @author Flaviu
 *  
 */

class SignalValueArray implements DataChunk    
{
    double[] values;
    final int count;
      
	public SignalValueArray( int count)
	{        
	    this.count = count;
	    values = new double[ count];          
	}
      
	public int getSize()
	{
	    return count; 
	}

	public double getElement( int index)
	{
	    return values[ index]; 
	}
   
	public void setElement( int index, double value)
	{
	    values[ index] = value; 
	}       
}
