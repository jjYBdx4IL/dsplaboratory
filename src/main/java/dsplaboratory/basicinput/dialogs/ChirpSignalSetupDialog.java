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

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dsplaboratory.Input;
import dsplaboratory.basicinput.inputs.ChirpSignal;

/**
 * @author Flaviu
 *
 */
class ChirpSignalSetupDialog  extends AbstractSignalSetupDialog
{
    protected JTextField mean;
    protected JTextField amplitude;
    protected JTextField initialFrequency;
    protected JTextField targetTime;
    protected JTextField targetFrequency;
    protected JTextField phase;
    
    protected double meanData;
    protected double amplitData;
    protected double initialFreqData;
    protected double targetTimeData;
    protected double targetFreqData;
    protected double phaseData;    

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
                meanData        = Double.parseDouble( mean.getText());
                amplitData      = Double.parseDouble( amplitude.getText());
                initialFreqData = Double.parseDouble( initialFrequency.getText());
                phaseData       = Double.parseDouble( phase.getText());
                targetTimeData  = Double.parseDouble( targetTime.getText());
                targetFreqData  = Double.parseDouble( targetFrequency.getText());                                
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
    
    public ChirpSignalSetupDialog( JDialog parent) throws HeadlessException
    {
        super( parent, "Chirp Signal Setup");
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
        mean = new JTextField("0");
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
        pane.add( new JLabel( "Initial Frequency (Hz):"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        initialFrequency = new JTextField( "1");
        pane.add( initialFrequency, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        pane.add( new JLabel( "Phase offset (rad):"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        phase = new JTextField( "0");
        pane.add( phase, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        pane.add( new JLabel( "Target Time (s):"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        targetTime = new JTextField( "5");
        pane.add( targetTime, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        pane.add( new JLabel( "Target Frequency (Hz):"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        targetFrequency = new JTextField( "10");
        pane.add( targetFrequency, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;        
        pane.add( cancel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        pane.add( new JButton( new OKAction()), gbc);
        
        pack();
        setResizable( false);                
    }
    
    public Input getInput()
    {
        BasicInputSetupDialog mainDialog = ( BasicInputSetupDialog) owner;        
    	return new ChirpSignal( meanData, amplitData, initialFreqData, targetFreqData, phaseData, targetTimeData, 
    	        		        mainDialog.getSamplingFrequency(),
    	        				mainDialog.getSamplesCount());
    }           
}
