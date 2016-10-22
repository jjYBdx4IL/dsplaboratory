/*
 * Created on Nov 14, 2004
 *
 */
package dsplaboratory.dummy;

import javax.swing.JDialog;

import dsplaboratory.Input;
import dsplaboratory.InputFactory;

/**
 *
 * @author Luke
 */
public class ConstantInputFactory implements InputFactory
{

    public String getName()
    {
        return "Constant Input";
    }

    public Input getInput(JDialog parent)
    {
        return new ConstInput(10, 7);
    }

}
