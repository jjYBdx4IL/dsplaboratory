/*
 * Created on Nov 19, 2004
 *
 */
package dsp;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.*;

import dsp.dummy.DummyVisFactory;
import dsp.dummy.TestFilter;
import dsp.exception.UserCancelled;
import dsp.grafix.GrafixVisFactory;
import dsp.soundinput.SoundInputFactory;
import dsp.soundinput.SoundOutputFactory;

/**
 * 
 * @author Luke
 */
public class SetupDialog extends JDialog
{
    protected WorkerThread workerThread;

    protected JButton setInput, setFilter, setOutput,
            setVisualisation;
    protected JCheckBox inputDone, filterDone, outputDone,
            visualisationDone;
    protected JComboBox inputChoice, filterChoice, outputChoice,
            visualisationChoice;

    protected JCheckBox slowDownBox;
    protected JLabel slowDownLabel;
    protected JTextField slowDownDelay;
    
    protected Input input;
    protected Filter filter;
    protected Output output;
    protected Analyzer inAnalyzer, outAnalyzer;
    protected Component visualisation;

    protected JButton ok, cancel;

    /*
     * Aici trebuie adaugate factory-urile, practic aici este locul in care
     * celelalte module se "lipesc" in program. Un factory trebuie sa reprezinte
     * pe cat posibil o categorie logica de obiecte, poate sa produca un singur
     * obiect, sau zeci, dupa caz. La input sunt doua factory-uri doar pentru
     * exemplificare, de fapt cele doua nu merita sa fie independente.
     */
    protected String[] inputChoices; // = new String[] {
//        "Basic", "Sound", "Dummy", "Constant", "Wave", "Au / Raw", "Voc"};
    protected InputFactory[] inputFactories = new InputFactory[] {
        new dsp.basicinput.BasicInputFactory(),
        new dsp.soundinput.SoundInputFactory(),
//        new dsp.serial.SerialInputFactory(),
        new dsp.dummy.ConstantInputFactory(),
        new dsp.wave.WaveInputFactory(),
        new dsp.auraw.AuRawInputFactory(),
        new dsp.voc.VocInputFactory()};

    protected String[] filterChoices; // = new String[] {
//        "Dummy", "Mediere-Test"};
    protected FilterFactory[] filterFactories = new FilterFactory[] {
        new dsp.filters.DiscretFourierFilterFactory(),
        new dsp.filters.FourierFilterFactory(),
        new dsp.dummy.DummyFilterFactory(),
        new dsp.dummy.TestFilterFactory(),
        new dsp.dummy.MiscFilterFactory()};

    protected String[] outputChoices; // = new String[] { "Dummy", "Au / Raw"};
    protected OutputFactory[] outputFactories = new OutputFactory[] {
        new dsp.dummy.DummyOutputFactory(),
        new dsp.auraw.AuRawOutputFactory(),
        new dsp.soundinput.SoundOutputFactory(),
        new dsp.voc.VocOutputFactory()};

    protected String[] visualisationChoices = new String[] { "Vizualizare", "Test"};
    protected VisualisationFactory[] visualisationFactories = new VisualisationFactory[] {
        new dsp.grafix.GrafixVisFactory(),
        new DummyVisFactory() };

