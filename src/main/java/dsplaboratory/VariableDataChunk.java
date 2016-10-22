/*
 * Created on Nov 14, 2004
 *
 */
package dsplaboratory;

/**
 *
 * @author Luke
 */
public class VariableDataChunk implements DataChunk
{
    protected double[] data;
    
    public VariableDataChunk(double[] inData)
    {
        data = new double[inData.length];
        System.arraycopy(inData, 0, data, 0, inData.length);
    }
    
    public int getSize()
    {
        return data.length; 
    }
    
    public double getElement(int index)
    {
        return data[index];
    }
}
