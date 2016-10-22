/*
 * Created on Nov 14, 2004
 *
 */
package dsp.dummy;

import javax.swing.JDialog;

import dsp.Input;
import dsp.InputFactory;
import dsp.exception.UserCancelled;

/**
 *
 * @author Luke
 */
public class DummyInputFactory implements InputFactory
{

    public String getName()
    {
        return "Dummy Input";
    }

    public Input getInput(JDialog parent) throws UserCancelled
    {
        DummyInputDialog did = new DummyInputDialog(parent);
        did.setVisible(true);
        
        if (did.isOK())
            return new DummyInput(did.getAmplitude(), did.getSinFreq(),
                    did.getSampleCount(), did.getSamplingFreq());
        else
            throw new UserCancelled();
    }

}
