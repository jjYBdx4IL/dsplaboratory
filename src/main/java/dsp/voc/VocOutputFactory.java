/*
 * Created on Feb 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsp.voc;
import javax.swing.JDialog;

import dsp.Output;
import dsp.OutputFactory;
import dsp.exception.UserCancelled;
/**
 * @author Sir Costy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VocOutputFactory implements OutputFactory{
	
	public Output getOutput(JDialog dialog, int frequency ) throws UserCancelled {
		VocOutputDialog sic = new VocOutputDialog(dialog,frequency);
		sic.setResizable(false); 
		sic.setVisible(true);
	    if (sic.isOK())
            return new VocOutput(sic.getFile(),sic.getSampleRate(),sic.getSampleSize());
        else
            throw new UserCancelled();	    
	}
	
	public String getName(){
		return "File Output (.voc)";
    }

	
}
