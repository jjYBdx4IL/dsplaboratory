/*
 * Created on Nov 14, 2004
 *
 */
package dsp.dummy;

import dsp.DataChunk;
import dsp.Filter;

/**
 *
 * @author Luke
 */
public class NullFilter implements Filter
{

    public void start()
    {
        // do nothing
    }

    public DataChunk process(DataChunk d)
    {
        // do nothing
        // atentie ! trebuie returnat un alt obiect DataChunk,
        // nu cel primit ca parametru !
        return d;
    }

    public void stop()
    {
        //do nothing
    }

}
