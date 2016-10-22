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
import dsp.Analyzer;
import dsp.DataChunk;
import dsp.dummy.PlotPanel;
import dsp.grafix.PlotModel;

/**
 * @author Marius
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GrafixAnalyzerOut implements Analyzer {
protected GraphPlot vizualizareSpectru,vizualizareSemnalFiltrat;
protected int row;
private boolean afisareSpectru,afisareSpectruOut,okeyed,afisareSemnal;
private String signalType,signalTypeOut;
float[] signal;
float Signal[];
float[] spectrum;
static float ampl = 1.0f;     // default amplitude = 1.0 V
static float rate = 8000.0f;  // default sampling rate = 8000 samples/s
int nSamples=256;
int contor=0; 
int  graphWidth=600;
int  graphHeigth=200;
 

public GrafixAnalyzerOut(boolean afisS,String sigT,boolean afisSemn,int zoomx,int zoomy,int freq)
{
	afisareSpectru=afisS;
	signalType=sigT;
	afisareSemnal=afisSemn;
	
	
	PlotModel pm=new PlotModel(0,0,(graphWidth)/zoomx,30);
	PlotModel pm1=new PlotModel(0,0,(graphWidth)/zoomx,30);
	vizualizareSpectru = new GraphPlot(pm,graphWidth,graphHeigth,"Afisare Spectru","f[HZ]","A[V]",freq); 
	vizualizareSemnalFiltrat= new GraphPlot(pm1,graphWidth,graphHeigth,"Afisare Semnal","Rad[Pi]","A[v]",freq);
	Signal=new float[nSamples];

}
public void start()
{
    row = 0;
}

public void putNext(DataChunk d)
{
	for(int i = 0; i < d.getSize(); i++){
        vizualizareSemnalFiltrat.getModel().addPoint(row++, d.getElement(i));
       // vizualizareSemnal.getModel().addPoint()
        if(afisareSpectru && contor<256) 
           Signal[contor++]=(float)d.getElement(i);
//           System.out.println(d.getElement(i));
    }
    // cere redesenarea mai rar
    // TODO auto calculeaza cat de rar sa redeseneze, sau 
    // intreaba utilizatorul
	if(row%20==0){ 
    	vizualizareSemnalFiltrat.repaint(); // nu deseneaza efectiv, doar cere o redesenare
	}
	
	if (row%nSamples==0)
    {
    	plotSpectrum(Signal);     	
        vizualizareSpectru.repaint();
        contor=0;
    }   
   
    
}

private void plotSpectrum(float []iSignal) {
    spectrum = new float [nSamples / 2];
    FastFourierTransform fft = new FastFourierTransform();
    spectrum = fft.fftMag(iSignal);
    vizualizareSpectru.setPlotStyle(GraphPlot.SPECTRUM);
    vizualizareSpectru.setTracePlot(false);
    float maxValue = 0.0f;
    for (int i = 0; i < nSamples/2; i++)
    maxValue = Math.max(maxValue, Math.abs(spectrum[i]));
    vizualizareSpectru.setYmax(maxValue);
    vizualizareSpectru.setPlotValues(spectrum);
    vizualizareSpectru.repaint();
} 
public Component getConfiguration()
{
	  JPanel p = new JPanel();
	  p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));	
	  if (afisareSemnal)
	      p.add(vizualizareSemnalFiltrat);	
	  if (afisareSpectru)
	      p.add(vizualizareSpectru);	
      return p;
}
public void stop()
{
    //do nothing
}

}
