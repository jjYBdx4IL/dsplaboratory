/*
 * Created by canti, 04.02.2005
 *
 */
package dsp.soundinput;

import javax.swing.JDialog;

import dsp.Output;
import dsp.OutputFactory;
import dsp.exception.UserCancelled;


/**
 * @author canti, 04.02.2005
 *
 */
public class SoundOutputFactory implements OutputFactory
{

    /**
     * 
     */
    public SoundOutputFactory()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    /* 
     * Overriden
     * 
     * TODO New implementation
     */
    public String getName()
    {
        // TODO Auto-generated method stub
        return "Soundcard output";
    }

    /* 
     * Overriden
     * 
     * TODO New implementation
     */
    public Output getOutput(JDialog parent, int frequency) throws UserCancelled
    {
	    SoundInputDialog sic = new SoundInputDialog(parent);
	    sic.setVisible(true);
        if (sic.isOK())
            return new SimpleAudioOutput(sic.getSampleRate(), sic.getSampleSize(), sic.getChannels(),
                    sic.isSigned(), sic.isBigEndian());
        else
            throw new UserCancelled();	    

    }

}
