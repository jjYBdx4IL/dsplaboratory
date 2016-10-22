/*
 * Created on Nov 15, 2004
 *
 */
package dsp;

import java.awt.Component;

import javax.swing.JDialog;

import dsp.exception.UserCancelled;

/**
 *
 * @author Luke
 */
public interface VisualisationFactory
{
    /**
     * @return numele categoriei de vis
     */
    public String getName();
    
    /**
     * Intreaba utilizatorul prin intermediul unui GUI propriu,
     * si intoarce primul Analyzer corespunzator 
     * @return Input
     * @throws UserCancelled daca utilizatorul a anulat dialogul
     */
    public Analyzer getInputAnalyzer(JDialog parent, int frequency) throws UserCancelled;

    /**
     * intoarce al doilea Analyzer corespunzator (conform selectiei
     * facute de utilizator la functia getInputAnalyzer 
     * @return Input
     * @see VisualisationFactory#getInputAnalyzer(JDialog, int)
     */
    public Analyzer getOutputAnalyzer();

    
    /**
     * intoarce un Component corespunzator (conform selectiei
     * facute de utilizator la functia getInputAnalyzer 
     * @return Component
     * @see VisualisationFactory#getInputAnalyzer(JDialog, int)
     */
    public Component getVisualisation();

}
