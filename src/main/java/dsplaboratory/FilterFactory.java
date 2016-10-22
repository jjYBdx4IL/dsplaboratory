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
public interface FilterFactory
{
    /**
     * @return numele categoriei de filter
     */
    public String getName();
    
    /**
     * Intreaba utilizatorul prin intermediul unui GUI propriu,
     * si intoarce un obiect Filter corespunzator 
     * @return Filter
     * @throws UserCancelled daca utilizatorul a anulat dialogul
     */
    public Filter getFilter(JDialog parent, int frequency) throws UserCancelled;
}
