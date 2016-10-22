/*
 * Created on Nov 14, 2004
 *
 */
package dsp.dummy;

import javax.swing.JDialog;

import dsp.Filter;
import dsp.FilterFactory;

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
