/*
 * Created on Nov 15, 2004
 *
 */
package dsp.dummy;

import javax.swing.JTable;

import dsp.Analyzer;
import dsp.DataChunk;

/**
 *
 * @author Luke
 */
public class DummyFFTAnalyzer implements Analyzer
{
    protected PlotPanel plot;
    protected int row; 
    private double[] time = new double[10];
    private double[] freq = new double[10];

    public DummyFFTAnalyzer(PlotPanel vis)
    {
        plot = vis;
    }
    
    public void start()
    {
        row = 0;
    }

    public void putNext(DataChunk d)
    {
        System.arraycopy(time, d.getSize(), time, 0, 10 - d.getSize());
        for(int i = 0; i < d.getSize(); i++)
            time[10 - d.getSize() + i] = d.getElement(i);
        fft();
        //plot.repaint();
    }

    public void stop()
    {
        //do nothing
    }

    private void fft()
    {
        System.arraycopy(time, 0, freq, 0, 10);
        // placeholder
    }
}
