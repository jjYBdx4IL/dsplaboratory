/*
 * Created on Nov 21, 2004
 *
 */
package dsplaboratory.dummy;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import dsplaboratory.SetupDialog;

/**
 * 
 * @author Luke
 */
public class DummyInputDialog extends JDialog
{
    private int sampleCount, amplitude, sinFreq, samplingFreq;
    private boolean okeyed = false;

    private JLabel sampleCountLabel, amplitudeLabel, sinFreqLabel,
            samplingFreqLabel;
    private JTextField sampleCountField, amplitudeField,
            sinFreqField, samplingFreqField;
    private JButton ok, cancel;

    public boolean isOK()
    {
        return okeyed;
    }

    public int getAmplitude()
    {
        return amplitude;
    }

    public int getSampleCount()
    {
        return sampleCount;
    }

    public int getSamplingFreq()
    {
        return samplingFreq;
    }

    public int getSinFreq()
    {
        return sinFreq;
    }

    public DummyInputDialog(Dialog parent) throws HeadlessException
    {
        super(parent, true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        sampleCountLabel = new JLabel("Numar de esantioane");
        amplitudeLabel = new JLabel("Amplitudine");
        sinFreqLabel = new JLabel("Frecventa sin");
        samplingFreqLabel = new JLabel("Frecventa de esantionare");

        sampleCountField = new JTextField("500");
        amplitudeField = new JTextField("5");
        sinFreqField = new JTextField("7");
        samplingFreqField = new JTextField("30");

        ok = new JButton(new OKAction());
        cancel = new JButton(new CancelAction());

        getContentPane().setLayout(new GridLayout(5, 2));
        ((GridLayout)getContentPane().getLayout()).setVgap(5);
        ((GridLayout)getContentPane().getLayout()).setHgap(5);
        getContentPane().add(sampleCountLabel);
        getContentPane().add(sampleCountField);
        getContentPane().add(amplitudeLabel);
        getContentPane().add(amplitudeField);
        getContentPane().add(sinFreqLabel);
        getContentPane().add(sinFreqField);
        getContentPane().add(samplingFreqLabel);
        getContentPane().add(samplingFreqField);
        getContentPane().add(ok);
        getContentPane().add(cancel);
        
        pack();
    }

    private class OKAction extends AbstractAction
    {
        public OKAction()
        {
            super("OK");
        }
        
        public void actionPerformed(ActionEvent arg0)
        {
            sampleCount = Integer.parseInt(sampleCountField.getText());
            amplitude = Integer.parseInt(amplitudeField.getText());
            sinFreq = Integer.parseInt(sinFreqField.getText());
            samplingFreq = Integer.parseInt(samplingFreqField.getText());
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

    /**
     * Doar pentru teste, nu de aici se porneste aplicatia ! vezi DSPLaboratory
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager
                    .getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            // just give it up, go with default l&f
        }
        JDialog tmp = new DummyInputDialog(null);
        tmp.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        tmp.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                System.exit(0);
            }
        });
        tmp.setVisible(true);
    }

}