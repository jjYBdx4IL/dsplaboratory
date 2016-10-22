/*
 * Created on Nov 19, 2004
 *
 */
package dsp;

/**
 * Un semnal de intrare provenit de la o sursa externa, a carei
 * intrare nu poate fi intarziata nelimitat, sau grabita.
 * @author Luke
 */
public interface RealTimeInput extends Input
{
    /**
     * @return cate milisecunde + / - se estimeaza ca trebuie
     * ajustata perioada de asteptare intre doua apeluri succesive
     * ale metodei getNext()
     */
    float getDelayCorrection();
}
