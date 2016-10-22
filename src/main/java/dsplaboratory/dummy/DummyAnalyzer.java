/*
 * Created on Nov 15, 2004
 *
 */
package dsplaboratory.dummy;

import javax.swing.JTable;

import dsplaboratory.Analyzer;
import dsplaboratory.DataChunk;

/**
 *
 * @author Luke
 */
public class DummyAnalyzer implements Analyzer
{
    protected PlotPanel plot;
    protected int row; 

    public DummyAnalyzer(PlotPanel vis)
    {
        plot = vis;
    }
    
    public void start()
    {
        row = 0;
    }

    public void putNext(DataChunk d)
    {
        for(int i = 0; i < d.getSize(); i++)
            plot.getModel().addPoint(row++, d.getElement(i));
        // cere redesenarea mai rar
        // TODO auto calculeaza cat de rar sa redeseneze, sau 
        // intreaba utilizatorul
        //if (row % 20 == 0) // doar o data la 20 de apeluri
            plot.repaint(); // nu deseneaza efectiv, doar cere o redesenare
                            // la prima ocazie -> ok pentru multithreading
    }

    public void stop()
    {
        //do nothing
    }

}
