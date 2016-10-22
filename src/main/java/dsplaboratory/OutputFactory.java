/*
 * Created on Nov 14, 2004
 *
 */
package dsplaboratory;

import javax.swing.JDialog;

import dsplaboratory.exception.UserCancelled;

/**
 *
 * @author Luke
 */
public interface OutputFactory
{
    /**
     * @return numele categoriei de output
     */
    public String getName();
    
    /**
     * Intreaba utilizatorul prin intermediul unui GUI propriu,
     * si intoarce un obiect Output corespunzator 
     * @return Output
     * @throws UserCancelled daca utilizatorul a anulat dialogul
     */
    public Output getOutput(JDialog parent, int frequency) throws UserCancelled;
}
