/*
 * Created on Nov 15, 2004
 *
 */
package dsplaboratory;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author Luke
 */
public class DSPLaboratory
{

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            // just give it up, go with default l&f
        }
        
        JFrame main = new MainFrame();
        main.setVisible(true);
    }
}
