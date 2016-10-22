/*
 * Created on Nov 14, 2004
 *
 */
package dsplaboratory;

import dsplaboratory.exception.RecoverableInputProblem;

import java.util.Calendar;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * 
 * @author Luke
 */
public class WorkerThread extends Thread
{
    protected Input in;
    protected Output out;
    protected Filter filter;
    protected Analyzer inAnalyzer, outAnalyzer;
    protected boolean stopping = false;
    protected float loopDelay;
    protected JFrame mainFrame;

    public WorkerThread(Input i, Output o, Filter f, Analyzer inA, Analyzer outA,
            float delay, JFrame mainFrame)
    {
        in = i;
        out = o;
        filter = f;
        inAnalyzer = inA;
        outAnalyzer = outA;
        loopDelay = delay;
        this.mainFrame = mainFrame;
    }

    public void shutDown()
    {
        stopping = true;
    }

    public void run()
    {
        // initialisation
        in.start();
        filter.start();
        out.start();
        inAnalyzer.start();
        outAnalyzer.start();

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                mainFrame.pack();
            }
        });

        // delay accumulated, in miliseconds
        float runningDelay = 0f;
        long oldTime = Calendar.getInstance().getTimeInMillis();

        setPriority(MAX_PRIORITY);
        // run loop
        while (!stopping && !in.endOfInput())
        {
            try
            {
                DataChunk raw = in.getNext();
                DataChunk processed = filter.process(raw);
                inAnalyzer.putNext(raw);
                outAnalyzer.putNext(processed);
                out.putNext(processed);

                runningDelay += loopDelay * raw.getSize();
                if (runningDelay > 1)
                {
                    long newTime = Calendar.getInstance().getTimeInMillis();
                    if (newTime - oldTime > runningDelay)
                    {
                        oldTime = newTime;
                        runningDelay = 0f;
                    }
                    else
                    {
                        runningDelay -= newTime - oldTime;
                        int delay = (int) Math.floor(runningDelay);
                        Thread.sleep(delay);
                        runningDelay -= delay;
                        oldTime = Calendar.getInstance().getTimeInMillis();
                    }
                }
            }
            catch (RecoverableInputProblem rip)
            {
                if (in instanceof RealTimeInput)
                    loopDelay += ((RealTimeInput) in).getDelayCorrection();
                continue;
            }
            catch (InterruptedException e)
            {
                // problema, nu are de ce sa fie intrerupta
                System.err.println("WorkerThread interrupted.");
            }
        }
        setPriority(NORM_PRIORITY);

        // cleanup
        in.stop();
        filter.stop();
        out.stop();
        inAnalyzer.stop();
        outAnalyzer.stop();

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                ((MainFrame) mainFrame).setup.setEnabled(true);
                ((MainFrame) mainFrame).start.setEnabled(true);
                ((MainFrame) mainFrame).stop.setEnabled(false);
            }
        });
    }

}