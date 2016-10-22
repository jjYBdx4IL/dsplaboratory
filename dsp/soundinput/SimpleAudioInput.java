/*
 * Created on 18.11.2004
 *  
 */
package dsp.soundinput;

import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import dsp.DataChunk;
import dsp.RealTimeInput;
import dsp.SingleDataChunk;
import dsp.VariableDataChunk;
import dsp.exception.RecoverableInputProblem;

/**
 * @author canti, 23.11.2004
 *  
 */
public class SimpleAudioInput implements RealTimeInput
{
    private float sampleRate;
    private int sampleSizeInBits;
    private int channels;
    private boolean bigEndian;
    AudioFormat audioFormat;

    SimpleAudioRecorder recorder;

    public SimpleAudioInput(float pSampleRate, int pSampleSizeInBits,
            int pChannels, boolean pSigned, boolean pBigEndian)
    {
        sampleRate = pSampleRate;
        sampleSizeInBits = pSampleSizeInBits;
        channels = pChannels;
        bigEndian = pBigEndian;
        audioFormat = new AudioFormat(
                (pSigned) ? AudioFormat.Encoding.PCM_SIGNED
                        : AudioFormat.Encoding.PCM_UNSIGNED, sampleRate,
                sampleSizeInBits, channels, (sampleSizeInBits / 8) * pChannels, sampleRate,
                bigEndian);

    }

    public boolean endOfInput()
    {
        return recorder.stopCapture;
    }

    public DataChunk getNext() throws RecoverableInputProblem
    {
        DataChunk nextSample = null;
        /*
         * Get next byte value from the blockingQueue of the recorder thread.
         * The take() method blocks execution until data is available in the
         * queue.
         */
        try
        {
            nextSample = (VariableDataChunk) (recorder.blockingQueue.take());
        }
        catch (SoundBlockingQueue.Closed e)
        {
            e.printStackTrace();
            throw new RecoverableInputProblem();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        /*
         * just a test, nextByte could be null
         */
        if (nextSample != null)
            return nextSample;
        else
        {
            throw new RecoverableInputProblem();
        }
    }

    public void start()
    {
        /*
         * Now, we are trying to get a TargetDataLine. The TargetDataLine is
         * used later to read audio data from it. If requesting the line was
         * successful, we are opening it (important!).
         */
        DataLine.Info info = new DataLine.Info(TargetDataLine.class,
                audioFormat);
        TargetDataLine targetDataLine = null;
        try
        {
            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);
        }
        catch (LineUnavailableException e)
        {
            System.out.println("unable to get a recording line");
//            e.printStackTrace();
//            System.exit(1);
            throw new RuntimeException(e);
        }

        /*
         * Now, we are creating an SimpleAudioRecorder object. It contains the
         * logic of starting and stopping the recording, reading audio data from
         * the TargetDataLine.
         */

        recorder = new SimpleAudioRecorder(targetDataLine, AudioFileFormat.Type.WAVE);

        /*
         * runs the recording thread
         */
        recorder.startRecording();
    }

    public void stop()
    {
        // stops the recording thread
        recorder.stopRecording();
    }

    public int getFrequency()
    {
        return (int)(sampleRate);
    }

    /*
     * Overriden 
     * 
     * TODO New implementation
     */
    public float getDelayCorrection()
    {
        // aici trebuie sa anuntam cat trebuie sa astepte WorkerThread dupa un
        // nou sample
        return 0f;
    }
}

class SimpleAudioRecorder extends Thread
{
    private TargetDataLine targetLine;
    private AudioFileFormat.Type targetType;
    private AudioInputStream audioInputStream;
    boolean stopCapture;

    SoundBlockingQueue blockingQueue;

    public SimpleAudioRecorder(TargetDataLine line,
            AudioFileFormat.Type pTargetType)
    {
        targetLine = line;
        audioInputStream = new AudioInputStream(line);
        targetType = pTargetType;
        stopCapture = false;
        blockingQueue = new SoundBlockingQueue();
    }

    /**
     * Starts the recording. To accomplish this, (i) the line is started and
     * (ii) the thread is started.
     */
    public void startRecording()
    {
        /*
         * Starting the TargetDataLine. It tells the line that we now want to
         * read data from it. If this method isn't called, we won't be able to
         * read data from the line at all.
         */
        targetLine.start();

        /*
         * Starting the thread. This call results in the method 'run()' (see
         * below) being called. There, the data is actually read from the line.
         */
        System.out.println("Recording from the Sound card..."
                + targetLine.toString());
        super.start();
    }

    public void stopRecording()
    {
        stopCapture = true;
    }

    public void run()
    {
        try
        {
            int frameSize = targetLine.getFormat().getFrameSize();
            byte[] buffer = new byte[1000/*frameSize*/];
            while (!stopCapture)
            {
                // Reads the data from the InputStream into buffer
                int countBytes = audioInputStream.read(buffer, 0,
                            buffer.length);
              // Adds the data in the buffer to the blocking queue
                convertInputData(buffer);
                
            }
            targetLine.stop();
            targetLine.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void convertInputData(byte[] buffer)
    {
        AudioFormat.Encoding afe = targetLine.getFormat().getEncoding();
        AudioFormat format = targetLine.getFormat();
        int sampleSize = format.getSampleSizeInBits();
        int frameSize = format.getFrameSize();
        boolean bigEndian = format.isBigEndian();
        int channelNumber = format.getChannels();
        double[] data = new double[buffer.length / frameSize];
        double temp = 0;
        int j=0;
        
        switch (sampleSize)
        {
            case 8:
                if (afe == AudioFormat.Encoding.PCM_SIGNED)
                    for (int i = 0; i < buffer.length; i+=channelNumber)
                    {
                        temp = 0;
                        for (int c = 0; c<channelNumber; c++)
                            temp += buffer[i+c];
                        data[j++] = temp/channelNumber;
                    }
                else if (afe == AudioFormat.Encoding.PCM_UNSIGNED)
                    for (int i = 0; i < buffer.length; i+=channelNumber)
                    {
                        temp = 0;
                        for (int c = 0; c<channelNumber; c++)
                            temp += (byte)(buffer[i+c] ^ 0x80)+128;
                        data[j++] = temp/channelNumber;
                    }
                break;
            case 16:
                if (bigEndian)
                    for (int i = 0; i < buffer.length; i += 2*channelNumber)
                    {
                        temp = 0;
                        for (int c = 0; c<channelNumber; c++)
                            temp += (short)((buffer[i+c] << 8) | (buffer[i + c + 1]));
                        data[j++] = (temp/channelNumber) / 256;
                    }
                else
                    for (int i = 0; i < buffer.length; i += 2*channelNumber)
                    {
                        temp = 0;
                        for (int c = 0; c<channelNumber; c++)
                            temp += (short)((buffer[i+c]) | (buffer[i + c + 1] << 8));
                        data[j++] = (temp/channelNumber) / 256;
                    }
        }

        blockingQueue.add(new VariableDataChunk(data));
    }
}
