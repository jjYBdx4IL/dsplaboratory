
/**
 * Write a description of class RawOutput here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package dsplaboratory.auraw;
import dsplaboratory.DataChunk;
import dsplaboratory.Output;

import java.io.*;
public class RawOutput implements Output
{
 private FileOutputStream fo;
public RawOutput(File fisier)
{
 try{
 	 fo=new FileOutputStream(fisier);
 	}
 catch(IOException e)
 	{
 	 System.out.println("Eroare fisier "+e);
 	}	 
}
public void putNext(DataChunk d)
{
 try
 	{
 	 fo.write((int)d.getElement(0));	
 	}
 catch(IOException e)
 {
 	System.out.println("Eroare :"+e);
 }
 	
}
public void start()
{
}
public void stop()
{
 try
 	{
	 fo.close();
 	}
 catch(IOException e)
 	{
 	 System.out.println("Eroare :"+e);
 	}
}
}
