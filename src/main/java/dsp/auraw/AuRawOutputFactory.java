
/**
 * Write a description of class AuRawOutputFactory here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package dsp.auraw;
import java.beans.PropertyChangeEvent;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.*;
import java.beans.*;
import dsp.*;
public class AuRawOutputFactory implements OutputFactory,PropertyChangeListener
{
private JFileChooser AlegeFisier;
private FileFilter Filtru; 
private String NumeFiltru;
private AuOutputOptions au;
private String cale;
private JPanel suport;
private FiltruFisierAu filtruAu;
private FiltruFisierRaw filtruRaw;
public AuRawOutputFactory()
{
 AlegeFisier=new JFileChooser();
 au=new AuOutputOptions();
 filtruAu=new FiltruFisierAu();
 filtruRaw=new FiltruFisierRaw();
}

public String getName()
{ 
   return "File Output (.raw)";
}
    
    /**
     * Intreaba utilizatorul prin intermediul unui GUI propriu, daca Raw sau Au
     * si intoarce un obiect Output corespunzator 
     * @return Output
     */
public Output getOutput(JDialog parent, int frequency)
{//creez o casuta save unde intreb numele fisierului si unde sa 
//il salvez si ce tip de fisier prin intermediul filtrelor si intorc un 
//obiect corespunzator in care creez un astfel de fisier si cu putNext pun 
//datachunckuri in el	
	
 AlegeFisier.setMultiSelectionEnabled(false);
 AlegeFisier.setAcceptAllFileFilterUsed(false);//pentru ca sa nu mai apara filtru AllFiles
 AlegeFisier.setFileFilter(filtruAu);
 AlegeFisier.setFileFilter(filtruRaw);
 AlegeFisier.addPropertyChangeListener(this); 
 suport=new JPanel();
 suport.add(au);
 suport.setVisible(false);
 AlegeFisier.setAccessory(suport);
 int option=AlegeFisier.showSaveDialog(null);//aici pointer la fereastra parinte
 if (option == JFileChooser.APPROVE_OPTION)
 	{
 	 File Fisier=AlegeFisier.getSelectedFile( );
     if(Fisier.getAbsolutePath().endsWith(".raw"))
 	 	{
     	 //creez obiectul RawOutput cu parametrul Fisier
     	 return new RawOutput(Fisier);
 	 	}
     if(Fisier.getAbsolutePath().endsWith(".au"))
	 	{
  	     //creez obiectul auOutput cu parametrul de scriere intrun fisier au
     	
     	 return new AuOutput((String)au.SampleRateCombo.getSelectedItem(),(String)au.SampleSizeInBitsCombo.getSelectedItem(),(String)au.ChannelsCombo.getSelectedItem(),(String)au.SignedCombo.getSelectedItem(),(String)au.BigEndianCombo.getSelectedItem(),Fisier);
	 	}
 
     Filtru=AlegeFisier.getFileFilter();
     NumeFiltru=Filtru.getDescription();
     if(NumeFiltru.endsWith(".raw)"))
     	{
     	 cale=Fisier.getAbsolutePath()+".raw";
     	 File f=new File(cale);
     	 return new RawOutput(f);
     	}
     	
     if(NumeFiltru.endsWith(".au)"))
     	{
     	 cale=Fisier.getAbsolutePath()+".au";
     	 File f=new File(cale);
     	 return new AuOutput((String)au.SampleRateCombo.getSelectedItem(),(String)au.SampleSizeInBitsCombo.getSelectedItem(),(String)au.ChannelsCombo.getSelectedItem(),(String)au.SignedCombo.getSelectedItem(),(String)au.BigEndianCombo.getSelectedItem(),Fisier); 
     	}
     	
    }
 else
 	{
 	 //daca am apasat pe cancel
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
           	 suport.setVisible(false);
            }
           if(NumeFiltru.endsWith(".au)"))
            {
           	 suport.setVisible(true);
            }
      }
}
}
