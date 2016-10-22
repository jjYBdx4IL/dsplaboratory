/*
 * Created on Nov 14, 2004
 *
 */
package dsplaboratory;

/**
 *
 * @author Luke
 */
public class SingleDataChunk implements DataChunk
{
    protected double data;

    public SingleDataChunk(double d)
    {
        data = d;
    }
    public int getSize()
    {
        return 1;
    }

    public double getElement(int index)
    {
        return data;
    }

}
