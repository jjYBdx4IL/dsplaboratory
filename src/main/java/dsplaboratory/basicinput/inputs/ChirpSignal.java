/*
 * Created on Jan 12, 2005
 *
 */
package dsplaboratory.basicinput.inputs;

import dsplaboratory.DataChunk;
import dsplaboratory.exception.RecoverableInputProblem;

/**
 * @author Flaviu
 *
 */

public class ChirpSignal extends AbstractSignal
{    
    protected double factor;
    protected double mean;
    protected double amplit;
    protected double freq;
    protected double targetFreq;
    protected double phaseOffset;
    protected double targetTime;
    protected double freqIncrement;
    protected int	 incLimit;
    
    protected final double originalFreq;    
    
    public ChirpSignal( double mean, double amplit, double initialFreq,
            			double targetFreq, double phaseOffset, double targetTime,
            			double samplingFreq, int samples)
    {
        super( samplingFreq, samples);        
        this.mean         = mean;
        this.amplit   	  = amplit;
        this.freq         = initialFreq;
        this.targetFreq   = targetFreq;
        this.phaseOffset  = phaseOffset;
        this.targetTime	  = targetTime;
        this.originalFreq = initialFreq;
        
        factor = 2 * Math.PI / samplingFreq;
        
        final double samplesBeforeTarget = targetTime * samplingFreq;
        
        incLimit = ( int) samplesBeforeTarget;
        
        freqIncrement = ( targetFreq - initialFreq) / samplesBeforeTarget;
    }
    
    public void start()
    {
        counter = 0;
    }   
    
    public void stop()
    {
        freq = originalFreq;        
    }    
    
	public DataChunk getNext() throws RecoverableInputProblem
	{	
	    // if ( endOfInput()) return null;	            
	    data.setTo( mean + amplit * Math.sin( factor * freq * counter + phaseOffset));
        if ( counter < incLimit) freq += freqIncrement;	    
	    ++counter;
		return data;
	}
}
