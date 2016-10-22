/*
 * Created on Nov 15, 2004
 *
 */
package dsplaboratory.dummy;

import dsplaboratory.VisualisationFactory;
import dsplaboratory.Analyzer;

import java.awt.Component;

import javax.swing.*;


/**
 *
 * @author Luke
 */
public class DummyVisFactory implements VisualisationFactory
{
    //JTable tabel;
    PlotPanel visIn, visOut;
    
    public String getName()
    {
        return "Dummy Visualisation Factory";
    }

    public Analyzer getInputAnalyzer(JDialog parent, int frequency)
    {
        //tabel = new JTable(new DefaultTableModel(10, 2));
        visIn = new PlotPanel(new PlotModel(0, 0, 1000, 20));
        visOut = new PlotPanel(new PlotModel(0, 0, 1000, 20));
        return new DummyAnalyzer(visIn);
    }

    public Analyzer getOutputAnalyzer()
    {
        return new DummyAnalyzer(visOut);
    }

    public Component getVisualisation()
    {
        // atentie ! Component asta va fi utilizat in alt thread !
        // atentie la comunicate / sincronizare
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(visIn);
        p.add(visOut);
        return p;
    }

}
