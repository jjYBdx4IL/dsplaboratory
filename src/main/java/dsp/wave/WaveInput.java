/*
 * Created on Dec 2, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsp.wave;

import java.io.File;
import java.io.*;
import java.io.IOException;
import java.io.RandomAccessFile;

import dsp.DataChunk;
import dsp.Input;
import dsp.SingleDataChunk;
import dsp.VariableDataChunk;
import dsp.exception.RecoverableInputProblem;

/**
 * @author silviu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WaveInput implements Input
{
	    private String nume_fisier;
	    private long filesize=0;
	    private long datariffsize=0;
	    private long datachunksize=0;
	    private int nr_channels=0;
	    private int compression_code=0;
	    private int sample_rate=0;
	    private int average_bytes_per_second=0;
	    private int block_align=0;
	    private int significant_bits_per_sample=0;
	    private int extra_format_bytes=0;
	    private boolean ready4sending_chunks = false;
	    private boolean chunks_finished = false;
	    private boolean error;
	    private long current_chunk=0;
	    private int picked_channel=0;
	    private long chunk_max_value=0;
	    private int data_offset=0;
	    	    
	  //  RandomAccessFile is;
	    
	    FileInputStream fis = null ;
	    FileOutputStream fos = null ;
	    DataInputStream dis = null;
	    
	  public WaveInput (String pnume_fisier,int pickeed_channel)
	  {
	    nume_fisier = pnume_fisier;
	    error=false;
	    picked_channel=pickeed_channel;
	    
	    try 
		{
	    	fis = new  FileInputStream(pnume_fisier);
	    	dis = new DataInputStream(fis);
		}
	    catch (IOException ie) { }   
	    	      
	    try 
		{
	    	int riffdata=0;  // size of RIFF data chunk.
	    	int chunkSize=0, bytecount=0;
	    	String sfield="" ;
	    	String sp = "     " ;  // spacer string.
	    	String indent = sp + "     " ;
	    	
	    	filesize = (new File(pnume_fisier)).length() ;  // get file length.
	    	//System.out.println(" ******  FILE:  " + pnume_fisier   + "    LENGTH:  " + filesize + " bytes ****** \n") ; 

	    	/*  --------  Get RIFF chunk header --------- */
	    	
	    	for (int i=1; i<=4; i++) 
	    	     sfield+=(char)dis.readByte() ;
	    	
	    	if (!sfield.equals("RIFF")) 
	    	{
	    	     System.out.println(" ****  Not a valid RIFF file  ****") ;
	    	     error=true;
	    	     return ;
	    	}      

	    	for (int i=0; i<4; i++)
	    	{
	    		int a = dis.readUnsignedByte();
	    		chunkSize += a *(int)Math.pow(256,i) ;
	    	}
	    	System.out.println(sp+sfield + "    ----- data size: "+chunkSize+ " bytes") ;
	    	
	    	   
	    	sfield="" ;
	    	for (int i=1; i<=4; i++) 
	    	     sfield+=(char)dis.readByte() ;
	        System.out.println(sp+"        ----- form type: "+ sfield + "\n") ;

	    	riffdata=chunkSize ;
	    	/* --------------------------------------------- */

	    	bytecount = 4 ;// initialize bytecount to include RIFF form-type bytes.
	    	int cont = 12;
	    	while (bytecount < riffdata )  
	    	{    // check for chunks inside RIFF data area. 
	    	     sfield="" ;
	    	     for (int i=1; i<=4; i++) 
	    	         sfield+=(char)dis.readByte() ;

	    	     chunkSize=0 ;
	    	     
	    	     for (int i=0; i<4; i++) 
	    	         chunkSize += dis.readUnsignedByte() *(int)Math.pow(256,i) ;
	    	     cont = cont + 8 + chunkSize;
	    	     
	    	     if (sfield.equals("data"))
	    	     { 	    	     
	    	     	data_offset = cont - chunkSize;	    	    
	    	     }	    	     	
	    	     
	    	     bytecount += (8+chunkSize) ;
	    	     System.out.println("\n" +sp+ sfield + "    ----- data size: "+chunkSize+ " bytes") ;
	    	     
	    	     if (sfield.equals("data"))
	    	     {
	    	     	ready4sending_chunks = true;
	    	     	datachunksize=chunkSize;	    	     
	    	     }
	    	     	
	    	     if (sfield.equals("fmt ")) 
	    	     {               
	    	         if (chunkSize<16) 
	    	         {
	    	            System.out.println(" ****  Not a valid fmt chunk  ****") ;
	    	         	error=true; 
	    	         	return ;
	    	         }      
	    	         int wFormatTag = dis.readUnsignedByte() ;
	    	         
	    	         dis.skipBytes(1) ;  
	    	         compression_code = wFormatTag;
	    	         if (wFormatTag == 1) 
	    	         {
	    	            System.out.println(indent + "wFormatTag:  MS PCM format") ;
	    	         }	    	         	    	         
	    	         else
	    	         {
	    	         	error=true;
	    	         	System.out.println(indent + "wFormatTag:  non-PCM format") ;
	    	         }
	    	         		    	         	    	         
	    	         int nChannels = dis.readUnsignedByte() ;
	    	         nr_channels = nChannels;
	    	         dis.skipBytes(1) ;	    	         
	    	         System.out.println(indent + "nChannels:  "+nChannels) ;
	    	         
	    	         int nSamplesPerSec=0 ;
	    	         for (int i=0; i<4; i++) 
	    	         		nSamplesPerSec += dis.readUnsignedByte() *(int)Math.pow(256,i) ;
	    	         System.out.println(indent + "nSamplesPerSec:  "+nSamplesPerSec) ;
	    	         sample_rate = nSamplesPerSec;
	    	         
	    	         int nAvgBytesPerSec=0 ;
	    	         for (int i=0; i<4; i++)
	    	         {
	    	         	int aa=dis.readUnsignedByte();
	    	         	nAvgBytesPerSec += aa *(int)Math.pow(256,i) ;
	    	         }
	    	         System.out.println(indent + "nAvgBytesPerSec:  "+nAvgBytesPerSec) ;
	    	         average_bytes_per_second = nAvgBytesPerSec;
	    	         
	    	         int nBlockAlign=0;
	    	         for (int i=0; i<2; i++)
	    	         		nBlockAlign += dis.readUnsignedByte() *(int)Math.pow(256,i) ;
	    	         System.out.println(indent + "nBlockAlign:  "+nBlockAlign) ;
	    	         block_align = nBlockAlign;
	    	         
	    	         if (wFormatTag ==1) 
	    	         {     // if MS PCM format
	    	          		int nBitsPerSample = dis.readUnsignedByte() ;
	    	          		significant_bits_per_sample = nBitsPerSample;
	    	                dis.skipBytes(1) ;
	    	                chunk_max_value = (long)Math.pow(256,significant_bits_per_sample/8)/2;
	    	                System.out.println(indent + "nBitsPerSample:  "+nBitsPerSample) ;
	    	          }
	    	          else  
	    	          		dis.skipBytes(2) ;
	    	          extra_format_bytes = chunkSize-16;
	    	          dis.skipBytes(chunkSize-16) ; //skip over any extra bytes in format specific field.                                  
	    	     }
	    	     else    // if NOT the fmt chunk.
	    	     dis.skipBytes(chunkSize);
	    	     
	    	     dis = new DataInputStream(fis);
	    	     dis.skip(data_offset);
	    	  }  // end while.

	    	  System.out.println("\n"+sp+"Final RIFF data bytecount: " + bytecount) ;
	    	     if ((8+bytecount) != (int)filesize)
	    	     {
	    	        error=true;
	    	     	System.out.println(sp+"!!!!!!! Problem with file structure  !!!!!!!!! ") ;
	    	     }
	    	     else 
	    	     {
	    	      	System.out.println(sp + "File chunk structure consistent with valid RIFF ") ;
	    	     }
	    	      	 
	    	     System.out.println(" -------------------------------------------------------") ;   	    	    
			 }  // end try.
	         
	         catch(Exception ex1)
		     {	         		
					ex1.printStackTrace();
		     }
	    
	    /*	
	    	try
			{
				is = new RandomAccessFile(new File(nume_fisier),"rw");
				int octet = 0;
				byte data[] = new byte[4];
				// fill
				for(int i=0;i<4;i++)
					data[i] = (byte)is.read();
				
				while((octet = is.read()) != -1)
				{
					data = this.shiftBuffer(data,(byte)octet);
					if(this.isData(data))			
						break;
				}	
				
				int sample = 0;
				octet = 0;
				while(octet!= -1)
				{
					System.out.println("Octet: " + this.GetByte(is));
				}
					
				/*while((sample = this.getSample(is) )!= -1)
				{
					// read data chunks	
					System.out.println("DATA found"); 				
				}
					
				System.out.println("DONE");	
				is.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			*/
		}
		
	    /* (non-Javadoc)
		 * @see dsp.Input#endOfInput()
		 */
	        
	 public int GetCompressionCode()
	 {
	 	return compression_code;
	 }
	 public int GetNumberOfChannels()
	 {
	 	return nr_channels;
	 }
	 public int GetSampleRate()
	 {
	 	return sample_rate;
	 }
	 public int GetAvrgBPerS()
	 {
	 	return average_bytes_per_second;
	 }
	 public int GetBlockAlign()
	 {
	 	return block_align;
	 }
	 public int GetSegnBits()
	 {
	 	return significant_bits_per_sample;
	 }
	 public int GetExtraFBytes()
	 {
	 	return extra_format_bytes;
	 }
	 public long GetDataChunkSize()
	 {
	 	return datachunksize;
	 }
	 public boolean GetError()
	 {
	 	return error;
	 }
	 public long GetChunkMaxValue()
	 {	 		
	 	return chunk_max_value;
	 }
        public byte[] shiftBuffer(byte[] b,byte newB) throws java.io.UnsupportedEncodingException
	    {
			byte buffer[] = new byte[4];
			for(int i=0;i<3;i++)
				buffer[i] = b[i+1];
			buffer[3] = newB;

			return buffer;
	    }
		
		public boolean isData(byte[] b) throws java.io.UnsupportedEncodingException
		{
			String buffer = new String(b,"US-ASCII");		
			return buffer.equals("data");
		}
		
		public int getSample(RandomAccessFile is) throws IOException//(InputStream is) throws IOException
		{
			int b1 = is.read();
			int b2 = is.read();
			
			if( b1== -1 || b2==-1)
				return -1;
			
			return (byte)b2 * 0x100 + (byte)b1;
		}
		
		public byte GetByte(RandomAccessFile is) throws IOException
		{
			return (byte)is.read();			
		}
	    
	 public boolean endOfInput() 
	 {
			if(current_chunk<=datachunksize)
				chunks_finished=false;
			else
				chunks_finished=true;
			return chunks_finished;
	 }


	 /* (non-Javadoc)
	 * @see dsp.Input#start()
	 */
	 public void start() 
	 {	 	
		
    	try 
		{
	    	fis = new  FileInputStream(nume_fisier);
	    	dis = new DataInputStream(fis);
		}
	    catch (IOException ie) { }
	    			
		try 
		{
			dis.skipBytes(data_offset);				
		} 
		catch (IOException e1) 
		{			
				e1.printStackTrace();
		}
	 }

	 /* (non-Javadoc)
	 * @see dsp.Input#getNext()
	 */
	 public DataChunk getNext() 
	 {    			
	 	SingleDataChunk sdc = null;
		long chunk,val=0,val1=0,test=0;
	 	    if (ready4sending_chunks)
	        {
	          try
			  {				
				for(int k=0;k<picked_channel;k++)
				  	for(int j=0;j<significant_bits_per_sample/8;j++)				
	                	test=dis.skipBytes(1);
				current_chunk++;  	
	            if (significant_bits_per_sample==8)
	            {
	                for (int i=0; i<significant_bits_per_sample/8; i++)
	            	{	            	   
	            	   if((chunk=dis.readUnsignedByte())!=-1)	            			            			            			     			        	
	            			val += chunk *(int)Math.pow(256,i) ;	            			            		
	            	   else
	            		    val+=0;	            	   	            		    
	            	}
	            }
	            else
	            {  
	            	for (int i=0; i<significant_bits_per_sample/8; i++)
	            	{	            	   
	            	   if((chunk=dis.readUnsignedByte())!=-1)	            	   
	            			val += (chunk) *(int)Math.pow(256,i) ;	            	   		
	            	   else
	            		    val+=0;	            	   	            		    
	            	}
	            }
	            System.out.print("  |  CH: "+ current_chunk);
	            System.out.print("  |  U: "+val);
	            if (val>=chunk_max_value)
	            	 val= (-1)*(chunk_max_value*2-val);
	            System.out.print("  |  S: "+val);
	            
	            double val2 = val*59.0/chunk_max_value;
	            System.out.println("  |  PtGrafic: "+ val2);	            
	            if (val2>=59||val2<=-59)
	            System.out.println("ERROR -> punct Peste/SuB GRAFIC");
	            sdc = new SingleDataChunk(val2);
			  }
	          catch (IOException e)
			  {
	        	  e.printStackTrace();	
			  }
				
	        }			
			return sdc;
	 }

	 /* (non-Javadoc)
	 * @see dsp.Input#stop()
	 */
	 public void stop() 
	 {
		// TODO Auto-generated method stub
	 }

	 /* (non-Javadoc)
	 * @see dsp.Input#getFrequency()
	 */
	 public int getFrequency() 
	 {
	 return sample_rate;
	 }
	    

}
