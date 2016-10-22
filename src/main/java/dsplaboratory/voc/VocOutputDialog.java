/*
 * Created on Feb 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsplaboratory.voc;
import dsplaboratory.voc.VocInputOutputFilterDialog;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;

import java.awt.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.lang.*;
import java.math.*;
/**
 * @author Sir Costy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VocOutputDialog extends JDialog {
	private JLabel FormatLabel,FileLabel,label;
	private JTextField FileName,SampleRateValue,SampleRateSetValue,ChannelSizeValue;
	private int sampleRate;
	private int sampleSize;
	private File WriteFile; 
	private JLabel SampleRateLabel,SampleRateSetLabel,SampleSizeLabel,ChannelLabel;
	private JComboBox SampleSizeValue;
	private JButton browse,ok,cancel;
    private boolean okeyed;
    private boolean FileChoosed;
    
	public VocOutputDialog(Dialog parent,int frequency) throws HeadlessException{
        super(parent, "VOC OUTPUT DIALOG", true);
        sampleRate=frequency;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        okeyed = false;
    	FileChoosed=false;
        
    	 Container pane = getContentPane();
         JPanel UPpanel = new JPanel();
         UPpanel.setLayout(new GridBagLayout());
         UPpanel.setBorder(new TitledBorder(new EtchedBorder(), "File Details"));
         
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.gridx = 0;
         gbc.gridy = 0;
         gbc.gridheight = 1;
         gbc.gridwidth = 1;
         gbc.insets = new Insets(10, 10, 10, 10);
         gbc.fill = GridBagConstraints.HORIZONTAL;
                                                       
         FileLabel = new JLabel("Voc File:  ");                        
         UPpanel.add(FileLabel,gbc);
         
         gbc.gridx = 1;
         gbc.gridy = 0;
         label = new JLabel("                                       ");
         UPpanel.add(label,gbc);
         
         gbc.gridx = 0;
         gbc.gridy = 1;
         FileName = new JTextField();
         FileName.setText("No selected file");
         FileName.setEnabled(false);
         UPpanel.add(FileName,gbc);
             
                         
         gbc.gridx = 0;
         gbc.gridy = 2;
         FormatLabel = new JLabel("Voc Properties:    ");
               
         FormatLabel.setFont(new Font("gigi",0,23));
         UPpanel.add(FormatLabel,gbc);
         
         gbc.gridy = 3;         
         gbc.gridx = 0;
         SampleRateLabel = new JLabel("Sample Rate found:");
         UPpanel.add(SampleRateLabel,gbc);
        
         gbc.gridx = 1;
         SampleRateValue = new JTextField((new Integer(sampleRate).toString()));
         SampleRateValue.setEnabled(false);
         UPpanel.add(SampleRateValue,gbc);
        
         
         gbc.gridy = 4;
         gbc.gridx = 0;
         SampleRateSetLabel = new JLabel("Sample Rate save:");
         UPpanel.add(SampleRateSetLabel,gbc);
         
         gbc.gridx = 1;
         SampleRateSetValue = new JTextField((new Integer(sampleRate).toString()));
         SampleRateSetValue.setEnabled(true);
         UPpanel.add(SampleRateSetValue,gbc);
         
         gbc.gridy = 5;
         gbc.gridx = 0;
         SampleSizeLabel = new JLabel("Sample Size save:");
         UPpanel.add(SampleSizeLabel,gbc);
        
         gbc.gridx = 1;
         String[] SampleSize = { "8 Biti","16 Biti" };
         SampleSizeValue = new JComboBox(SampleSize);
         SampleSizeValue.setEnabled(true);
         UPpanel.add(SampleSizeValue,gbc);        
         
         gbc.gridy = 6;
         gbc.gridx = 0;
         ChannelLabel = new JLabel("Number of chanells:");
         UPpanel.add(ChannelLabel,gbc);
         
         gbc.gridx = 1;
      
         ChannelSizeValue = new JTextField("1 Channel");
         ChannelSizeValue.setEnabled(false);
         UPpanel.add(ChannelSizeValue,gbc);
         
         
         
         gbc.gridy = 7;
         gbc.gridx = 0;
         JLabel Extra = new JLabel("");    
         UPpanel.add(Extra,gbc); 
            
         ok = new JButton(new OKAction());        
         cancel = new JButton(new CancelAction());
         browse = new JButton(new BrowseAction());
         
         JPanel buttonPane = new JPanel(); 
         
         buttonPane.add(ok);
         buttonPane.add(cancel);
         buttonPane.add(browse);
         
         pane.add(UPpanel);
         pane.add(buttonPane, BorderLayout.SOUTH);
         
         browse.setAction(new BrowseAction());
         setLocation(300, 70);
         setResizable(false);
    	
    	
    	
    	
    	
        
    	doPack();
    	}
	
//	------------------------------------------------------------------------------------------
    private class BrowseAction extends AbstractAction{
    	public BrowseAction(){
    		super("Browse");
    		}
    		public void actionPerformed(ActionEvent arg0){
    			if(ChoseOutputFile()!=false)
    				FileChoosed=true;		
				else{
					System.out.println("Folder selected or bad extension file!");			
					FileChoosed=false;
					}
    			}
    
	}
//-------------------------------------------------------------------------------------------
    public boolean ChoseOutputFile(){
    	JFileChooser chooser = new JFileChooser();
        // Note: source for ExampleFileFilter can be found in FileChooserDemo,
        // under the demo/jfc directory in the Java 2 SDK, Standard Edition.
    	VocInputOutputFilterDialog filter = new VocInputOutputFilterDialog();
        filter.addExtension("voc");
        filter.setDescription("Creative Voice File");
     
        chooser.setFileFilter(filter);
        chooser.setDialogType(JFileChooser.SAVE_DIALOG); 
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        int returnVal = chooser.showOpenDialog(new Frame("Open Voc File"));
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           System.out.println("You chose to open this file: " +  chooser.getSelectedFile().getName());
           FileName.setText(chooser.getSelectedFile().getName());
           WriteFile=chooser.getSelectedFile();
           doPack();
           return true;
           }
           
        else{
        	FileName.setText("No selected file");
        	doPack();
        	return false;
        }  	
    //return false;	
  
    }
    
//  ------------------------------------------------------------------------------------------	
    private class CancelAction extends AbstractAction{
    	public CancelAction()
		{
    		super("Cancel");
		}

    	public void actionPerformed(ActionEvent arg0){
    		dispose();
		}

    }

//------------------------------------------------------------------------------------------
    private class OKAction extends AbstractAction{
    	public OKAction()
    		{
    		super("OK");
    		}

    	public void actionPerformed(ActionEvent arg0){
    		if((FileChoosed)&&((new Integer(SampleRateSetValue.getText()).intValue())>0))
    			okeyed = true;
    		dispose();
       		}
    }
//------------------------------------------------------------------------------------
    public boolean isOK(){
    	return okeyed;
	}
    public File getFile(){
    	return WriteFile;
	}
    public int getSampleRate(){
    	return (new Integer(SampleRateSetValue.getText()).intValue());
	}
    
    public int getSampleSize(){
    	return (SampleSizeValue.getSelectedIndex());
	}
    
    public void doPack(){
    	pack();
    }
	
	
}
