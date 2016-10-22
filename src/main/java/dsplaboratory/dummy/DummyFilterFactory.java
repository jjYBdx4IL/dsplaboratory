/*
 * Created on Nov 14, 2004
 *
 */
package dsplaboratory.dummy;

import javax.swing.JDialog;

import dsplaboratory.Filter;
import dsplaboratory.FilterFactory;

/**
 *
 * @author Luke
 */
public class DummyFilterFactory implements FilterFactory
{

    public String getName()
    {
        return "Null Filter";
    }

    public Filter getFilter(JDialog parent, int frequency)
    {
        return new NullFilter();
    }

}
