/*
 * Created on Nov 19, 2004
 *
 */
package dsplaboratory.exception;

/**
 * Semnalizeaza o problema la aducerea esantioanelor de catre un obiect
 * Input, problema care poate fi rezolvata in cadru programului
 * (ex. tipic: mai trebuie sa asteptam) 
 * @author Luke
 */
public class RecoverableInputProblem extends Exception
{

    public RecoverableInputProblem()
    {
        super();

    }

    public RecoverableInputProblem(String arg0)
    {
        super(arg0);

    }

    public RecoverableInputProblem(Throwable arg0)
    {
        super(arg0);

    }

    public RecoverableInputProblem(String arg0, Throwable arg1)
    {
        super(arg0, arg1);

    }

}
