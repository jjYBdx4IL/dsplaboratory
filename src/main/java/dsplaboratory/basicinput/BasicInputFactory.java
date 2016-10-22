/*
 * Created on Jan 12, 2005
 *
 */
package dsplaboratory.basicinput;

import javax.swing.JDialog;

import dsplaboratory.Input;
import dsplaboratory.InputFactory;
import dsplaboratory.exception.UserCancelled;
import dsplaboratory.basicinput.dialogs.BasicInputSetupDialog;


/**
 * @author Flaviu
 *
 */
public class BasicInputFactory implements InputFactory 
{
	public String getName()
	{
		return "Basic Generated Inputs";
	}

	public Input getInput( JDialog dialog) throws UserCancelled
	{
		BasicInputSetupDialog inputDialog = new BasicInputSetupDialog( dialog);
		inputDialog.setVisible( true);
        if ( inputDialog.setupFailed()) throw new UserCancelled();
        return inputDialog.getInput();
	}
}
