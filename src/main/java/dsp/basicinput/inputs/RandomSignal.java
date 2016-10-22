/*
 * Created on Jan 12, 2005
 *
 */
package dsp.basicinput.inputs;

import dsp.DataChunk;
import dsp.exception.RecoverableInputProblem;

/**
 * @author Flaviu
 *
 */

public class RandomSignal extends AbstractSignal
{       
    protected final double amplitude;
    protected final double base;        
    
    public RandomSignal( double mean, double amplitude, 
            			 double samplingFreq, int samples)
    {
        super( samplingFreq, samples);       
        base = mean - amplitude / 2;
        this.amplitude = amplitude;                              
    }
    
	public DataChunk getNext() throws RecoverableInputProblem
	{		
	    // if ( endOfInput()) return null;
	    data.setTo( base + amplitude * Math.random());
	    ++counter;
	    return data;
	}
}
