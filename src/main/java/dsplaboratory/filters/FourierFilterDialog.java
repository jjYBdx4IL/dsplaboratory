/*
 * Created on Jan 28, 2005
 */
package dsplaboratory.filters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Irina
 */

public class FourierFilterDialog extends JDialog 
{
    private int Fe, Ft, Ft2, N;
    private String FType;
    private boolean okeyed;
    
    static private JTextField FeField, FtField, Ft2Field, NField;
    
    private JPanel leftPane, rightPane, middlePane, buttonPane, downPane, upPane;
    private JButton ok, cancel;
    private ButtonGroup type;
    
    
    public FourierFilterDialog(Dialog parent) throws HeadlessException
    {
        super(parent, "Fourier Filter Configuration", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initialize();
    }

    private void initialize()
    {
        leftPane = new JPanel();
                
        middlePane = new JPanel();
        middlePane.setBorder(new TitledBorder(new EtchedBorder(), "Type"));
        
        rightPane = new JPanel();
        rightPane.setBorder(new TitledBorder(new EtchedBorder(), "Parameters"));
        
        leftPane.setLayout(new GridLayout(2, 1));
        ((GridLayout)leftPane.getLayout()).setVgap(5);
        ((GridLayout)leftPane.getLayout()).setHgap(5);
        
        rightPane.setLayout(new GridLayout(4, 1));
        ((GridLayout)rightPane.getLayout()).setVgap(5);
        ((GridLayout)rightPane.getLayout()).setHgap(5);
                
        middlePane.setLayout(new GridLayout(5, 2));
        ((GridLayout)middlePane.getLayout()).setVgap(5);
        ((GridLayout)middlePane.getLayout()).setHgap(5);
        
        JLabel m1 = new JLabel("Filtru Fourier");
        m1.setHorizontalAlignment(JLabel.CENTER);
        m1.setVerticalAlignment(JLabel.BOTTOM);
        m1.setSize(new Dimension(500,10));
        leftPane.add(m1);
        JLabel m2 = new JLabel("Nerecursiv");
        m2.setHorizontalAlignment(JLabel.CENTER);
        m2.setVerticalAlignment(JLabel.TOP);
        leftPane.add(m2);
        
        type = new ButtonGroup();
        
        JRadioButton t1 = new JRadioButton("FTJ",true);
        t1.setActionCommand("FTJ");
        type.add(t1);
        rightPane.add(t1);
        
        JRadioButton t2 = new JRadioButton("FTS");
        t2.setActionCommand("FTS");
        type.add(t2);
        rightPane.add(t2);
        
        JRadioButton t3 = new JRadioButton("FTB");
        t3.setActionCommand("FTB");
        type.add(t3);
        rightPane.add(t3);
        
        JRadioButton t4 = new JRadioButton("FOB");
        t4.setActionCommand("FOB");
        type.add(t4);
        rightPane.add(t4);
        
        middlePane.add(new JLabel("Frecventa de esantionare"));
        FeField = new JTextField("200");
        middlePane.add(FeField);
        
        middlePane.add(new JLabel("Frecventa de taiere"));
        FtField = new JTextField("50");
        middlePane.add(FtField);
        
        middlePane.add(new JLabel("Ordinul filtrului"));
        NField = new JTextField("15");
        if (t3.isSelected()) NField.disable();
        middlePane.add(NField);
        
        middlePane.add(new JLabel("Pentru FTB sau FOB:"));
        middlePane.add(new JLabel(""));
        middlePane.add(new JLabel("Frecventa de taiere 2"));
        Ft2Field = new JTextField("100");
        middlePane.add(Ft2Field);
        
        

        buttonPane = new JPanel();
     
        ok = new JButton(new OKAction());
        cancel = new JButton(new CancelAction());
        
        buttonPane.add(ok);
        buttonPane.add(cancel);
        buttonPane.setAlignmentX(0);
        
        downPane = new JPanel();
        
        downPane.setLayout(new BorderLayout(2, 1));
                
        downPane.add(middlePane,"North");
        downPane.add(buttonPane,"South");
        
        upPane = new JPanel();
        upPane.setLayout(new GridLayout(1, 2));
        upPane.add(leftPane,"West");
        upPane.add(rightPane,"East");
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(upPane,"North");
        getContentPane().add(downPane,"South");
        
        pack();     
    }
    
    public int getFe()
    {
    	return Fe;
    }
    
    public int getFt()
    {
    	return Ft;
    }
    
    public int getFt2()
    {
    	return Ft2;
    }
    
    public int getOrdin()
    {
    	return N;
    }

    public int getFType()
    {
    	if (FType.equals("FTJ"))
    		return 1;
    	if (FType.equals("FTS"))
    		return 1;
    	if (FType.equals("FTB"))
    		return 1;
    	if (FType.equals("FOB"))
    		return 1;
    	return 0;	
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
        	FType = type.getSelection().getActionCommand();
        	
        	Fe = Integer.parseInt(FeField.getText());
        	Ft = Integer.parseInt(FtField.getText());
        	Ft2 = Integer.parseInt(Ft2Field.getText());
        	N = Integer.parseInt(NField.getText());
        	
        	//FType,Fe,Ft,N
            okeyed = true;
            dispose();
        }
        
    }
   
    
    public boolean isOK()
    {
        return okeyed;
    }
}
