/*
 * Created on Nov 14, 2004
 *
 */
package dsp;

/**
 *
 * @author Luke
 */
public interface DataChunk
{
    public abstract int getSize();

    public abstract double getElement(int index);
}