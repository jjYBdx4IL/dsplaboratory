/*
 * Created on Dec 9, 2004
 *
 */

package dsplaboratory.basicinput.dialogs;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dsplaboratory.Input;

/**
 * @author Flaviu
 *
 */

public class BasicInputSetupDialog extends AbstractSignalSetupDialog
{
	// protected BasicInputSetupDialog owner;
	protected AbstractSignalSetupDialog	selectedSignal;	
	
    protected JComboBox		inputNamesSelection;      
    protected JTextField	samplingRate;
    protected JTextField	samples;
    
    protected double samplingFreq;
    protected int 	 samplesCount;
    
    protected AbstractSignalSetupDialog[] setupDialogs;    
    
    protected String[] inputNames = new String[]
    {
       "Sinusoid", "Pattern", "Chirp", "Random"
    };        
    
    protected class OKAction extends AbstractAction
    {
        public OKAction()
        {
            super( "OK");
        }

        public void actionPerformed(ActionEvent arg0)
        {
        	if ( !setupFailed()) dispose();
        }
    }
    
    protected class SelectionChanged implements ActionListener
    {
        public void actionPerformed( ActionEvent e)
        {            
            selectedSignal = setupDialogs[ (( JComboBox) e.getSource()).getSelectedIndex()]; 
        }
    }    

    protected class ConfigureAction extends AbstractAction
    {
        public ConfigureAction()
        {
            super( "Configure");
        }

        public void actionPerformed( ActionEvent arg0)
        {       
            try
            {
                samplingFreq = Double.parseDouble( samplingRate.getText());
                samplesCount = Integer.parseInt( samples.getText());
            }
            catch( Exception e)
            {   
                return;
            }
        	selectedSignal.setLocationRelativeTo( owner);        	
        	selectedSignal.setVisible(true);
        }
    }

    public BasicInputSetupDialog( JDialog parent) throws HeadlessException
    {          		   		
    	super( parent, "Basic Signal Setup");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        owner = this;
       	setLocation( 150, 150);
       	
       	Container pane = getContentPane();
       	pane.setLayout( new GridBagLayout());

       	GridBagConstraints gbc = new GridBagConstraints();
       	gbc.insets = new Insets( 10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridheight = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        pane.add( new JLabel( "Sampling Rate (Hz):"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        samplingRate = new JTextField( "50");
        pane.add( samplingRate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pane.add( new JLabel( "Samples:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        samples = new JTextField( "500");
        pane.add( samples, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth  = 1;
        gbc.ipadx = 10;
        pane.add( new JLabel( "Signal Type:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputNamesSelection = new JComboBox<String>(inputNames);
        inputNamesSelection.addActionListener( new SelectionChanged());
        pane.add( inputNamesSelection, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        pane.add( new JButton( new ConfigureAction()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        pane.add( Box.createHorizontalStrut( 10), gbc);        
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        pane.add( cancel, gbc);        

        gbc.gridx = 2;
        gbc.gridy = 4;
        pane.add( new JButton( new OKAction()), gbc);
        
        pack();
        setResizable( false);
        
        setupDialogs = new AbstractSignalSetupDialog[]
        {
           new SinusoidSignalSetupDialog( this),
           new PatternSignalSetupDialog ( this),
           new ChirpSignalSetupDialog   ( this),
           new RandomSignalSetupDialog  ( this)
        };   
        selectedSignal = setupDialogs[ 0];
    }
    
    public Input getInput()
    {
        if ( setupFailed()) return null;
    	return selectedSignal.getInput();
    }        
    
	public boolean setupFailed()
	{	    
		return selectedSignal.setupFailed();
	}
	
    public double getSamplingFrequency()
    {
        return samplingFreq;
    }

    public int getSamplesCount()
    {
        return samplesCount;
    }
}