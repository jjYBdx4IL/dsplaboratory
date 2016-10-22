/*
 * Created on Dec 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsplaboratory.wave;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author silviu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WaveInputDialog extends JDialog
{
	private String nume_fisier; 
	private float sampleCount;
	private int amplitude, sinFreq, samplingFreq;
    private boolean okeyed = false;

    private JLabel FileLabel,label;
    private JTextField FileField;
    private JLabel FormatLabel;
    private JLabel Error,CompressionCode,NumberChannels,SampleRate,AvgBytePerSec,BlockAlign,SignBperSample,ExtraFormBytes;
    private JLabel DataChunkSize;
    private JLabel Channels;
    private JTextField error,compressionCode,numberChannels,sampleRate,avgBytePerSec,blockAlign,signBperSample,extraFormBytes;
    private JTextField dataChunkSize;    
    private JComboBox channels;
    
    private JButton ok, cancel,browse;
	
    public String GetFileName()
    {
        return nume_fisier;
    }
    public int GetChannel()
    {
    	return channels.getSelectedIndex();
    }
    public boolean isOK()
    {
        return okeyed;
    }

    public WaveInputDialog(Dialog parent) throws HeadlessException
    {
        super(parent, true);
        setTitle("WAVE");
        
        Container pane = getContentPane();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
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
                                                      
        FileLabel = new JLabel("WAV File:  ");                        
        UPpanel.add(FileLabel,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        label = new JLabel("                                       ");
        UPpanel.add(label,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        FileField = new JTextField();
        FileField.setEnabled(false);
        UPpanel.add(FileField,gbc);
             
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        Error = new JLabel("Wav Structure: ");       
        UPpanel.add(Error,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        error = new JTextField("NOT OK");
        error.setEnabled(false);
        UPpanel.add(error,gbc);
                        
        gbc.gridy = 3;
        gbc.gridx = 0;
        FormatLabel = new JLabel("Wav Properties:    ");
              
        FormatLabel.setFont(new Font("gigi",0,23));
        UPpanel.add(FormatLabel,gbc);
        
        gbc.gridy = 4;
        gbc.gridx = 0;
        CompressionCode = new JLabel("Compression code: ");
        UPpanel.add(CompressionCode,gbc);
        
        gbc.gridx = 1;
        compressionCode = new JTextField("0");
        compressionCode.setEnabled(false);
        UPpanel.add(compressionCode,gbc);
        
        
        gbc.gridy = 5;
        gbc.gridx = 0;
        NumberChannels = new JLabel("Number of channels: ");
        UPpanel.add(NumberChannels,gbc);
        
        gbc.gridx = 1;
        numberChannels = new JTextField("0");
        numberChannels.setEnabled(false);
        UPpanel.add(numberChannels,gbc);        
        
        gbc.gridy = 6;
        gbc.gridx = 0;
        SampleRate = new JLabel("Sample rate: ");
        UPpanel.add(SampleRate,gbc);
        
        gbc.gridx = 1;
        sampleRate = new JTextField("0");
        sampleRate.setEnabled(false);
        UPpanel.add(sampleRate,gbc);
        
        gbc.gridy = 7;
        gbc.gridx = 0;
        AvgBytePerSec= new JLabel("Average bytes - kilobytes per second: ");
        UPpanel.add(AvgBytePerSec,gbc);
        
        gbc.gridx = 1;
        avgBytePerSec = new JTextField("0");
        avgBytePerSec.setEnabled(false);
        UPpanel.add(avgBytePerSec,gbc);
        
        gbc.gridy = 8;
        gbc.gridx = 0;
        BlockAlign = new JLabel("Block align: ");
        UPpanel.add(BlockAlign,gbc);
        
        gbc.gridx = 1;
        blockAlign = new JTextField("0");
        blockAlign.setEnabled(false);
        UPpanel.add(blockAlign,gbc);
        
        gbc.gridy = 9;
        gbc.gridx = 0;
        SignBperSample = new JLabel("Significant bits per sample: ");
        UPpanel.add(SignBperSample,gbc);
        
        
        gbc.gridx = 1;
        signBperSample = new JTextField("0");
        signBperSample.setEnabled(false);
        UPpanel.add(signBperSample,gbc);
        
        gbc.gridy = 10;
        gbc.gridx = 0;
        ExtraFormBytes = new JLabel("Extra format bytes: ");
        UPpanel.add(ExtraFormBytes,gbc);
        
        gbc.gridx = 1;
        extraFormBytes = new JTextField("0");
        extraFormBytes.setEnabled(false);
        UPpanel.add(extraFormBytes,gbc);
        
        gbc.gridy = 11;
        gbc.gridx = 0;
        DataChunkSize = new JLabel("Data Chunk Size: ");
        UPpanel.add(DataChunkSize,gbc);
        
        gbc.gridx = 1;
        dataChunkSize = new JTextField("0");
        UPpanel.add(dataChunkSize,gbc);
        dataChunkSize.setEnabled(false);
        
        gbc.gridy = 12;
        gbc.gridx = 0;
        Channels = new JLabel("Pick the channel:  ");
        UPpanel.add(Channels,gbc);               
        
        gbc.gridx = 1;
        String []canale=new String[4];
        for (int i=0;i<4;i++)
           	canale[i] = ("CH ").concat(String.valueOf(i));
        channels  = new JComboBox(canale);
        channels.setSelectedIndex(0);
        channels.updateUI();
        channels.setEnabled(false);
        UPpanel.add(channels,gbc);        
        
        gbc.gridy = 13;
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
        pack();
        setResizable(false);
    }

    protected class BrowseAction extends AbstractAction
    {
        
        public BrowseAction()
        {
            super("Browse");
        }
        
        public void actionPerformed(ActionEvent e)
        {
            JFileChooser fc = new JFileChooser();
            
            fc.setCurrentDirectory(new File("c:"));
            ExampleFileFilter filter = new ExampleFileFilter("wav","WAVE File");            
            fc.setFileFilter(filter);
            
            int retVal = fc.showOpenDialog(WaveInputDialog.this);
            if(retVal== JFileChooser.APPROVE_OPTION)
            {
                File f = fc.getSelectedFile();
                nume_fisier = f.getAbsolutePath();
                FileField.setText(nume_fisier);
                WaveInput a = new WaveInput(nume_fisier,0);
                if (a.GetError())
                	error.setText("NOT OK!!");
                else
                {
                	error.setText("ALL OK");                
                    if (a.GetCompressionCode()==1)
                        compressionCode.setText("PCM / uncompressed");
                    int nr_channels = a.GetNumberOfChannels();
                    for (int i=nr_channels;i<4;i++)
                    	channels.removeItemAt(nr_channels);
                    channels.setSelectedIndex(0);
                    channels.setEnabled(true);
                    channels.updateUI();	
                    	
                    Channels.setVisible(true);                    
                    numberChannels.setText(String.valueOf(a.GetNumberOfChannels()));
                    sampleRate.setText(String.valueOf(a.GetSampleRate()));
                    avgBytePerSec.setText(String.valueOf(a.GetAvrgBPerS())+"-"+(String.valueOf(a.GetAvrgBPerS()/1024)));
                    blockAlign.setText(String.valueOf(a.GetBlockAlign()));
                    signBperSample.setText(String.valueOf(a.GetSegnBits()));
                    extraFormBytes.setText(String.valueOf(a.GetExtraFBytes()));
                    dataChunkSize.setText(String.valueOf(a.GetDataChunkSize()));                                      	
				}            
            }
        }
    }

    private class OKAction extends AbstractAction
    {
        public OKAction()
        {
            super("OK");
            
        }
        
        public void actionPerformed(ActionEvent arg0)
        {
        	//nume_fisier = sampleCountField.getText();
        	//sampleCount = Integer.parseInt(sampleCountField.getText());
            okeyed = true;
            dispose();
        }
    }
    
    private class CancelAction extends AbstractAction
    {
        public CancelAction()
        {
            super("Cancel");
        }
        
        public void actionPerformed(ActionEvent arg0)
        {
            dispose();
        }
    }

	
	
}
