
/**
 * Write a description of class AuImputOptions here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package dsplaboratory.auraw;
import javax.swing.*;

import java.awt.*;
public class AuInputOptions extends JPanel 
{
 ButtonGroup group;
 JComboBox OctetPerEsantion;
public AuInputOptions()
{  
   setPreferredSize(new Dimension(150,150));
   setLayout(new BorderLayout());
   JLabel eticheta1=new JLabel("  Au File Reading properties  ");
   eticheta1.setBounds(10,0,20,20);
   add(eticheta1,BorderLayout.NORTH);  
//-----------------------------------------------------------------------------
   JPanel PanouCentral=new JPanel();
   PanouCentral.setLayout(null);
   //radio buttons
   JRadioButton Canal1 = new JRadioButton("Read channel 1");
   Canal1.setSelected(true);
   Canal1.setBounds(0,10,100,20);
   JRadioButton Canal2 = new JRadioButton("Read channel 2");
   Canal2.setBounds(0,30,120,20);
   JRadioButton Canal12 = new JRadioButton("Read both channels");
   Canal12.setBounds(0,50,150,20);

   // Group the radio buttons.
   group = new ButtonGroup();
   group.add(Canal1);
   group.add(Canal2);
   group.add(Canal12);
   PanouCentral.add(Canal1);
   PanouCentral.add(Canal2);
   PanouCentral.add(Canal12);
   add(PanouCentral,BorderLayout.CENTER);
}
}
