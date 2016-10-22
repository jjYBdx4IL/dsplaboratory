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
import dsplaboratory.basicinput.inputs.SinusoidSignal;
import dsplaboratory.basicinput.SinusoidSignalData;

/**
 * @author Flaviu
 *
 */
class SinusoidSignalSetupDialog  extends AbstractSignalSetupDialog
{
    protected JTextField mean      = new JTextField();
    protected JTextField amplitude = new JTextField();
    protected JTextField frequency = new JTextField();
    protected JTextField phase     = new JTextField();
    
    protected final String crt   = "Current: ";
    protected final String harms = "Harmonics: ";
    
    protected JLabel crtF   = new JLabel( crt + "1");
    protected JLabel harmsF = new JLabel( harms + "1");
    
    protected double meanData = 0;
    
    protected SinusoidSignalData[] sinusoidData = new SinusoidSignalData[ 10];
    protected int harmonics = 1;
    protected int current   = 0;
            
    protected class OKAction extends AbstractAction
    {
        public OKAction()
        {
            super( "OK");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            if ( !setupFailed) dispose();
        }
    }

    protected void loadData()
    {             
        mean.setText( Double.toString( meanData));
        amplitude.setText( Double.toString( sinusoidData[ current].amplitData));
        frequency.setText( Double.toString( sinusoidData[ current].freqData));        
        phase.setText( Double.toString( sinusoidData[ current].phaseData));        
    }

    protected void currentModified()
    {
       crtF.setText( crt + Integer.toString( current + 1));                     
    }
    
    protected void harmonicsAndCurrentModified()
    {    
       currentModified();
       harmsF.setText( harms + Integer.toString( harmonics));              
    }   

    protected class PreviousAction extends AbstractAction
    {
        public PreviousAction()
        {
            super( "Previous");
        }

        public void actionPerformed(ActionEvent arg0)
        {                        
            if ( current > 0)
            {
               --current;
               loadData();
               currentModified();
            }   
        }
    }      
    
    protected class NextAction extends AbstractAction
    {
        public NextAction()
        {
            super( "Next");
        }

        public void actionPerformed(ActionEvent arg0)
        {            
            if ( current + 1 < harmonics)
            {
                ++current;
                loadData();
                currentModified();                
            }            
        }
    }   
    protected class AddAction extends AbstractAction
    {
        public AddAction()
        {
            super( "Add harmonic");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            if ( harmonics < 11)
            {
                current = harmonics;                
                ++harmonics;
                loadData();
                harmonicsAndCurrentModified();                
            }                                    
        }
    }   

    protected class DeleteAction extends AbstractAction
    {
        public DeleteAction()
        {
            super( "Delete");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            if ( harmonics > 1)
            {
                for( int i = current + 1; i < harmonics; ++i)                
                    sinusoidData[ i - 1] = sinusoidData[ i];
                --harmonics;              
                if ( current == harmonics) --current;
                loadData();
                harmonicsAndCurrentModified();                
            }
        }
    }   
    
    protected class ApplyAction extends AbstractAction
    {
        public ApplyAction()
        {
            super( "Apply");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            try
            {
                meanData = Double.parseDouble( mean.getText());                
                sinusoidData[ current].amplitData = Double.parseDouble( amplitude.getText());
                sinusoidData[ current].freqData   = Double.parseDouble( frequency.getText());
                sinusoidData[ current].phaseData  = Double.parseDouble( phase.getText());                
            }
            catch( Exception e)
            {   
                setupFailed = true;                
                return;
            }           
            setupFailed = false;            
        }
    }       

    public SinusoidSignalSetupDialog( JDialog parent) throws HeadlessException
    {
        super( parent, "Sinusoid Signal Setup");            	
        Container pane = getContentPane();
        pane.setLayout( new GridBagLayout());
        
        for( int i = 0; i < 10; ++i)
            sinusoidData[ i] = new SinusoidSignalData();

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
        pane.add( mean, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pane.add( new JLabel( "Amplitude (V):"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        pane.add( amplitude, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        pane.add( new JLabel( "Frequency (Hz):"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        pane.add( frequency, gbc);
      
        gbc.gridx = 0;
        gbc.gridy = 3;
        pane.add( new JLabel( "Phase offset (rad):"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;        
        pane.add( phase, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;        
        pane.add( new JButton( new ApplyAction()), gbc);        


        gbc.gridx = 0;
        gbc.gridy = 4;
        pane.add( crtF, gbc);        
        gbc.gridx = 1;
        gbc.gridy = 4;
        pane.add( new JButton( new PreviousAction()), gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;        
        pane.add( new JButton( new NextAction()), gbc);        

        gbc.gridx = 0;
        gbc.gridy = 5;
        pane.add( harmsF, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        pane.add( new JButton( new AddAction()), gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;        
        pane.add( new JButton( new DeleteAction()), gbc);               

        gbc.gridx = 0;
        gbc.gridy = 6;        
        pane.add( cancel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        pane.add( new JButton( new OKAction()), gbc);

        pack();
        setResizable( false);
        loadData();
        setupFailed = false;           
    }

    public Input getInput()
    {
        BasicInputSetupDialog mainDialog = ( BasicInputSetupDialog) owner;        
    	return new SinusoidSignal( meanData, sinusoidData, harmonics,
    	        				   mainDialog.getSamplingFrequency(), mainDialog.getSamplesCount());    	        					
    }              
}
