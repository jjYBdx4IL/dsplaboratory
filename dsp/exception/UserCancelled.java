/*
 * Created on Nov 19, 2004
 *
 */
package dsp.exception;

/**
 * Semnalizeaza anularea de catre utilizator a dialogului de
 * construire a unui obiect
 * @author Luke
 */
public class UserCancelled extends Exception
{

    public UserCancelled()
    {
        super();

    }

    public UserCancelled(String arg0)
    {
        super(arg0);

    }

    public UserCancelled(Throwable arg0)
    {
        super(arg0);

    }

    public UserCancelled(String arg0, Throwable arg1)
    {
        super(arg0, arg1);

    }

}
