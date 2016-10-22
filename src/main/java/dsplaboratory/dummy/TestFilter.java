/*
 * Created by canti, 23.11.2004
 *
 */
package dsplaboratory.dummy;

import dsplaboratory.DataChunk;
import dsplaboratory.Filter;
import dsplaboratory.SingleDataChunk;
import dsplaboratory.VariableDataChunk;

/**
 * @author canti, 23.11.2004
 *
 * Implementeaza un filtu de mediere banal
 */
public class TestFilter implements Filter
{
    private SingleDataChunk oldValue;
    /**
     * 
     */
    public TestFilter()
    {
        super();
        
        // implementam tehnica zero padding 
        oldValue = new SingleDataChunk(0);
    }

    /* 
     * Overriden
     * 
     */
    public void start()
    {
        // do nothing
    }

    /* 
     * Overriden
     * 
     */
    public DataChunk process(DataChunk d)
    {
        double[] tabel = new double[d.getSize()];
        for (int i = 0; i < d.getSize(); i++)
        {
            tabel[i] = (d.getElement(i) + oldValue.getElement(0))/10;
            oldValue = new SingleDataChunk(d.getElement(i));
        }
        return new VariableDataChunk(tabel);
    }

    /* 
     * Overriden
     * 
     */
    public void stop()
    {
            // do nothing
    }
    
    public String toString()
    {
        return "Filtru de mediere";
    }
}
