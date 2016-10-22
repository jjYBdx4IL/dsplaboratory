/*
 * Created on Nov 15, 2004
 *
 */
package dsp;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import dsp.dummy.*;
import dsp.exception.UserCancelled;

/**
 *
 * @author Luke
 */
public class MainFrame extends JFrame
{
    JButton setup, start, stop;
    WorkerThread worker;
    JPanel north;
    Component visualisation;
    SetupDialog setupDialog;
    
    public MainFrame()
    {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        setup = new JButton(new SetupAction("Setup"));
        setup.setEnabled(true);
        start = new JButton(new StartAction("Start"));
        start.setEnabled(false);
        stop = new JButton(new StopAction("Stop"));
        stop.setEnabled(false);
        north = new JPanel();
        north.setBorder(new TitledBorder(new EtchedBorder(), "Controls"));        
        getContentPane().add(north, BorderLayout.NORTH);
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        north.setLayout(fl);
        setup.setAlignmentX(LEFT_ALIGNMENT);
        north.add(setup);
        north.add(start);
        north.add(stop);
        setSize(600, 300);
        setLocation(100, 100);
        setTitle("Digital Signal Processing Laboratory");
        
        setupDialog = new SetupDialog(MainFrame.this);
    }

    protected class StartAction extends AbstractAction
    {
        
        public StartAction(String arg0)
        {
            super(arg0);
        }
        
        public void actionPerformed(ActionEvent arg0)
        {
            if (worker != null && !worker.isAlive())
            {
                worker.start();
                stop.setEnabled(true);
                start.setEnabled(false);
                setup.setEnabled(false);
            }
        }
    }

    protected class StopAction extends AbstractAction
    {
        
        public StopAction(String arg0)
        {
            super(arg0);
        }
        
        public void actionPerformed(ActionEvent arg0)
        {
            if (worker != null && worker.isAlive())
            {
                worker.shutDown();
                start.setEnabled(false);
                setup.setEnabled(true);
                stop.setEnabled(false);
                worker = null;
            }
            else if (!worker.isAlive())
            {
                start.setEnabled(false);
                setup.setEnabled(true);
                stop.setEnabled(false);
                worker = null;
            }
        }
    }
    
    protected class SetupAction extends AbstractAction
    {
        
        private JPanel visPanel;

        public SetupAction(String arg0)
        {
            super(arg0);
            visPanel = new JPanel();
        }
        
        public void actionPerformed(ActionEvent arg0)
        {
            if (worker != null && worker.isAlive())
                return;
            
            setupDialog.setVisible(true);
            
            worker = setupDialog.getWorkerThread();
            if (worker == null)
                return;
            visPanel.removeAll();
            visualisation = setupDialog.getVisualisation();
            visPanel.setBorder(new TitledBorder(new EtchedBorder(), "Visualisation"));
            visPanel.add(visualisation);
            getContentPane().add(visPanel, BorderLayout.CENTER);
            start.setEnabled(true);
        }
    }
    
}
