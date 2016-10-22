/*
 * Created on Jan 12, 2005
 *
 */
package dsplaboratory.basicinput.dialogs;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JButton;

import dsplaboratory.Input;

/**
 * @author Flaviu
 *
 */

abstract class AbstractSignalSetupDialog extends JDialog
{
	protected JButton cancel;
	protected boolean setupFailed; 
	protected JDialog owner;		
	
	private class CancelAction extends AbstractAction	
    {
        public CancelAction()
        {
            super( "Cancel");
        }

        public void actionPerformed(ActionEvent arg0)
        {
        	setupFailed = true;
        	dispose();
        }
    }
        
    AbstractSignalSetupDialog( String title)
	{
    	setTitle( title);
    	cancel = new JButton( new CancelAction());
    	setupFailed = true;
	}
    
    AbstractSignalSetupDialog( JDialog parent, String title)
	{
    	super( parent, title, true);
    	owner = parent;
    	cancel = new JButton( new CancelAction());
    	setupFailed = true;
    }   
    
	public boolean setupFailed()
	{
		return setupFailed;
	}
	
    public abstract Input getInput();
}
