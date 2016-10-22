
/**
 * Write a description of class AuOutput here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package dsp.auraw;
import dsp.*;
import java.io.*;
import javax.sound.sampled.*;
public class AuOutput implements Output
{
 AudioInputStream audioInputStream=null;
 ByteArrayOutputStream output;
 File fisier;
 float SampleRate;
 int SampleSizeInBits;
 int Channels;
 boolean Signed;
 boolean BigEndian; 
public AuOutput(String sampleRate,String sampleSizeInBits,String channels,String signed,String bigEndian,File fi)
{ 
 output=new ByteArrayOutputStream();
 fisier=fi; 
 SampleRate=Float.valueOf(sampleRate).floatValue();
 SampleSizeInBits=Integer.valueOf(sampleSizeInBits).intValue();
 Channels=Integer.valueOf(channels).intValue(); 
 Signed=Boolean.valueOf(signed).booleanValue();
 BigEndian=Boolean.valueOf(bigEndian).booleanValue();  
}
public void putNext(DataChunk d)
{
 output.write((int)d.getElement(0));	 
}
public void start()
{
}
public void stop()
{ 
 AudioFormat format=new AudioFormat(SampleRate,SampleSizeInBits,Channels,Signed,BigEndian);																																			//frameSize,frameRate	
 audioInputStream=new AudioInputStream(new ByteArrayInputStream(output.toByteArray()),format,output.size()*Channels);
 	
 if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.AU,audioInputStream))
 	{
 	 try{
 	 	 AudioSystem.write(audioInputStream,AudioFileFormat.Type.AU,fisier);
 	 	}
 	 catch(IOException e)
	 	{
 	 	 System.out.println("Eroare la scrierea fisierului au "+e);
 	 	 return;
	 	}	 
	}
	 
}
}
