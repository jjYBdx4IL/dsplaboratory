/*
 * Created on Feb 4, 2005
 *
 */
package dsp.dummy;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import dsp.Filter;
import dsp.FilterFactory;
import dsp.exception.UserCancelled;

/**
 *
 * @author Luke
 */
public class MiscFilterFactory implements FilterFactory
{

    public MiscFilterFactory()
    {
        super();
    }

    public String getName()
    {
        return "Diverse - Miscellanea";
    }

    public Filter getFilter(JDialog parent, int frequency) throws UserCancelled
    {
        Filter[] possibleValues = {
            new DelayFilter(frequency), new TestFilter() 
            };
        Filter filtru = (Filter)JOptionPane.showInputDialog(parent,
                    "Alegeti un filtru", "Filtru",
                    JOptionPane.QUESTION_MESSAGE, null,
                    possibleValues, possibleValues[0]);
        
        if (filtru == null)
            throw new UserCancelled();
        
        return filtru;
    }

}
