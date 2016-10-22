/*
 * Created on Dec 9, 2004
 *
 */

package dsplaboratory.basicinput.dialogs;

import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dsplaboratory.Input;
import dsplaboratory.basicinput.PatternSignalData;
import dsplaboratory.basicinput.inputs.PatternSignal;


/**
 * @author Flaviu
 *
 */

class PatternSignalSetupDialog  extends AbstractSignalSetupDialog
{
	protected JTextField        mean;		
	protected DefaultTableModel model;
    protected JTable 			table;    
    protected PatternSignalData signalData;
    
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
                final double meanData = Double.parseDouble( mean.getText());
                signalData = new PatternSignalData();
                Vector v = model.getDataVector();
                if ( v.size() == 0)
                {
                    setupFailed = true;
                    return;                   
                }
                for( int i = 0; i < v.size(); ++i)
                {                                      
                    final double value = Double.parseDouble( ( String) (( Vector) v.get( i)).get( 0));                    
                    final double delay = Double.parseDouble( ( String) (( Vector) v.get( i)).get( 1));                                        
                    signalData.addItem( delay, value + meanData);                    
                }
                model.fireTableDataChanged();
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
    
    protected class AddAction extends AbstractAction
    {
        public AddAction()
        {
            super( "Add");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            final Object[] row = { "0", "0"};
            model.addRow( row);
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
            int pos = table.getSelectedRow();
            if ( pos >= 0)
            {
                model.getDataVector().remove( pos);
                model.fireTableDataChanged();
            }
        }
    }   
  
    public PatternSignalSetupDialog( JDialog parent) throws HeadlessException
    {
        super( parent, "Pattern Signal Setup");
    	Container pane = getContentPane();    	
    	pane.setLayout( new BoxLayout( pane, BoxLayout.X_AXIS));
        
    	Box leftSide = Box.createVerticalBox();  			
    	pane.add( leftSide);
    	
    	pane.add( Box.createHorizontalStrut( 10));
    	
    	JPanel rightSide = new JPanel();
    	pane.add( rightSide);
    	
    	
  // LeftSide  
    	leftSide.setBorder( new EmptyBorder( new Insets( 10, 10, 10, 10)));
    	
    	model = new DefaultTableModel ();
        table = new JTable( model);        
        
        JScrollPane scrollPane = new JScrollPane( table);        
        scrollPane.setPreferredSize( new Dimension( 200, 250));
        

        model.addColumn( "Value (V)");        
        model.addColumn( "Next value after (s)");
        Object[] s = { "0", "0"};
        model.addRow( s);
                
		leftSide.add( scrollPane, BorderLayout.EAST);   	
				
  // RightSide
    	rightSide.setLayout( new GridBagLayout());
    	GridBagConstraints gbc = new GridBagConstraints();
    	gbc.insets = new Insets( 10, 10, 10, 10);    	
        gbc.fill = GridBagConstraints.HORIZONTAL;            
    	
        gbc.gridy = 0;               
        rightSide.add( new JLabel( "Mean (V):"), gbc);
        
        mean = new JTextField( "0");
        mean.setColumns( 5);
        rightSide.add( mean, gbc);
        
        gbc.gridy = 1;
        Component strut = Box.createHorizontalStrut( 10);
        rightSide.add( strut, gbc);               
        
        gbc.gridy = 2;        
        rightSide.add( new JButton( new AddAction()), gbc);                
        rightSide.add( new JButton( new DeleteAction()), gbc);        
        
        gbc.gridy = 3;        
        rightSide.add( strut, gbc);
        
        gbc.gridy = 4;        
        rightSide.add( new JButton( new OKAction()), gbc);
        rightSide.add( cancel, gbc);        
        
        pack();
        setResizable( false);                      
    }
    
    public Input getInput()
    {
        BasicInputSetupDialog mainDialog = ( BasicInputSetupDialog) owner;        
    	return new PatternSignal( signalData, 
    	        				  mainDialog.getSamplingFrequency(),
    	        				  mainDialog.getSamplesCount());
    }              
}


