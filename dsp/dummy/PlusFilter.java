/*
 * Created on Nov 15, 2004
 *
 */
package dsp.dummy;

import dsp.*;
import dsp.DataChunk;
import dsp.Filter;

/**
 * 
 * @author Luke
 */
public class PlusFilter implements Filter
{
    double delta;

    public PlusFilter(double d)
    {
        delta = d;
    }

    public void start()
    {
        // do nothing
    }

    public DataChunk process(DataChunk d)
    {
            double[] tabel = new double[d.getSize()];
            for (int i = 0; i < d.getSize(); i++)
                tabel[i] = d.getElement(i) + delta;
            return new VariableDataChunk(tabel);
    }

    public void stop()
    {
        // do nothing
    }

}