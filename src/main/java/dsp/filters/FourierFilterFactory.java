/*
 * Created on Jan 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsp.filters;

import javax.swing.JDialog;

import dsp.Filter;
import dsp.FilterFactory;
import dsp.exception.UserCancelled;
import dsp.filters.FTJFourierFilter;
import dsp.filters.FourierFilterDialog;

/**
 * @author Irina
 */
public class FourierFilterFactory implements FilterFactory {

	FourierFilterDialog fd;
	/* (non-Javadoc)
	 * @see dsp.FilterFactory#getName()
	 */
	public String getName() {
		return "Fourier";
	}

	/* (non-Javadoc)
	 * @see dsp.FilterFactory#getFilter(javax.swing.JDialog, int)
	 */
	public Filter getFilter(JDialog parent, int frequency) throws UserCancelled 
	{
		fd = new FourierFilterDialog(parent);
	    fd.setVisible(true);
	    if (fd.isOK())
	    {
	    	switch (fd.getFType())
			{
	    	// Filtru trece jos
	    		case 1:
	    			System.out.println("FTJ");
	    			return new FTJFourierFilter(fd.getFe(),fd.getFt(),fd.getOrdin());
	    				    			
	    	// Filtru trece sus
	    		case 2:
	    			System.out.println("FTS");
	    			return new FTSFourierFilter(fd.getFe(),fd.getFt(),fd.getOrdin());
	    			
	    	// Filtru trece banda
	    		case 3:
	    			System.out.println("FTB");
	    			return new FTBFourierFilter(fd.getFe(),fd.getFt(),fd.getFt2(),fd.getOrdin());
	    			
	    	// Filtru opreste banda
	    		case 4:
	    			System.out.println("FOB");
	    			return new FOBFourierFilter(fd.getFe(),fd.getFt(),fd.getFt2(),fd.getOrdin());
	    			
	    		default:
	    			return null;
	    	}
	    }
	    else
	    	throw new UserCancelled();	
	}

}
