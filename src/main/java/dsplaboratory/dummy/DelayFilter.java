/*
 * Created on Feb 4, 2005
 *
 */
package dsplaboratory.dummy;

import java.util.LinkedList;
import java.util.List;

import dsplaboratory.DataChunk;
import dsplaboratory.Filter;
import dsplaboratory.VariableDataChunk;

/**
 *
 * @author Luke
 */
public class DelayFilter implements Filter
{
    private List<DataChunk> queue;
    private int queueSize;
    private final int delaySize; 

    public DelayFilter(int frequency)
    {
        // ar trebui sa rezulte 3 secunde
        delaySize = 3 * frequency;
    }
    
    public void start()
    {
        queue = new LinkedList<>();
        queueSize = 0;
        System.out.println("Delay Size : " + delaySize);
    }

    public DataChunk process(DataChunk d)
    {
        
        if (queueSize < delaySize)
        {
            queue.add(d);
            queueSize += d.getSize();
            return new VariableDataChunk(new double[d.getSize()]);
        }
        else
        {
            queue.add(d);
            queueSize += d.getSize();
            d = (DataChunk)queue.remove(0);
            queueSize -= d.getSize();
            return d;
        }
    }

    public void stop()
    {
        queue = null;
    }

    
    public String toString()
    {
        return "Filtru de întârziere (3sec)";
    }
}
