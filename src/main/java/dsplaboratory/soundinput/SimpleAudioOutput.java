/*
 * Created by canti, 04.02.2005
 *
 */
package dsplaboratory.soundinput;

import java.io.IOException;
import java.math.*;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import dsplaboratory.DataChunk;
import dsplaboratory.Output;
import dsplaboratory.VariableDataChunk;
import dsplaboratory.soundinput.SoundBlockingQueue.Closed;

/**
 * @author canti, 04.02.2005
 *
 */
public class SimpleAudioOutput implements Output
{
	AudioFormat	audioFormat = null;
	SourceDataLine	line = null;
	DataLine.Info	dataLineInfo = null;//new DataLine.Info(SourceDataLine.class,

	SimpleAudioPlayer player = null;

    /**
     * 
     */
    public SimpleAudioOutput(float pSampleRate, int pSampleSizeInBits,
            int pChannels, boolean pSigned, boolean pBigEndian)
    {
        super();
        audioFormat = new AudioFormat(
                (pSigned) ? AudioFormat.Encoding.PCM_SIGNED
                        : AudioFormat.Encoding.PCM_UNSIGNED, pSampleRate,
                pSampleSizeInBits, pChannels, (pSampleSizeInBits / 8)*pChannels, pSampleRate,
                pBigEndian);


		dataLineInfo = new DataLine.Info(
			SourceDataLine.class,
			audioFormat);
		

    }
    /* 
     * Overriden
     * 
     * TODO New implementation
     */
    public void putNext(DataChunk d)
    {
        int nItems = d.getSize();
        double buffer[] = new double[nItems];
        for(int i=0; i<nItems; i++)
        {
            buffer[i] = d.getElement(i);
        }
        player.blockingQueue.add(new VariableDataChunk(buffer));
    }

    /* 
     * Overriden
     * 
     * 
     */
    public void start()
    {
		try
		{
			line = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			line.open(audioFormat);
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		line.start();
		
		player = new SimpleAudioPlayer(line, AudioFileFormat.Type.WAVE);
        player.startPlaying();

    }

    /* 
     * Overriden
     * 
     * 
     */
    public void stop()
    {
        player.stopPlaying();
		line.drain();
		line.close();
    }

}


class SimpleAudioPlayer extends Thread
{
    private SourceDataLine outputLine;
    boolean stopPlaying;

    public SoundBlockingQueue blockingQueue;

    public SimpleAudioPlayer(SourceDataLine line,
            AudioFileFormat.Type pTargetType)
    {
        outputLine = line;
        stopPlaying = false;
        blockingQueue = new SoundBlockingQueue();
    }

    /**
     * Starts the playing. To accomplish this, the thread is started.
     */
    public void startPlaying()
    {
        /*
         * Starting the thread. This call results in the method 'run()' (see
         * below) being called. There, the data is actually read from the line.
         */
        stopPlaying = false;
        System.out.println("Playing to the Sound card..."
                + outputLine.toString());
        super.start();
    }

    public void stopPlaying()
    {
        stopPlaying = true;
    }

    public void playBuffer(byte buffer[])
    {
	    int	nBytesWritten = outputLine.write(buffer, 0, buffer.length);
    }
    
    public void playDataChunk(DataChunk samples)
    {
		int	nItems = samples.getSize();
		AudioFormat format = outputLine.getFormat();
		int channelNumber = format.getChannels();
		int sampleSizeInBytes = format.getSampleSizeInBits() / 8;
        boolean bigEndian = format.isBigEndian();
        int domainExtend = (sampleSizeInBytes == 2)?256:1;
        for (int i=0; i < nItems; i++)
		{
		    double sample = (samples.getElement(i)); 
			byte outputBuffer[] = new byte[sampleSizeInBytes * channelNumber];
		    for (int channel=0; channel<channelNumber; channel++)
		    {
				for (int bytes=0; bytes<sampleSizeInBytes; bytes++)
				{
			        if (bigEndian)
			            outputBuffer[channel * channelNumber + bytes] = (byte)(((short)(sample * domainExtend) >> (8 * (sampleSizeInBytes - bytes - 1))) & 0xFF);
			        else
			            outputBuffer[channel * channelNumber + bytes] = (byte)(((short)(sample * domainExtend) >> (8 * bytes)) & 0xFF);
			    }
			}
			playBuffer(outputBuffer);
		}
        
    }
    public void run()
    {
        DataChunk nextSamples = null;
        while (!stopPlaying)
        {
	        try
	        {
	            nextSamples = (VariableDataChunk)(blockingQueue.take());
	            playDataChunk(nextSamples);
	        }
            catch (Closed e)
            {
                e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }


}

