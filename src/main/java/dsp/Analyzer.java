/*
 * Created on Nov 14, 2004
 *
 */
package dsp;

/**
 *
 * @author Luke
 */
public interface Analyzer
{
    public void start();
    public void putNext(DataChunk d);
    public void stop();
    
}
