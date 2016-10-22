/*
 * Created on Feb 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsp.voc;
import dsp.DataChunk;
import dsp.Output;
import java.io.*;


/**
 * @author Sir Costy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VocOutput implements Output{
	private long sampleRate;
	private int sampleSize;
	private File WriteFile;
	private DataOutputStream myOutputStream;
	private byte [] pData = new byte[16384];
	byte [] BlockHeader16Biti = new byte[16];
	byte [] BlockHeader8Biti = new byte[6];
	private int pDataPos;
	private boolean WriteOpenOk;
	public VocOutput(File pWriteFile,int psampleRate,int psampleSize) {
		super();
		sampleRate =psampleRate;
		sampleSize=psampleSize;
		WriteFile = pWriteFile;
	
		
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dsp.Output#putNext(dsp.DataChunk)
	 */
	public void putNext(DataChunk d)
    {
        //System.out.print("out chunk: ");
        if(WriteOpenOk)
        for(int i = 0; i < d.getSize(); i++){
        	//System.out.print("next chunk" + d.getElement(i) + " ");
        	if(sampleSize==1)
        		WriteToFile16Biti(d.getElement(i));
        	else
        		WriteToFile8Biti(d.getElement(i));
        	
        }
            
        
    }
//----------------------------------------------------------------------------------------
	public byte GetHighPart(double d){
		//System.out.println("next chunk double" + d + " ");
		short sType = (short)d;
		//System.out.println("next chunk short" + sType + " ");
		sType >>= 8;
		//System.out.println("next chunk short>>" + sType + " ");
	//	System.out.println("next chunk byte High" + (byte)sType + " ");
		return (byte)(sType);
		
	}
//----------------------------------------------------------------------------------------    
	public byte GetLowPart(double d){
	//	System.out.println("next chunk double" + d + " ");
		short sType = (short)d;
	//	System.out.println("next chunk short" + sType + " ");
		sType<<=8;
	//	System.out.println("next chunk short<<" + sType + " ");
		sType>>=8;
	//	System.out.println("next chunk short>>" + sType + " ");
	//	System.out.println("next chunk byte Low" + (byte)sType + " ");
		
		return (byte) (sType);	
	}
//  ----------------------------------------------------------------------------------------
	public byte Get1Part(double d){
		int sType = (int)d;
		return (byte) (sType >> 24);
		
	}
//	----------------------------------------------------------------------------------------
	public byte Get2Part(double d){
		int sType = (int)d;
		sType<<=8;
		return (byte) (sType >> 24);
		
	}
//	----------------------------------------------------------------------------------------
	public byte Get3Part(double d){
		int sType = (int)d;
		
		sType<<=16;
		return (byte) (sType >> 24);
		
	}
//	----------------------------------------------------------------------------------------
	public byte Get4Part(double d){
		int sType = (int)d;
		sType<<=24;
		return (byte) (sType >> 24);
		
	}
//	----------------------------------------------------------------------------------------
	public void WriteToFile16Biti(double d){
		//System.out.println("value"+d);
		d=d*256.0;
		if(pDataPos==16384){
			try{
				myOutputStream.write(BlockHeader16Biti,0,16);
				myOutputStream.write(pData,0,16384);	
			}
			catch(Exception e){
				System.out.println("Bad Output");	
			}
		pDataPos=0;	
		}
		// indiferent daca am un bloc plin sau nu;
		//System.out.println("ori value"+d);
		pData[pDataPos++]=GetLowPart(d);
	//	System.out.println("Low Part"+GetLowPart(d));
		pData[pDataPos++]=GetHighPart(d);	
	//	System.out.println("High Part"+GetHighPart(d));
	}
//--------------------------------------------------------------------------------------------
	public void WriteToFile8Biti(double d){		
		
		if(pDataPos==16384){
		//	System.out.println("Gata Un Bloc");
			try{
				myOutputStream.write(BlockHeader8Biti,0,6);
				myOutputStream.write(pData,0,16384);	
			}
			catch(Exception e){
				System.out.println("Bad Output");	
			}
		pDataPos=0;	
		}
		// indiferent daca am un bloc plin sau nu;
		pData[pDataPos++]=(byte)(d);
	//	System.out.println("pDataPos"+pDataPos);
		
	}
	
//----------------------------------------------------------------------------------------
	public void start()
    {
		//System.out.println("Starting Output");
    	try{
    		WriteOpenOk=true;
    		pDataPos=0;
    		myOutputStream= new DataOutputStream(new FileOutputStream(WriteFile));
    		byte [] FileHeader = {'C','r','e','a','t','i','v','e',' ','V','o','i','c','e',' ','F','i','l','e',
								0x1A,0x1A,0x00,0x0A,0x01,0x29,0x11};
    		myOutputStream.write(FileHeader,0,26);

    		if(sampleSize==1){    			
        		
        		BlockHeader16Biti[0]=0x09;
        		BlockHeader16Biti[1]=0x0C; 
    			BlockHeader16Biti[2]=0x40;
    			BlockHeader16Biti[3]=0x00;
        		BlockHeader16Biti[4]=Get4Part(sampleRate);
        		BlockHeader16Biti[5]=Get3Part(sampleRate);
        		BlockHeader16Biti[6]=Get2Part(sampleRate);
        		BlockHeader16Biti[7]=Get1Part(sampleRate);
        		BlockHeader16Biti[8]=0x10;
        		BlockHeader16Biti[9]=0x01;
        		BlockHeader16Biti[10]=0x04;
    			BlockHeader16Biti[11]=0x00;
    		}
    		else{
    			BlockHeader8Biti[0]=0x01;
    			BlockHeader8Biti[1]=0x02;
    			BlockHeader8Biti[2]=0x40;
    			BlockHeader8Biti[3]=0x00;
    			BlockHeader8Biti[4]=GetHighPart(65536 - (256000000/sampleRate));
    			BlockHeader8Biti[5]=0x00;
    			
    			
    		}
    		
    		
            System.out.println("FinishStarting Output");
    	}
    	catch(Exception e){
    		WriteOpenOk=false;
    		System.out.println("Bad Output");
    	}
    }

    public void stop(){
    	/*System.out.println("Last Size = "+pDataPos);
    	System.out.println("Last 4 = "+pDataPos);
    	System.out.println("Last  = "+pDataPos);
    	System.out.println("Last Size = "+pDataPos);*/
    	if(sampleSize==1){
    		BlockHeader16Biti[1]=Get4Part(pDataPos+12);
        	BlockHeader16Biti[2]=Get3Part(pDataPos+12);
        	BlockHeader16Biti[3]=Get2Part(pDataPos+12);
        	try{
        		myOutputStream.write(BlockHeader16Biti,0,16);
        		pData[pDataPos++]=0x00;
    			myOutputStream.write(pData,0,pDataPos);
        	}
        	catch(Exception e){
        		System.out.println("Bad Output");	
        	}
    	}
    	else{
    		BlockHeader8Biti[1]=Get4Part(pDataPos+2);
        	BlockHeader8Biti[2]=Get3Part(pDataPos+2);
        	BlockHeader8Biti[3]=Get2Part(pDataPos+2);
        	try{
        		myOutputStream.write(BlockHeader8Biti,0,6);
        		pData[pDataPos++]=0x00;
    			myOutputStream.write(pData,0,pDataPos);
        	}
        	catch(Exception e){
        		System.out.println("Bad Output");	
        	}
    	}
    	try {
    		myOutputStream.flush();
			myOutputStream.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
        System.out.println("Finished Output");
    }
}
