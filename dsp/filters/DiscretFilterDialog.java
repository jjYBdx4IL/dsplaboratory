/*
 * Created on Feb 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsp.filters;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Administrator
 */
public class DiscretFilterDialog extends JDialog {

    private int FMethod,FType,Fe,Ft,N;
    private boolean okeyed;
    
    private JPanel panouInput,panouOutput,panouSud,panouAfis;
    private JComboBox signalTypeComboBox,zoomInComboBox,signalTypeOutComboBox,zoomOutComboBox;
    private JButton ok, cancel;
    private ButtonGroup method, type;
    private JCheckBox afisareSemnalBox,afisareSemnalFiltratBox;
    private String zoom[]={"1x","2x","3x","4x","5x","6x","7x","8x","9x","10x"};
    
    public DiscretFilterDialog(Dialog parent) throws HeadlessException
    {
        super(parent, "FilterDialog", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initialize();
    }

    private void initialize()
    {
        JPanel leftPane = new JPanel();
        leftPane.setBorder(new TitledBorder(new EtchedBorder(), "Method"));
        
        JPanel middlePane = new JPanel();
        middlePane.setBorder(new TitledBorder(new EtchedBorder(), "Type"));
        
        JPanel rightPane = new JPanel();
        rightPane.setBorder(new TitledBorder(new EtchedBorder(), "Parameters"));
        
        leftPane.setLayout(new GridLayout(4, 1));
        ((GridLayout)leftPane.getLayout()).setVgap(5);
        ((GridLayout)leftPane.getLayout()).setHgap(5);
        
        rightPane.setLayout(new GridLayout(4, 1));
        ((GridLayout)rightPane.getLayout()).setVgap(5);
        ((GridLayout)rightPane.getLayout()).setHgap(5);
                
        middlePane.setLayout(new GridLayout(3, 2));
        ((GridLayout)middlePane.getLayout()).setVgap(5);
        ((GridLayout)middlePane.getLayout()).setHgap(5);
        
        method = new ButtonGroup();
        
        JRadioButton b1 = new JRadioButton("Fourier",true);
        method.add(b1);
        leftPane.add(b1);
        
        JRadioButton b2 = new JRadioButton("Fourier Discret");
        method.add(b2);
        leftPane.add(b2);
        
        JRadioButton b3 = new JRadioButton("Tansformata Z");
        method.add(b3);
        leftPane.add(b3);
        
        JRadioButton b4 = new JRadioButton("Biliniara");
        method.add(b4);
        leftPane.add(b4);

        type = new ButtonGroup();
        
        JRadioButton t1 = new JRadioButton("FTJ",true);
        type.add(t1);
        rightPane.add(t1);
        
        JRadioButton t2 = new JRadioButton("FTS");
        type.add(t2);
        rightPane.add(t2);
        
        JRadioButton t3 = new JRadioButton("FTB");
        type.add(t3);
        rightPane.add(t3);
        
        JRadioButton t4 = new JRadioButton("FOB");
        type.add(t4);
        rightPane.add(t4);
        
        middlePane.add(new JLabel("Frecventa de esantionare"));
        middlePane.add(new JTextField("200"));
        
        middlePane.add(new JLabel("Frecventa de taiere"));
        middlePane.add(new JTextField("50"));
        
        middlePane.add(new JLabel("Ordinul filtrului"));
        middlePane.add(new JTextField("15"));

        JPanel buttonPane = new JPanel();
     
        ok = new JButton(new OKAction());
        cancel = new JButton(new CancelAction());
        
        buttonPane.add(ok);
        buttonPane.add(cancel);
        buttonPane.setAlignmentX(0);
        
        JPanel downPane = new JPanel();
        
        downPane.setLayout(new BorderLayout(2, 1));
                
        downPane.add(middlePane,"North");
        downPane.add(buttonPane,"South");
        
        JPanel upPane = new JPanel();
        upPane.setLayout(new GridLayout(1, 2));
        upPane.add(leftPane,"West");
        upPane.add(rightPane,"East");
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(upPane,"North");
        getContentPane().add(downPane,"South");
        
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
        }

        public void actionPerformed(ActionEvent arg0)
        {
        	FMethod = method.getButtonCount();
        	
        	System.out.println(method.getSelection().toString());
        	//FType,Fe,Ft,N
 /*       	afisareSpectru = afisareSpectruCheckBox.isSelected();
            afisareSpectruOut= afisareSpectruOutCheckBox.isSelected();
            afisareSemnal= afisareSemnalBox.isSelected();
            afisareSemnalFiltrat= afisareSemnalFiltratBox.isSelected();
            signalType= (String)signalTypeComboBox.getSelectedItem();
            signalTypeOut= (String)signalTypeComboBox.getSelectedItem();
            zoomxIn=zoomInComboBox.getSelectedIndex();
            zoomxOut=zoomOutComboBox.getSelectedIndex();
 */           
            okeyed = true;
            dispose();
        }
        
    }
   
    
    public boolean isOK()
    {
        return okeyed;
    }
	public static void main(String[] args) {
	}
}
