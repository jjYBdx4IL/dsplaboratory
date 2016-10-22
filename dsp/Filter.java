/*
 * Created on Nov 14, 2004
 *
 */
package dsp;

/**
 *
 * @author Luke
 */
public interface Filter
{
    public void start();
    public DataChunk process(DataChunk d);
    public void stop();
}
