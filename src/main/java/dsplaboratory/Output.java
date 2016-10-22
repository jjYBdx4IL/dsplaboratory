/*
 * Created on Nov 14, 2004
 *
 */
package dsplaboratory;

/**
 *
 * @author Luke
 */
public interface Output
{
    public void putNext(DataChunk d);
    public void start();
    public void stop();
}
