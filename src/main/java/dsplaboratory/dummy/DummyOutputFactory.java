/*
 * Created on Nov 15, 2004
 *
 */
package dsplaboratory.dummy;

import javax.swing.JDialog;

import dsplaboratory.Output;
import dsplaboratory.OutputFactory;

/**
 *
 * @author Luke
 */
public class DummyOutputFactory implements OutputFactory
{

    public String getName()
    {
        return "Null Output";
    }

    public Output getOutput(JDialog parent, int frequency)
    {
        return new NullOutput();
    }

}
