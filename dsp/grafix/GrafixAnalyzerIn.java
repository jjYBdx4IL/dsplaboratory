/*
 * Created on Dec 4, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsp.grafix;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import dsp.Analyzer;
import dsp.DataChunk;
import dsp.MainFrame;
import dsp.grafix.PlotModel;
/**
 * @author Marius
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GrafixAnalyzerIn implements Analyzer {
protected GraphPlot plot,vizualizareSemnal;
protected GraphPlot vizualizareSpectru;
protected int row=0;
private boolean afisareSpectru,afisareSpectruOut,okeyed,afisareSemnal;
private String signalType,signalTypeOut;
float[] signal;
float Signal[];
float[] spectrum;
int nSamples=256;
int contor=0;
int  graphWidth=600;
int  graphHeigth=200;
private int zoomx, zoomy;
private int freq;
private JPanel p;

public GrafixAnalyzerIn(boolean afisS,String sigT,boolean afisSemn,int zoomx,int zoomy,int freq)
{
	afisareSpectru=afisS;
	signalType=sigT;
	afisareSemnal=afisSemn;
	this.zoomx = zoomx;
    this.zoomy = zoomy;
    this.freq = freq;
    p = new JPanel();
    p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));  

}

public void start()
{
    PlotModel pm=new PlotModel(0,0,(graphWidth)/zoomx,500);
    PlotModel pm1=new PlotModel(0,0,(graphWidth)/zoomx,500);
    vizualizareSpectru = new GraphPlot(pm,graphWidth,graphHeigth,"Afisare Spectru","f[HZ]","A[V]",freq); 
    vizualizareSemnal= new GraphPlot(pm1,graphWidth,graphHeigth,"Afisare Semnal","Rad[Pi]","A[v]",freq);
    Signal=new float[nSamples];

    try
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                p.removeAll();
                if (afisareSemnal)
                    p.add(vizualizareSemnal);
                if (afisareSpectru)
                    p.add(vizualizareSpectru);
            }
        });
    }
    catch (Exception e)
    {
        throw new RuntimeException(e);
    }
    row = 0;
}

public void putNext(DataChunk d)
{
	for(int i = 0; i < d.getSize(); i++){
        vizualizareSemnal.getModel().addPoint(row++, d.getElement(i));
       // vizualizareSemnal.getModel().addPoint()
        if(afisareSpectru && contor<256) 
           Signal[contor++]=(float)d.getElement(i);
    }
    // cere redesenarea mai rar
    // TODO auto calculeaza cat de rar sa redeseneze, sau 
    // intreaba utilizatorul
	if(row%20==0){ 
    	vizualizareSemnal.repaint(); // nu deseneaza efectiv, doar cere o redesenare
	}
	
	if (row%nSamples==0)
    {
		float maxValue = 0.0f;
		plotSpectrum(Signal);     	
        vizualizareSpectru.repaint();
        for (int i = 0; i < nSamples/2; i++){
           maxValue=Math.max(maxValue, Math.abs(spectrum[i]));
        vizualizareSemnal.setYmax(maxValue);
        }
        contor=0;
    }   
   
    
}
private void plotSpectrum(float []iSignal) {
    int i=0;
	spectrum = new float [nSamples / 2];
    FastFourierTransform fft = new FastFourierTransform();
    spectrum = fft.fftMag(iSignal);
    vizualizareSpectru.setPlotStyle(GraphPlot.SPECTRUM);
    vizualizareSpectru.setTracePlot(false);
    float maxValue = 0.0f;
    System.out.println("Spectrum");
    for (i = 0; i < nSamples/2; i++){
    	maxValue=Math.max(maxValue, Math.abs(spectrum[i]));
    }	
    vizualizareSpectru.setYmax(maxValue);
    vizualizareSpectru.setPlotValues(spectrum);
    vizualizareSpectru.repaint();
} 
public Component getConfiguration()
{
    return p;
}
public void stop()
{
    //do nothing
}

}
