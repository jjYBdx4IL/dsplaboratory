/**
**
* @author Radu
*
* TODO To change the template for this generated type comment go to
* Window - Preferences - Java - Code Style - Code Templates
*/
package dsp.serial;

import dsp.DataChunk;
import dsp.Input;
import dsp.SingleDataChunk;
import dsp.VariableDataChunk;
import dsp.exception.RecoverableInputProblem;
import javax.comm.*;

import java.io.*;
import java.awt.TextArea;
import java.util.TooManyListenersException;
import java.util.*;


public class SerialInput implements Input,SerialPortEventListener, 
						 CommPortOwnershipListener{
	   
		private SerialInputDialog parent;
	    private SerialParametrii parameters;
	    
	    private InputStream inputStream;
	    private CommPortIdentifier portId;
	    private SerialPort sPort;
	    private TextArea messageAreaIn;

        private List coada = Collections.synchronizedList(new LinkedList());
        
	    private boolean open = false;
	    public String portName;
	    public int baudRate;
	    public int flowControlIn;
	    public int flowControlOut;
	    public int databits;
	    public int stopbits;
	    public int parity;
	    
	    Thread		      readThread;
	
	    public SerialInput(String portName,int baudRate,int flowControlIn,
							int flowControlOut,int databits, int stopbits, int parity) 
	    {
		this.portName = portName;
		this.baudRate = baudRate;
		this.flowControlIn = flowControlIn;
		this.flowControlOut = flowControlOut;
		this.databits = databits;
		this.stopbits = stopbits;
		this.parity = parity;
		open = false;
	   }

	   public void openConnection() throws SerialConnectionException {

		// Obtain a CommPortIdentifier object for the port you want to open.
		try {
		    portId = 
			 CommPortIdentifier.getPortIdentifier(this.portName);
		} catch (NoSuchPortException e) {
		    throw new SerialConnectionException(e.getMessage());
		}

		try {
		    sPort = (SerialPort)portId.open("SerialAplication", 30000);
		} catch (PortInUseException e) {
		    throw new SerialConnectionException(e.getMessage());
		}

		try {
		    setConnectionParameters();
		} catch (SerialConnectionException e) {	
		    sPort.close();
		    throw e;
		}


		try {
			inputStream = sPort.getInputStream();
		} catch (IOException e) {
		    sPort.close();
		    throw new SerialConnectionException("Error opening i/o streams");
		}
	

		// Add this object as an event listener for the serial port.
		try {
		    sPort.addEventListener(this);
		} catch (TooManyListenersException e) {
		    sPort.close();
		    throw new SerialConnectionException("too many listeners added");
		}

	
		sPort.notifyOnDataAvailable(true);
		sPort.notifyOnBreakInterrupt(true);

		// Set receive timeout to allow breaking out of polling loop during
		// input handling.
		try {
		    sPort.enableReceiveTimeout(30);
		} catch (UnsupportedCommOperationException e) {
		}

		portId.addPortOwnershipListener(this);
		
//		readThread = new Thread(this);

//		readThread.start();
		open = true;
	    }


	    public void setConnectionParameters() throws SerialConnectionException {

		// Save state of parameters before trying a set.
		int oldBaudRate = sPort.getBaudRate();
		int oldDatabits = sPort.getDataBits();
		int oldStopbits = sPort.getStopBits();
		int oldParity   = sPort.getParity();
		int oldFlowControl = sPort.getFlowControlMode();

		// Set connection parameters, if set fails return parameters object
		// to original state.
		try {
		    sPort.setSerialPortParams(this.baudRate,
					      this.databits,
					      this.stopbits,
					      this.parity);
		} catch (UnsupportedCommOperationException e) {
		    parameters.setBaudRate(oldBaudRate);
		    parameters.setDatabits(oldDatabits);
		    parameters.setStopbits(oldStopbits);
		    parameters.setParity(oldParity);
		    throw new SerialConnectionException("Unsupported parameter");
		}

		// Set flow control.
		try {
		    sPort.setFlowControlMode(this.flowControlIn 
				           |  this.flowControlOut);
		} catch (UnsupportedCommOperationException e) {
		    throw new SerialConnectionException("Unsupported flow control");
		}
	    }
	    
//	    public void run() {
//	    	
//	        try {
//	    	    Thread.sleep(1000);
//	    		
//	    	} catch (InterruptedException e) {}
//
//	        } 
	    /**
	    Close the port and clean up associated elements.
	    */
	    
	    public void closeConnection() 
        {
		if (!open) {
		    return;
		}
		
		if (sPort != null) {
		    try {
		    	inputStream.close();
		    } catch (IOException e) {
			System.err.println(e);
		    }

		    sPort.close();
		    
		    portId.removePortOwnershipListener(this);
		}

		open = false;
	    }
	    
	    public void serialEvent(SerialPortEvent e) {
	 	// Create a StringBuffer and int to receive input data.
		

		// Determine type of event.
		switch (e.getEventType()) {
		    // Read data until -1 is returned. If \r is received substitute
		    // \n for correct newline handling.
			case SerialPortEvent.BI:
			case SerialPortEvent.OE:
			case SerialPortEvent.FE:
			case SerialPortEvent.PE:
	    	case SerialPortEvent.CD:
	    	case SerialPortEvent.CTS:
	    	case SerialPortEvent.DSR:
	    	case SerialPortEvent.RI:
	    	case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
	        break;
		    case SerialPortEvent.DATA_AVAILABLE:
		    
			int newData;
            try 
            {
		        newData = inputStream.read();
		    	while (newData != -1) 
                {
                    coada.add(new Integer(newData));
                    newData = inputStream.read();
	   		    }
            }
            catch (IOException ex) 
            {
                ex.printStackTrace();
                return;
            }
			break;
		/*	
		byte[] readBuffer = new byte[8];

	    try {
		while (inputStream.available() > 0) {
		    int numBytes = inputStream.read(readBuffer);
		} 

		System.out.print(new String(readBuffer));
	    } catch (IOException ea) {}
	   
	    break;*/
		    }

	    }   

	    /**
	    Handles ownership events. If a PORT_OWNERSHIP_REQUESTED event is
	    received a dialog box is created asking the user if they are 
	    willing to give up the port. No action is taken on other types
	    of ownership events.
	    */
	    public void ownershipChange(int type) {
		if (type == CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED) {
			SerialPortRequestedDialog prd = new SerialPortRequestedDialog(parent);
		}
	    }

	    public boolean endOfInput() {
				return open; 	
	    }

		public void start() {
			 try {
		    	this.openConnection();
		    } catch (SerialConnectionException e2) {
			SerialAlertDialog ad = new SerialAlertDialog(e2.getMessage() + ".");
			return;
		    }	
		}


		public DataChunk getNext() throws RecoverableInputProblem 
        {
		    Integer i = (Integer) coada.get(0);
            return new SingleDataChunk(i.doubleValue());
		}
		
		public void stop() {
			open = false;
			this.closeConnection();	
		}


		public int getFrequency() {
	
			return 100;
		
		}
	    
	}
	  



