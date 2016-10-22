/*
 * Created on Feb 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsp.serial;
import javax.swing.JDialog;
import dsp.Input;
import dsp.InputFactory;
import dsp.exception.UserCancelled;

import dsp.serial.SerialInput;
import dsp.serial.SerialInputDialog;
/**
 * @author Radu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SerialInputFactory implements InputFactory{
	 
	public Input getInput(JDialog dialog) throws UserCancelled
	    {
		   SerialInputDialog serial = new SerialInputDialog(dialog);
		    serial.setVisible(true);
		    if (serial.isOK())
	            return new SerialInput(serial.getPortName(),serial.getBaudRate(),serial.getFlowControlIn(),
	            		serial.getFlowControlOut(),serial.getDatabits(),serial.getStopbits(),
						serial.getParity());
	        else
	            throw new UserCancelled();	   
          
		
		}

	    public String getName()
	    {
	        return "Serial Input";
	    }
}
