/*
 * Created on Nov 14, 2004
 *
 */
package dsplaboratory;

import dsplaboratory.exception.RecoverableInputProblem;

/**
 * Un semnal de intrare.
 * @author Luke
 */
public interface Input
{
    /**
     * @return true daca s-a terminat semnalul de intrare 
     */
    boolean endOfInput();
    
    /**
     * Face pregatirile finale pentru inceperea aducerii datelor.
     * Poate sa blocheze, eventual, in asteptarea inceperii fluxului
     * de date. <br>
     * Aici nu se mai discuta cu utilizatorul.
     */
    void start();
    
    /**
     * Aduce urmatorul(ele) esantion de la intrare.
     * Se prefera viteza maxima.
     * @return DataChunk unul sau mai multe esantioane din semnal.
     * @throws RecoverableInputProblem
     */
    DataChunk getNext() throws RecoverableInputProblem; 
    
    /**
     * Incheie aducerea datelor.
     */
    void stop();
    
    /**
     * Aceasta functie trebuie sa intoarca rezultatul corect imediat
     * dupa constructia obiectului, <b> inainte </b> de metoda start().
     * @return frecventa de esantionare a semanlului de intrare, in Hz.
     * Pare rezonbila o frecventa maxima de 2GHz.
     */
    int getFrequency();
}
