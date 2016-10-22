/*
 * Created on Nov 14, 2004
 *
 */
package dsp.dummy;

import dsp.*;
import dsp.DataChunk;
import dsp.Input;

/**
 *
 * @author Luke
 */
public class DummyInput implements Input
{
    protected int count;
    protected int maxCount, amplitude, sinFreq, sampleFreq;
    
    public DummyInput(int ampl, int sinF, int max, int sampF)
    {
        amplitude = ampl;
        maxCount = max;
        sinFreq = sinF;
        sampleFreq = sampF;
    }
    
    public boolean endOfInput()
    {
        return count == maxCount;
    }

    public void start()
    {
        count = 0;
    }

    public DataChunk getNext()
    {
        count++;
        return new SingleDataChunk(amplitude * Math.sin( 2 * Math.PI * count*sinFreq/sampleFreq));
    }

    public void stop()
    {
        // do nothing
    }

    /**
     * Hz
     * @see dsp.Input#getFrequency()
     */
    public int getFrequency()
    {
        return sampleFreq;
    }
    
}
