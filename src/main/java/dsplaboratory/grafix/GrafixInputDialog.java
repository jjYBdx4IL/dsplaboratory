/*
 * Created on 18.11.2004
 *  
 */
package dsplaboratory.grafix;

import javax.swing.JDialog;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
/**
 * @author marius
 *  
 */
public class GrafixInputDialog extends JDialog
{
    private boolean afisareSpectru,afisareSpectruOut,okeyed,afisareSemnal,afisareSemnalFiltrat;
    private String signalType,signalTypeOut;
    private boolean signed;
    private int zoomXIn,zoomXOut,zoomYIn,zoomYOut;
    
    private JPanel panouInput,panouOutput,panouSud,panouAfis;
    private JComboBox signalTypeComboBox,zoomInXComboBox,zoomInYComboBox,signalTypeOutComboBox,zoomOutXComboBox,zoomOutYComboBox;
    private JButton ok, cancel;
    private JCheckBox signedCheckBox,afisareSpectruCheckBox,afisareSpectruOutCheckBox;
    private JCheckBox afisareSemnalBox,afisareSemnalFiltratBox;
    private String zoom[]={"1x","2x","3x","4x","5x","6x","7x","8x","9x","10x"};
    
    public GrafixInputDialog(Dialog parent) throws HeadlessException
    {
        super(parent, "GrafixInputDialog", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initialize();
    }

    private void initialize()
    {
    	GridLayout gridL=new GridLayout(7,2);
//    	gridL.setColumns(5);
    	gridL.setHgap(5);
    	gridL.setVgap(5);

    	panouInput=new JPanel(gridL);
    	panouOutput=new JPanel(gridL);
    	panouAfis=new JPanel();
    	panouSud=new JPanel();
    	String[] signals = {"Real","Normal" };
        signalTypeComboBox = new JComboBox(signals);
        signalTypeComboBox.setSelectedIndex(0);
        afisareSpectruCheckBox = new JCheckBox("Vizualizare Spectru");
        afisareSpectruCheckBox.setSelected(true);
        zoomInXComboBox=new JComboBox(zoom); 
        zoomInYComboBox=new JComboBox(zoom); 
         
        
        signalTypeOutComboBox = new JComboBox(signals);
        signalTypeOutComboBox.setSelectedIndex(0);
        zoomOutXComboBox=new JComboBox(zoom); 
        zoomOutYComboBox=new JComboBox(zoom); 
        
        afisareSpectruOutCheckBox = new JCheckBox("Vizualizare Spectru");
        afisareSemnalFiltratBox=new JCheckBox("Afisare Semnal Filtrat ");
        afisareSemnalBox=new JCheckBox("Afisare Semnal");
        afisareSemnalBox.setSelected(true);                
        ok = new JButton(new OKAction());
        cancel = new JButton(new CancelAction());
        
        panouInput.add(new JLabel("Tip Semnal"));
        panouInput.add(signalTypeComboBox);
        panouInput.add(afisareSpectruCheckBox);
        panouInput.add(afisareSemnalBox);
        panouInput.add(new JLabel("ZOOM X"));
        panouInput.add(zoomInXComboBox);
        panouInput.add(new JLabel("ZOOM Y"));
        panouInput.add(zoomInYComboBox);
        

        panouOutput.add(new JLabel("Tip Semnal"));
        panouOutput.add(signalTypeOutComboBox);
        panouOutput.add(afisareSpectruOutCheckBox);
        panouOutput.add(afisareSemnalFiltratBox);
        panouOutput.add(new JLabel("ZOOM X"));
        panouOutput.add(zoomOutXComboBox);
        panouOutput.add(new JLabel("ZOOM Y"));
        panouOutput.add(zoomOutYComboBox);
        
        panouInput.setBorder(new TitledBorder(new EtchedBorder(), "Panou Semnal Intrare"));
        panouOutput.setBorder(new TitledBorder(new EtchedBorder(), "Panou Semnal Iesire"));
        
        panouSud.add(ok);
        panouSud.add(cancel);
        panouSud.setAlignmentX(0);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panouInput,"West");
        getContentPane().add(panouOutput,"East");
        getContentPane().add(panouSud,"South");
        pack();        
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
            okeyed = false;
        }

    }
    private class OKAction extends AbstractAction
    {
        public OKAction()
        {
            super("OK");
            okeyed=true;
            signed=true;
        }

        public void actionPerformed(ActionEvent arg0)
        {
        	afisareSpectru = afisareSpectruCheckBox.isSelected();
            afisareSpectruOut= afisareSpectruOutCheckBox.isSelected();
            afisareSemnal= afisareSemnalBox.isSelected();
            afisareSemnalFiltrat= afisareSemnalFiltratBox.isSelected();
            signalType= (String)signalTypeComboBox.getSelectedItem();
            signalTypeOut= (String)signalTypeComboBox.getSelectedItem();
            zoomXIn=zoomInXComboBox.getSelectedIndex();
            zoomXOut=zoomOutXComboBox.getSelectedIndex();
            zoomYIn=zoomInYComboBox.getSelectedIndex();
            zoomYOut=zoomOutYComboBox.getSelectedIndex();
            
            okeyed = true;
            dispose();
        }
        
    }
    public int getzoomXIn()
    {
    return zoomXIn+1;
    }
    public int getzoomXOut()
    {
    return zoomXOut+1;
    }
    public int getzoomYIn()
    {
    return zoomYIn+1;
    }
    public int getzoomYOut()
    {
    return zoomYOut+1;
    }
    public boolean getAfisareSemnal()
    {
    return afisareSemnal;
    }
    public boolean getAfisareSemnalFiltrat()
    {
    return afisareSemnalFiltrat;
    }
    public boolean getAfisareSpectruOut()
    {
    return afisareSpectruOut;
    }
    public boolean getAfisareSpectru()
    {
        return afisareSpectru;
    }
    public String getSignalType()
    {
    	return signalType; 
    }
    public String getSignalTypeOut()
    {
    	return signalTypeOut; 
    }
    
    public boolean isOK()
    {
        return okeyed;
    }
    

    
}