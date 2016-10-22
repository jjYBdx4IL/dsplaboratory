/*
 * Created on Jan 23, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsp.voc;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;

import javax.sound.sampled.AudioFormat;
import dsp.DataChunk;
import dsp.SingleDataChunk;
import dsp.VariableDataChunk;
import dsp.exception.RecoverableInputProblem;
import dsp.Input;
import javax.*;

/**
 * @author Sir Costy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VocInput implements Input {
    
	private long sampleRate;
    private int sampleSize;
    private int channels;
    private int TimeConstant;
    private int totalLength;
    private int tmpPosition;
    private byte[] pData;
    
    private int Sel_channel;
    private int tempSampleRate;
    private DataInputStream myInputStream;
    private File ReadFile;
    
    private boolean IsOk;
    
    public VocInput(File pReadFile ,int pSel_channel,int pchannels,long psampleRate,int psampleSize){
    	ReadFile = pReadFile;
    	sampleRate =psampleRate;
		sampleSize = psampleSize;
		channels = pchannels;
		Sel_channel = pSel_channel;
		totalLength=0;
		IsOk = true;
		ReadFile();
   	
    }
    
    /* (non-Javadoc)
	 * @see dsp.Input#endOfInput()
	 */
	public void ReadFile(){
		try{
			//System.out.println("READING FILE");
			myInputStream= new DataInputStream(new FileInputStream(ReadFile));
			pData = new byte[myInputStream.available()];
			//System.out.println("memorie alocata pt stream"+myInputStream.available());
		//	char[] pDataTemp=new char[myInputStream.available()];
			byte bType;
			int lLen,timeConstant,foundSampleSize,foundChannels;
			long foundSampleRate;
	    	myInputStream.skipBytes(20);
	    	byte [] a =new byte[2];
			myInputStream.read(a);
			byte [] aa ={a[1],a[0]};
			myInputStream.skipBytes((new BigInteger(1,aa)).intValue()-22);
			while(true){
				bType = (byte) myInputStream.readUnsignedByte();
				lLen=timeConstant=0;
				//System.out.println("READING FILE"+bType);
				switch(bType){
		        	case 1:{
		        		//Used to define mono, 8 bit audio

		        		//System.out.println("TYPE 1");
		        		byte [] b =new byte[3];
		        		try{
		        			myInputStream.read(b);
		        		}
		        		catch(EOFException FileEnd){
		        			IsOk=false;
		        			System.out.println("Unexpected read file finish");
		        			return ;
		        		}
		        		byte [] bb ={b[2],b[1],b[0]};
		        		lLen=((new BigInteger(1,bb)).intValue())-2;
		        		try{
		        			timeConstant = myInputStream.readUnsignedByte();
		        		}
		        		catch(EOFException FileEnd){
		        			IsOk=false;
		        			System.out.println("Unexpected read file finish");
		        			return ;
		        		}
		        		myInputStream.skipBytes(1);
		        		foundSampleSize =8;
		        		foundChannels = 1;
		        		foundSampleRate = 1000000 /(256-(timeConstant % 256));
		        		if((foundSampleSize!=sampleSize)||(foundSampleRate!=sampleRate)||(foundChannels!=channels)){
		        			IsOk=false;
		        			return ;
		        		}
		        
		        		try{
		        			//System.out.println("lungime="+lLen);
		        			myInputStream.read(pData,totalLength,lLen);
			        		
		        		}
		        		catch(EOFException FileEnd){
		        			IsOk=false;
		        			System.out.println("Unexpected read file finish");
		        			return ;
		        		}
		        		catch(IndexOutOfBoundsException OutOfBound){
		        			IsOk=false;
		        			System.out.println("IndexOutOfBounds");
		        			return ;
		        		}
		        		totalLength+=lLen;
		        		break; 
		        	}
		        	case 8:{
		        		//Used to define stereo, 8 bit audio

		        		//System.out.println("TYPE 8");
		        		myInputStream.skipBytes(3);
		        			        		
		        		byte [] b =new byte[2];
		        		try{
		        			myInputStream.read(b);
		        		}
		        		catch(EOFException FileEnd){
		        			IsOk=false;
		        			System.out.println("Unexpected read file finish");
		        			return ;
		        		}
		        		byte [] bb ={b[1],b[0]};
		        		BigInteger Bi = (new BigInteger(1,bb)).shiftRight(8);
		        		timeConstant=Bi.intValue();
		        		//System.out.println("time ct ="+timeConstant);
		        		
		        		myInputStream.skipBytes(1);
		        		try{
		        			foundChannels = myInputStream.readUnsignedByte();	
		        		}
		        		catch(EOFException FileEnd){
		        			IsOk=false;
		        			System.out.println("Unexpected read file finish");
		        			return ;
		        		}
		        		
		        		
		        		foundSampleSize = 8;
		        		foundChannels++;
						foundSampleRate = 500000 /(256-(timeConstant % 256));
						if((foundSampleSize!=sampleSize)||(foundSampleRate!=sampleRate)||(foundChannels!=channels)){
		        			IsOk=false;
		        			return ;
		        		}
					//	System.out.println("chanels found"+ foundChannels);
					//	System.out.println("chanels "+ channels);
						myInputStream.skipBytes(1);
//						Block of type 8 is always followed by a block of type 1
						byte [] bbb =new byte[3];
		        		try{
		        			myInputStream.read(bbb);
		        		}
		        		catch(EOFException FileEnd){
		        			IsOk=false;
		        			System.out.println("Unexpected read file finish");
		        			return ;
		        		}
		        		byte [] bbbb ={bbb[2],bbb[1],bbb[0]};
		        		lLen=((new BigInteger(1,bbbb)).intValue())-2;
		        	//	System.out.println("Lungime"+lLen);
		        		myInputStream.skipBytes(2);
		        		
		        		
		        		try{
		        		//	System.out.println("lungime="+lLen);
		        			myInputStream.read(pData,totalLength,lLen);
			        		
		        		}
		        		catch(EOFException FileEnd){
		        			IsOk=false;
		        			System.out.println("Unexpected read file finish");
		        			return ;
		        		}
		        		catch(IndexOutOfBoundsException OutOfBound){
		        			IsOk=false;
		        			System.out.println("IndexOutOfBounds");
		        			return ;
		        		}
		        		totalLength+=lLen;
		        	//	System.out.println("Finish reaad bloc 8");
		        		break; 
		        		
		        	}
		        	case 9:{
		        		//Used for stereo, 16 bit.
		        		//System.out.println("TYPE 9");
		        		byte [] b =new byte[3];
		        		try{
		        			myInputStream.read(b);
		        		}
		        		catch(EOFException FileEnd){
		        			IsOk=false;
		        			System.out.println("Unexpected read file finish");
		        			return ;
		        		}
		        		byte [] bb ={b[2],b[1],b[0]};
		        		lLen=((new BigInteger(1,bb)).intValue())-12;
		        		
		        		
		        	//	System.out.println("lungime bloc 9"+lLen);
		        		
		        		byte [] bbb =new byte[4];
		        		try{
		        			myInputStream.read(bbb);
		        		}
		        		catch(EOFException FileEnd){
		        			IsOk=false;
		        			System.out.println("Unexpected read file finish");
		        			return ;
		        		}
		        		byte [] bbbb ={bbb[3],bbb[2],bbb[1],bbb[0]};
		        		foundSampleRate=((new BigInteger(1,bbbb)).intValue());	        		
		        		
		        		foundSampleSize = myInputStream.read();
		        		foundChannels = myInputStream.read();
		        		if(foundChannels==2)
		        			foundSampleRate/=2;
		        		if((foundSampleSize!=sampleSize)||(foundSampleRate!=sampleRate)||(foundChannels!=channels)){
		        			IsOk=false;
		        			return ;
		        		}
		        		
		        		myInputStream.skipBytes(6);	
		        		try{
		        		//	System.out.println("lungime="+lLen);
		        			myInputStream.read(pData,totalLength,lLen);
			        		
		        		}
		        		catch(EOFException FileEnd){
		        			IsOk=false;
		        			System.out.println("Unexpected read file finish");
		        			return ;
		        		}
		        		catch(IndexOutOfBoundsException OutOfBound){
		        			IsOk=false;
		        			System.out.println("IndexOutOfBounds");
		        			return ;
		        		}
		        		totalLength+=lLen;
		        		//System.out.println("Finish reaad bloc 9");
		        		break; 
		        	}
		        
		        }
			}
		}
        catch(EOFException FileEnd){
        	//System.out.println("Read file finish ok");
        	try {
				myInputStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	return;
        }
		catch(Exception e){
			System.out.println("Unable to read file! File Bad or Corupted");
			e.printStackTrace();
			try {
				myInputStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            return;
		}
		
	
	}
    
    
    public boolean endOfInput() {
		// TODO Auto-generated method stub
    	//System.out.println("IsOK?????? = "+IsOk);
    //	System.out.println("Lungime totala"+totalLength);
    	//System.out.println("Pozitie curenta"+tmpPosition);
    	//System.out.println("SampleRate"+tempSampleRate);
    	//System.out.println("Selected chanel"+Sel_channel);
    	if((tmpPosition<totalLength)&&(IsOk)){
    	//	System.out.println("Am returnat ok");
    		return false;
			
    	}
		else
			return true;
    	
    }


	/* (non-Javadoc)
	 * @see dsp.Input#start()
	 */
	public void start() {
		// TODO Auto-generated method stub
		//System.out.println("get start");
		//System.out.println("get start"+Sel_channel);
		switch(channels){
			case 1:{
				tmpPosition=0;
				//System.out.println("first "+GetNextChunk());
				tmpPosition=0;
				break;
			}
			case 2:{
				switch(Sel_channel){
					case 1:{
						tmpPosition=1;
						break;	
					}
					default:{
						tmpPosition=0;
						break;
					}
				}
			break;		
			}
		}		
	
	}


	/* (non-Javadoc)
	 * @see dsp.Input#getNext()
	 */
	public DataChunk getNext() {
		//System.out.println("next Chunk");	
		// TODO Auto-generated method stub
		double nextChunk=5;
		switch(channels){
			case 1:{
				nextChunk=GetNextChunk();
				break;
			}
			case 2:{
				switch(Sel_channel){
					case 2:{
						nextChunk=GetNextChunk();						
						break;			
					}
					default:{
						nextChunk=GetNextChunk();
						if(sampleSize==8)
							tmpPosition+=1;
						else
							tmpPosition+=2;
						
						break;
					}
				}
				break;
			}
		}
		//System.out.println("next Chunk = "+nextChunk);
		return new SingleDataChunk(nextChunk);
		

	}
	
	public double GetNextChunk(){
		double nextChunk=5;
		if(sampleSize==8){
			nextChunk=pData[tmpPosition++];
		}
		if(sampleSize==16){
			byte [] b={pData[tmpPosition+1],pData[tmpPosition]};
			nextChunk=(((new BigInteger(1,b)).intValue())/256);	
			tmpPosition+=2;
		}	
		return nextChunk;
	}

	/* (non-Javadoc)
	 * @see dsp.Input#stop()
	 */
	public void stop() {
		try {
			// TODO Auto-generated method stub
			myInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/* (non-Javadoc)
	 * @see dsp.Input#getFrequency()
	 */
	public int getFrequency() {
		// TODO Auto-generated method stub
		tempSampleRate=0;
		switch(channels){
			case 1:{
				tempSampleRate=(int)sampleRate;
				break;
			}
			case 2:{
				switch(Sel_channel){
					case 2:{
						tempSampleRate=(int)(sampleRate*2);

						break;
					}
					default:{
						tempSampleRate=(int)sampleRate;
						break;
					}
				}
			break;
			}
		}
		
		
		//System.out.println("sample rate = "+(int)tempSampleRate);
		return (int)tempSampleRate;
	
	}
    
    
	
}

