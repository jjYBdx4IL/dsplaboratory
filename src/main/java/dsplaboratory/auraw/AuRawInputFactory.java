
/**
 * Write a description of class AuRawInputFactory here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package dsplaboratory.auraw;
import dsplaboratory.Input;
import dsplaboratory.InputFactory;

import java.util.*;

import javax.sound.sampled.*;
import javax.swing.filechooser.FileFilter;

import java.io.*;

import javax.swing.*;

import java.beans.*;
public class AuRawInputFactory implements InputFactory,PropertyChangeListener
{
private AuInputOptions au;//panou Au
private RawInputOptions raw;//panou Raw
private int NumarCanale;
private JFileChooser AlegeFisier;
private FiltruFisierAu filtruAu;
private FiltruFisierRaw filtruRaw;
public AuRawInputFactory()
{
  AlegeFisier=new JFileChooser(); 
  au=new AuInputOptions();//panou optiuni pentru au
  raw=new RawInputOptions();//panou optiuni pentru raw
  filtruAu=new FiltruFisierAu();
  filtruRaw=new FiltruFisierRaw();
}
public String getName()
{  
  return "File Input (.au / .raw)";
}
public Input getInput(JDialog dialog)
{  
/*
----------------------------------------------------------------------------
      aici desfac o casuta de dialog pentru alegerea unui fisier .au
      dupa care fac cele necesare pentru a pregati citirea din acel fisier
-----------------------------------------------------------------------------
*/
  AlegeFisier.setMultiSelectionEnabled(false);
  AlegeFisier.setAcceptAllFileFilterUsed(false);//pentru ca sa nu mai apara filtru AllFiles
  AlegeFisier.setFileFilter(filtruAu);
  AlegeFisier.setFileFilter(filtruRaw);

//---------------------------------------------------------------------------
  JPanel suport=new JPanel();  
  suport.add(raw);
  suport.add(au);
  au.setVisible(false);
  AlegeFisier.setAccessory(suport);
  AlegeFisier.addPropertyChangeListener(this);  // To receive selection changes


//---------------------------------------------------------------------------
  int option=AlegeFisier.showOpenDialog(null);//aici pointer la fereastra parinte
  if (option == JFileChooser.APPROVE_OPTION)
      {
       File Fisier=AlegeFisier.getSelectedFile( );
       //aici iau optiunile octet/esantion etc in functie de terminatia fisierului 
       //selectat=>panoul corespunzator este in jfilechooser
       if(Fisier.getAbsolutePath().endsWith(".raw"))
       	{//daca am selectat un fisier .raw
       	 //obtin din panoul raw optiunile selectate
       	 boolean OctetiCuSemn=false;
      	 String OctetiPerEsantion;      	 
       	 Enumeration elemente=raw.group.getElements();
       	 String NumeButonSelectat=null;
       	 while(elemente.hasMoreElements())
       	 	{//aflu numele butonului selectat cel cu semn sau fara semn din button group
       	 	 JRadioButton manevra=(JRadioButton)elemente.nextElement();
       	 	 if(manevra.isSelected())
       	 	 	{
       	 	 	 NumeButonSelectat=manevra.getText();
       	 	  	 break;
       	 	 	}
       	 	}
       	 if(NumeButonSelectat.equals("Signed byte"))
       	  	OctetiCuSemn=true;
       	 OctetiPerEsantion=(String)raw.OctetPerEsantion.getSelectedItem();  
       	 return new RawInput(OctetiPerEsantion,OctetiCuSemn,Fisier);
       	}       	
/*--------------------------------------------------------------------------------       
  Pana aici am prelucrat cazul in care am selectat un fisier raw
  Urmeaza in if-ul urmator prelucrare in cazul in care am selectat un fisier au     
--------------------------------------------------------------------------------*/       
       if(Fisier.getAbsolutePath().endsWith(".au"))
      	{//daca am selectat un fisier .au
         try
		 	{
          	 Enumeration elemente=au.group.getElements();
           	 String NumeButonSelectat=null;
           	 while(elemente.hasMoreElements())
           	 	{//aflu numele butonului selectat cel cu semn sau fara semn din button group
           	 	 JRadioButton manevra=(JRadioButton)elemente.nextElement();
           	 	 if(manevra.isSelected())
           	 	 	{
           	 	 	 NumeButonSelectat=manevra.getText();
           	 	  	 break;
           	 	 	}
           	 	}
           	 if(NumeButonSelectat.equals("Read channel 1"))
           	  	NumarCanale=1;
           	 if(NumeButonSelectat.equals("Read channel 2"))
           		NumarCanale=2;
           	 if(NumeButonSelectat.equals("Read both channels"))
           		NumarCanale=12;
         	 AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(Fisier);         	 
         	 return new AuInput(audioInputStream,NumarCanale);
		 	}
         catch(Exception e)  
		 	{
         	 System.out.println("Eroare la deschiderea fisierului:"+e);    
		 	}
       	}       
       }
  else 
      {
      // Daca am apasat pe cancel  
      }

  return null;
}
public void propertyChange(PropertyChangeEvent e) 
{   FileFilter Filtru; 
    String NumeFiltru;
    if (JFileChooser.FILE_FILTER_CHANGED_PROPERTY.equals(e.getPropertyName( ))) 
       {
           //daca a fost schimbat filtrul
           Filtru=AlegeFisier.getFileFilter();
           NumeFiltru=Filtru.getDescription();
           if(NumeFiltru.endsWith(".raw)"))
            {
           	 au.setVisible(false);
             raw.setVisible(true);           
            }
           if(NumeFiltru.endsWith(".au)"))
            {
           	 raw.setVisible(false);
           	 au.setVisible(true);                       
            }
      }
}
}
