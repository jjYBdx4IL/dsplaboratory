/*
 * Created on Dec 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsplaboratory.wave;

import javax.swing.JDialog;

import dsplaboratory.exception.UserCancelled;
import dsplaboratory.Input;
import dsplaboratory.InputFactory;
import dsplaboratory.dummy.DummyInput;

/**
 * @author silviu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WaveInputFactory implements InputFactory 
{

	/* (non-Javadoc)
	 * @see dsp.InputFactory#getName()
	 */
	public String getName() {
		return "File Input (.wav)";
	}
	public Input getInput(JDialog dialog) throws UserCancelled 
	{
	WaveInputDialog did = new WaveInputDialog(dialog);
    did.setVisible(true);
    if (did.isOK())
        return new WaveInput(did.GetFileName(),did.GetChannel());       		
    else
        throw new UserCancelled();
	}

}
