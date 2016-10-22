/*
 * Created on Jan 12, 2005
 *
 */
package dsp.basicinput.inputs;

import dsp.DataChunk;
import dsp.exception.RecoverableInputProblem;
import dsp.basicinput.PatternSignalData;

/**
 * @author Flaviu
 *
 */

public class PatternSignal extends AbstractSignal
{
    protected PatternSignalData signalData;
    protected double 			samplingPeriod;
    protected double 			crtTime;
    
    public PatternSignal( PatternSignalData signalData, double samplingFreq, int samples)
    { 
        super( samplingFreq, samples);
        this.signalData = signalData;
        samplingPeriod = 1 / samplingFreq;
    }
    
    public void start()
    {
        counter = 0;
        crtTime = 0;
    }
    
    public DataChunk getNext() throws RecoverableInputProblem
    {
        // if ( endOfInput()) return null;        
        data.setTo( signalData.getValue( crtTime));
        ++counter;                
        crtTime += samplingPeriod;        
        return data;
    }
}
