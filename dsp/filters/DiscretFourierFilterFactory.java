/*
 * Created on Feb 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsp.filters;

import javax.swing.JDialog;

import dsp.Filter;
import dsp.FilterFactory;
import dsp.exception.UserCancelled;

/**
 * @author Irina
 */
public class DiscretFourierFilterFactory implements FilterFactory {

	DiscretFilterDialog fd;
	/* (non-Javadoc)
	 * @see dsp.FilterFactory#getName()
	 */
	public String getName() {
		return "Fourier Discret";
	}

	/* (non-Javadoc)
	 * @see dsp.FilterFactory#getFilter(javax.swing.JDialog, int)
	 */
	public Filter getFilter(JDialog parent, int frequency) throws UserCancelled 
	{
		fd = new DiscretFilterDialog(parent);
	    fd.setVisible(true);
	    if (fd.isOK()){
	    	return new FTJFourierFilter(0,0,0);     
	    }
	    else
	    	throw new UserCancelled();	
	}

}