    public SetupDialog(Frame parent) throws HeadlessException
    {
        super(parent, true);
        setTitle("Setup");
        Container pane = getContentPane();
        Box upBox = new Box(BoxLayout.X_AXIS);
        JPanel leftPane = new JPanel();
        leftPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        // Input Config

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputChoices = new String[inputFactories.length];
        for (int i = 0; i < inputFactories.length; i++)
        {
            inputChoices[i] = inputFactories[i].getName();
        }
        inputChoice = new JComboBox(inputChoices);
        leftPane.add(inputChoice, gbc);

        gbc.gridx = 1;
        setInput = new JButton(new ConfigInput());
        leftPane.add(setInput, gbc);

        gbc.gridx = 2;
        inputDone = new JCheckBox();
        inputDone.setEnabled(false);
        leftPane.add(inputDone, gbc);

        // Filter Config
        gbc.gridy = 1;
        gbc.gridx = 0;
        filterChoices = new String[filterFactories.length];
        for (int i = 0; i < filterFactories.length; i++)
        {
            filterChoices[i] = filterFactories[i].getName();
        }
        filterChoice = new JComboBox(filterChoices);
        leftPane.add(filterChoice, gbc);

        gbc.gridx = 1;
        setFilter = new JButton(new ConfigFilter());
        setFilter.setEnabled(false);
        leftPane.add(setFilter, gbc);

        gbc.gridx = 2;
        filterDone = new JCheckBox();
        filterDone.setEnabled(false);
        leftPane.add(filterDone, gbc);

        // Output Config
        gbc.gridy = 2;
        gbc.gridx = 0;
        outputChoices = new String[outputFactories.length];
        for (int i = 0; i < outputFactories.length; i++)
        {
            outputChoices[i] = outputFactories[i].getName();
        }
        outputChoice = new JComboBox(outputChoices);
        leftPane.add(outputChoice, gbc);

        gbc.gridx = 1;
        setOutput = new JButton(new ConfigOutput());
        setOutput.setEnabled(false);
        leftPane.add(setOutput, gbc);

        gbc.gridx = 2;
        outputDone = new JCheckBox();
        outputDone.setEnabled(false);
        leftPane.add(outputDone, gbc);

        // Analisys Config
        gbc.gridy = 3;
        gbc.gridx = 0;
        visualisationChoice = new JComboBox(visualisationChoices);
        leftPane.add(visualisationChoice, gbc);

        gbc.gridx = 1;
        setVisualisation = new JButton(new ConfigVisualisation());
        setVisualisation.setEnabled(false);
        leftPane.add(setVisualisation, gbc);

        gbc.gridx = 2;
        visualisationDone = new JCheckBox();
        visualisationDone.setEnabled(false);
        leftPane.add(visualisationDone, gbc);

        leftPane.setBorder(new TitledBorder(new EtchedBorder(), "Modules"));
        
        JPanel rightPane = new JPanel();
        rightPane.setBorder(new TitledBorder(new EtchedBorder(), "Options"));
        
        slowDownBox = new JCheckBox(new DelayBoxAction());
        rightPane.add(slowDownBox);
        slowDownLabel = new JLabel(" Duration(ms):");
        slowDownLabel.setEnabled(false);
        rightPane.add(slowDownLabel);
        slowDownDelay = new JTextField(10);
        slowDownDelay.setEnabled(false);
        rightPane.add(slowDownDelay);
        
        
        ok = new JButton(new OKAction());
        cancel = new JButton(new AbstractAction("Cancel") 
            {
                public void actionPerformed(ActionEvent arg0)
                {
                    dispose();
                }
            });

        upBox.add(leftPane);
        upBox.add(rightPane);

        JPanel buttonPane = new JPanel(); 
        
        buttonPane.add(ok);
        buttonPane.add(cancel);
        
        pane.add(upBox);
        pane.add(buttonPane, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        pack();
        setLocation(200, 200);
        setResizable(false);
    }

    public WorkerThread getWorkerThread()
    {
        return workerThread;
    }

    public Component getVisualisation()
    {
        return visualisation;
    }

    protected class ConfigInput extends AbstractAction
    {

        public ConfigInput()
        {
            super("Configure Input");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            int pos = inputChoice.getSelectedIndex();
            try
            {
                input = inputFactories[pos]
                        .getInput(SetupDialog.this);
                inputDone.setSelected(true);
                //inputChoice.setEnabled(false);
                //setInput.setEnabled(false);
                setFilter.setEnabled(true);
                float delay = 0;
                if (input.getFrequency() > 0)
                	delay = 1000.0f / input.getFrequency();
                if (delay > 0)
                {
                    DecimalFormat df = new DecimalFormat("0.######");
                    slowDownDelay.setText(
                        df.format(delay));
                    if (!slowDownBox.isSelected())
                        slowDownBox.doClick();
                }
            }
            catch (UserCancelled uc)
            {
                //ignore results
            }
        }

    }

    protected class ConfigFilter extends AbstractAction
    {

        public ConfigFilter()
        {
            super("Configure Filter");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            int pos = filterChoice.getSelectedIndex();
            try
            {
                filter = filterFactories[pos].getFilter(
                        SetupDialog.this, input.getFrequency());
                filterDone.setSelected(true);
                //filterChoice.setEnabled(false);
                //setFilter.setEnabled(false);
                setOutput.setEnabled(true);
            }
            catch (UserCancelled uc)
            {
                //ignore results
            }
        }

    }

    protected class ConfigOutput extends AbstractAction
    {

        public ConfigOutput()
        {
            super("Configure Output");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            int pos = outputChoice.getSelectedIndex();
            try
            {
                output = outputFactories[pos].getOutput(
                        SetupDialog.this, input.getFrequency());
                outputDone.setSelected(true);
                //outputChoice.setEnabled(false);
                //setOutput.setEnabled(false);
                setVisualisation.setEnabled(true);
            }
            catch (UserCancelled uc)
            {
                //ignore results
            }
        }

    }

    protected class ConfigVisualisation extends AbstractAction
    {

        public ConfigVisualisation()
        {
            super("Configure Visualisation");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            int pos = visualisationChoice.getSelectedIndex();
            try
            {
                inAnalyzer = visualisationFactories[pos]
                        .getInputAnalyzer(SetupDialog.this, input
                                .getFrequency());
                outAnalyzer = visualisationFactories[pos]
                        .getOutputAnalyzer();
                visualisation = visualisationFactories[pos]
                        .getVisualisation();
                visualisationDone.setSelected(true);
                //visualisationChoice.setEnabled(false);
                //setVisualisation.setEnabled(false);
            }
            catch (UserCancelled uc)
            {
                //ignore results
            }
        }

    }

    protected class OKAction extends AbstractAction
    {

        public OKAction()
        {
            super("OK");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            float delay = 0f;
            if (slowDownBox.isSelected())
                try
                {
                    delay = Float.parseFloat(slowDownDelay.getText());
                    if (delay <= 0f)
                        throw new NumberFormatException();
                }
                catch (NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(SetupDialog.this,
                    "Wrong Delay : \n Float > 0 ");
                    return;
                }
            
            if (inputDone.isSelected() && filterDone.isSelected()
                    && outputDone.isSelected()
                    && visualisationDone.isSelected())
            {
                workerThread = new WorkerThread(input, output,
                        filter, inAnalyzer, outAnalyzer, delay,
                        (JFrame)SetupDialog.this.getParent());
                dispose();
            }
            else
                JOptionPane.showMessageDialog(SetupDialog.this,
                        "Configuratie Incompleta");
        }

    }
    
    public class DelayBoxAction extends AbstractAction
    {
        public DelayBoxAction()
        {
            super("Use Delay");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            slowDownDelay.setEnabled(slowDownBox.isSelected());
            slowDownLabel.setEnabled(slowDownBox.isSelected());
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
        JDialog tmp = new SetupDialog(null);
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