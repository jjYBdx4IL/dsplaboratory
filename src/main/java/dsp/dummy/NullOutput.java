/*
 * Created on Nov 23, 2004
 *
 */
package dsp.dummy;

import dsp.DataChunk;
import dsp.Output;

/**
 *
 * @author Luke
 */
public class NullOutput implements Output
{

    public NullOutput()
    {
        // do nothing
    }

    public void putNext(DataChunk d)
    {
        // do nothing
    }

    public void start()
    {
        // do nothing
    }

    public void stop()
    {
        // do nothing
    }

}
