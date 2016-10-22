/*
 * Created on Jan 14, 2005
 *
 */
package dsp.basicinput.inputs;

import dsp.Input;
import dsp.SingleDataChunk;

/**
 * @author Flaviu
 *
 */
abstract class AbstractSignal implements Input
{
    protected class SimpleDataChunk extends SingleDataChunk
    {
        public SimpleDataChunk()
        {
            super( 0);
        }
        
        public void setTo( double data)
        {
            this.data = data;
        }
    }
    
    protected SimpleDataChunk data;
    
    protected double samplingFreq;
    protected int	 samples;       // max number of samples
    protected int	 counter;       // current number of the generated samples
    
    public AbstractSignal( double samplingFreq, int samples)
    {
        this.samplingFreq = samplingFreq;
        this.samples	  = samples;
        data = new SimpleDataChunk();
    }

	public boolean endOfInput()
	{
		return counter >= samples;
	}
	
	public void start()
    {
	    counter = 0;
    }	
    
	public void stop()
    {	    
    }
	
	public int getFrequency()
    {
		return ( int) samplingFreq;
    }	
}
