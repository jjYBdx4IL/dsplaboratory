
/**
 * Write a description of class RawInput here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package dsp.auraw;
import java.io.*;
import dsp.*;
import dsp.exception.RecoverableInputProblem;
public class RawInput implements Input
{
private int OctetiPerEsantion;
private boolean OctetCuSemn;
private DataInputStream fi;
private int Buffer;
public RawInput(String a,boolean b,File f)
{
 OctetCuSemn=b;
 OctetiPerEsantion=Integer.valueOf(a).intValue(); 
 try
 	{
 	 fi=new DataInputStream(new FileInputStream(f)); 	  	 
 	}
 catch(Exception e)
 	{ 
 	} 
}
public void start()
{
  
}
public DataChunk getNext() throws RecoverableInputProblem
{
 return new SingleDataChunk(Buffer);  
}
public void stop()
{
}
public boolean endOfInput()
{
	 try{	
		switch(OctetiPerEsantion)
	 		{
	 		 case 1:if(OctetCuSemn==true)
	 		 	    	Buffer=fi.readByte();		 	    	
	 		 		else
	 		 			Buffer=fi.readUnsignedByte();
	 		 		break;
	 		 case 2:if(OctetCuSemn==true)
	 		 			Buffer=fi.readShort();
	 		 		else
	 		 			Buffer=fi.readUnsignedShort();
	 		 		break;
	 		}
	    }
	 catch(EOFException e)
		{
	 	 try{
	 	 	 fi.close();
	 	 	}
	 	 catch(IOException ee)
		 	{
	 	 	 System.out.println("Eroare la inchiderea fisierului"+ee);
		 	}
	 	 return true;
		}
	 catch(IOException e)
	 	{
	 	 System.out.println("Eroare IOException"+e);
	 	}	
 return false;
}
public int getFrequency()
{
	return 0;
}
}
