/*
 * Created on Dec 9, 2004
 *
 */

package dsp.basicinput.dialogs;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import dsp.Input;
import dsp.basicinput.inputs.RandomSignal;

/**
 * @author Flaviu
 *
 */
class RandomSignalSetupDialog  extends AbstractSignalSetupDialog
{
    protected JTextField mean;
    protected JTextField amplitude;    
   
    protected double meanData;
    protected double amplitudeData;   
    
    protected class OKAction extends AbstractAction
    {
        public OKAction()
        {
            super( "OK");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            try
            {
                meanData      = Double.parseDouble( mean.getText());
                amplitudeData = Double.parseDouble( amplitude.getText());                
            }
            catch( Exception e)
            {
                setupFailed = true;
                return;
            }       
            setupFailed = false;            
        	dispose();
        }
    }    

    public RandomSignalSetupDialog( JDialog parent) throws HeadlessException
    {
        super( parent, "Random Signal Setup");
        setDefaultCloseOperation( DISPOSE_ON_CLOSE);
        
        Container pane = getContentPane();
        pane.setLayout( new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets( 10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridheight = 1;
        gbc.gridwidth  = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        pane.add( new JLabel( "Mean (V):"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        mean = new JTextField( "0");
        pane.add( mean, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pane.add( new JLabel( "Amplitude (V):"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        amplitude = new JTextField( "5");
        pane.add( amplitude, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;        
        pane.add( cancel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        pane.add( new JButton( new OKAction()), gbc);
        
        pack();
        setResizable( false);                        
    }
    
    public Input getInput()
    {
        BasicInputSetupDialog mainDialog = ( BasicInputSetupDialog) owner;        
    	return new RandomSignal( meanData, amplitudeData, 
    	        				 mainDialog.getSamplingFrequency(), mainDialog.getSamplesCount());
    }              
}
