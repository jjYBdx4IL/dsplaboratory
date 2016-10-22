/*
 * Created by canti, 23.11.2004
 *
 */
package dsplaboratory.dummy;

import javax.swing.JDialog;

import dsplaboratory.Filter;
import dsplaboratory.FilterFactory;
import dsplaboratory.exception.UserCancelled;

/**
 * @author canti, 23.11.2004
 *
 */
public class TestFilterFactory implements FilterFactory
{

    /**
     * 
     */
    public TestFilterFactory()
    {
        super();
    }

    /* 
     * Overriden
     * 
     */
    public String getName()
    {
        return "TestFilter: mediere";
    }

    /* 
     * Overriden
     * 
     */
    public Filter getFilter(JDialog parent, int frequency) throws UserCancelled
    {
        return new TestFilter();
    }

}
