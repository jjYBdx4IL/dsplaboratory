/*
 * Created on Nov 14, 2004
 *
 */
package dsplaboratory;

import javax.swing.JDialog;
import javax.swing.JFrame;

import dsplaboratory.exception.UserCancelled;

/**
 *
 * @author Luke
 */
public interface InputFactory
{
    /**
     * @return numele categoriei de input
     */
    public String getName();
    
    /**
     * Intreaba utilizatorul prin intermediul unui GUI propriu,
     * si intoarce un obiect Input corespunzator 
     * @return Input
     * @throws UserCancelled daca utilizatorul a anulat dialogul
     */
    public Input getInput(JDialog dialog) throws UserCancelled;
}
