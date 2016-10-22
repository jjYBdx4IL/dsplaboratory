/*
 * Created on Nov 14, 2004
 *
 */
package dsplaboratory.dummy;

import dsplaboratory.SingleDataChunk;
import dsplaboratory.DataChunk;
import dsplaboratory.Input;

/**
 *
 * @author Luke
 */
public class ConstInput implements Input
{
    protected final double value;
    protected final int times;
    protected int count;
    
    public ConstInput(double val, int samples)
    {
        value = val;
        times = samples;
    }

    public boolean endOfInput()
    {
        return count == times;
    }

    public void start()
    {
        count = 0;
    }

    public DataChunk getNext()
    {
        count++;
        return new SingleDataChunk(value);
    }

    public void stop()
    {
        // do nothing
    }

    /**
     * 1 Hz
     * @see dsp.Input#getFrequency()
     */
    public int getFrequency()
    {
        return 1;
    }

}
