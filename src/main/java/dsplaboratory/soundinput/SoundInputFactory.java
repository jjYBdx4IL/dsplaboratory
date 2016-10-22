/*
 * Created on 18.11.2004
 *
 */
package dsplaboratory.soundinput;

import javax.swing.JDialog;

import dsplaboratory.Input;
import dsplaboratory.InputFactory;
import dsplaboratory.dummy.DummyInput;
import dsplaboratory.exception.UserCancelled;

/**
 * @author canti
 *
 */
public class SoundInputFactory implements InputFactory
{

    public Input getInput(JDialog dialog) throws UserCancelled
    {
	    SoundInputDialog sic = new SoundInputDialog(dialog);
	    sic.setVisible(true);
        if (sic.isOK())
            return new SimpleAudioInput(sic.getSampleRate(), sic.getSampleSize(), sic.getChannels(),
                    sic.isSigned(), sic.isBigEndian());
        else
            throw new UserCancelled();	    
	}

    public String getName()
    {
        return "Sound Card Input";
    }


}
