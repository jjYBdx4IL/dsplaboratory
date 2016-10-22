/*
 * Created on Nov 14, 2004
 *
 */
package dsplaboratory.dummy;

import dsplaboratory.DataChunk;
import dsplaboratory.Output;

/**
 *
 * @author Luke
 */
public class ConsoleOutput implements Output
{

    public void putNext(DataChunk d)
    {
        System.out.print("chunk: ");
        for(int i = 0; i < d.getSize(); i++)
            System.out.print("" + d.getElement(i) + " ");
        System.out.println();
    }

    public void start()
    {
        System.out.println("Starting Output");
    }

    public void stop()
    {
        System.out.println("Finished Output");
    }

}
