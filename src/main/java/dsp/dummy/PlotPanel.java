/*
 * Created on Nov 21, 2004
 *
 */
package dsp.dummy;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author Luke
 */
public class PlotPanel extends JPanel
{
    PlotModel model;

    public PlotPanel(PlotModel m)
    {
        super();
        setPreferredSize(new Dimension(600, 200));
        setBackground(Color.black);
        setForeground(Color.white);
        model = m;
    }

    public PlotModel getModel()
    {
        return model;
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int oldx = 0, oldy = getHeight() / 2;
        int newx, newy;
        for (int i = 0; i < model.getCount(); i++)
        {
            // Pentru afisare "circulara" am adaugat % getWidth()
            synchronized (model)
            {
                newx = model.getX(i, getWidth()) % getWidth();
                newy = model.getY(i, getHeight());
            }   
            g.setColor(Color.black);
            g.fillRect(newx, 0, 2, getHeight());

            // La reintoarcere sa nu mai traga linia 
            if (newx != 0)
            {
                g.setColor(Color.white);
                g.drawLine(oldx, oldy, newx, newy);
            }
            oldx = newx;
            oldy = newy;
        }
    }

}
