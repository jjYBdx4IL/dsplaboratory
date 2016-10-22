/*
 * Created on Jan 12, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsp.auraw;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * @author adrian
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AuOutputOptions extends JPanel
{
public JComboBox SampleRateCombo ;
public JComboBox SampleSizeInBitsCombo;
public JComboBox ChannelsCombo;
public JComboBox SignedCombo;
public JComboBox BigEndianCombo ;
public AuOutputOptions()
{  
 setPreferredSize(new Dimension(150,150));
 setLayout(new BorderLayout());
 JLabel eticheta1=new JLabel("Au File Parameters");
 add(eticheta1,BorderLayout.NORTH);  
//	-----------------------------------------------------------------------------
 JPanel PanouCentral=new JPanel();
 PanouCentral.setLayout(new GridLayout(5,2,10,10));

 
 JLabel SampleRate = new JLabel("Sample Rate");
 String[] Strings = {"11025", "16000","22050","32000","44100","48000"};
 SampleRateCombo = new JComboBox(Strings);
 SampleRateCombo.setSelectedIndex(0);
 
 JLabel SampleSizeInBits = new JLabel("Sample size");
 String[] Strings1 = {"8", "16"};
 SampleSizeInBitsCombo = new JComboBox(Strings1);
 SampleSizeInBitsCombo.setSelectedIndex(0);

 JLabel Channels = new JLabel("Channels");
 String[] Strings2 = {"1","2"};
 ChannelsCombo = new JComboBox(Strings2);
 ChannelsCombo.setSelectedIndex(0);
 
 JLabel Signed = new JLabel("Signed");
 String[] Strings3 = {"true","false"};
 SignedCombo = new JComboBox(Strings3);
 SignedCombo.setSelectedIndex(0);
 
 JLabel BigEndian=new JLabel("Big Endian");
 String[] Strings4 = {"true"};
 BigEndianCombo = new JComboBox(Strings4);
 BigEndianCombo.setSelectedIndex(0);
 
 // Group the radio buttons.
 PanouCentral.add(SampleRate);
 PanouCentral.add(SampleRateCombo);
 PanouCentral.add(SampleSizeInBits);
 PanouCentral.add(SampleSizeInBitsCombo);
 PanouCentral.add(Channels);
 PanouCentral.add(ChannelsCombo); 
 PanouCentral.add(Signed);
 PanouCentral.add(SignedCombo);  
 PanouCentral.add(BigEndian);
 PanouCentral.add(BigEndianCombo);
 add(PanouCentral,BorderLayout.CENTER);
}
}
