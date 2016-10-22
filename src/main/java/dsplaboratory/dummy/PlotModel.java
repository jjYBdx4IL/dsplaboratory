/*
 * Created on Nov 21, 2004
 *
 */
package dsplaboratory.dummy;

import java.util.Vector;

/**
 *
 * @author Luke
 */
public class PlotModel
{
    Vector<Double> vx, vy;
    double xMin, yMin, xMax, yMax;
    int alloc;
    
    public PlotModel(double xmin, double ymin, double xmax, double ymax)
    {
        xMin = xmin;
        xMax = xmax;
        yMin = ymin;
        yMax = ymax;
        alloc = (int)Math.round(xmax - xmin) + 1;
        vx = new Vector<>(alloc);
        vy = new Vector<>(alloc);
    }

    public synchronized void addPoint(double x, double y)
    {
        if (x > xMax)
        {
            double delta = xMax - xMin;
            xMin += delta;
            xMax += delta;
            vx = new Vector<>(alloc);
            vy = new Vector<>(alloc);
        }
            
        vx.add(new Double(x));
        vy.add(new Double(y));
    }

    public synchronized int getCount()
    {
        //assert vx.size() == vy. size();
        return vx.size();
    }
    
    public int getX(int position, int width)
    {
        double x = ((Double)vx.get(position)).doubleValue();
        return (int)Math.round((x - xMin) * width / (xMax - xMin));
    }

    public int getY(int position, int height)
    {
        double y = ((Double)vy.get(position)).doubleValue();
        return (height / 2) - (int)Math.round((y - yMin) * height / (yMax - yMin));
    }

}
