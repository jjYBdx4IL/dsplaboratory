/*
 * Created on Feb 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dsp.serial;


import java.awt.event.*;
import java.awt.Dialog;
import java.awt.Event;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import javax.swing.*;


/**
 * @author Radu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SerialInputDialog extends JDialog implements ActionListener,ItemListener{
		
		private JLabel 		portNameLabel, baudLabel, flowControlInLabel, flowControlOutLabel, 
							databitsLabel, stopbitsLabel, parityLabel;

		private JComboBox 	portChoice, baudChoice, flowChoiceIn, flowChoiceOut, 
							databitsChoice, stopbitsChoice,  parityChoice;

		public JButton 		cancelButton, OkButton;


		private int ct_c1 = 2,ct_c2 = 2,ct_c3 = 2;

		static private final String newline = "\n";
		private  boolean okeyed ;
		
		SerialParametrii parameters;
		String str, a;

		int ceva = 0;
		
		private String portName;
	    private int baudRate;
	    private int flowControlIn;
	    private int flowControlOut;
	    private int databits;
	    private int stopbits;
	    private int parity;
	    SerialInput connection;
	    
	    

	public SerialInputDialog(Dialog parent)throws HeadlessException{
			super(parent, "SerialInputFactory",true);
			parameters = new SerialParametrii();
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			//addWindowListener(new CloseHandler(this));
			init();
			//connection = new serialConectare(this, parameters, messageAreaOut, messageAreaIn);
			setConfigurationPanel();
			this.resize(455,300);
	}
	void init(){

        getContentPane().setLayout(null);
        
       
        ImageIcon opportButtonIcon = new ImageIcon(SerialInputDialog.class.getResource("aa.GIF"));
        ImageIcon ExitButtonIcon = new ImageIcon(SerialInputDialog.class.getResource("error.gif"));
        
        portNameLabel = new JLabel("Port Name:");
        portChoice = new JComboBox();         
 	    listPortChoices();
 	    portChoice.setSelectedItem(parameters.getPortName());
 	    portChoice.addActionListener(this);
 	    

 	    baudLabel = new JLabel("Baud Rate:");

        baudChoice = new JComboBox();
 	    baudChoice.addItem("300");
 	    baudChoice.addItem("2400");
 	    baudChoice.addItem("9600");
 	    baudChoice.addItem("14400");
 	    baudChoice.addItem("28800");
 	    baudChoice.addItem("38400");
 	    baudChoice.addItem("57600");
 	    baudChoice.addItem("152000");
 	    baudChoice.setSelectedItem(Integer.toString(parameters.getBaudRate()));
 	    baudChoice.addActionListener(this);
        
 	    flowControlInLabel = new JLabel("Flow Control In:");

        flowChoiceIn = new  JComboBox();
 	    flowChoiceIn.addItem("None");
 	    flowChoiceIn.addItem("Xon/Xoff In");
 	    flowChoiceIn.addItem("RTS/CTS In");
 	    flowChoiceIn.setSelectedItem(parameters.getFlowControlInString());
 	    flowChoiceIn.addActionListener(this);
 	    
 	    flowControlOutLabel = new JLabel("Flow Control Out:");

        flowChoiceOut = new JComboBox();
 	    flowChoiceOut.addItem("None");
 	    flowChoiceOut.addItem("Xon/Xoff Out");
 	    flowChoiceOut.addItem("RTS/CTS Out");
 	    flowChoiceOut.setSelectedItem(parameters.getFlowControlOutString());
 	    flowChoiceOut.addActionListener(this);
 	    
        databitsLabel = new JLabel("Data Bits:");

        databitsChoice = new JComboBox();
 	    databitsChoice.addItem("5");
 	    databitsChoice.addItem("6");
 	    databitsChoice.addItem("7");
 	    databitsChoice.addItem("8");
 	    databitsChoice.setSelectedItem(parameters.getDatabitsString());
 	    databitsChoice.addActionListener(this);
 	    
        stopbitsLabel = new JLabel("Stop Bits:");

        stopbitsChoice = new JComboBox();
 	    stopbitsChoice.addItem("1");
 	    stopbitsChoice.addItem("1.5");
 	    stopbitsChoice.addItem("2");
 	    stopbitsChoice.setSelectedItem(parameters.getStopbitsString());
 	    stopbitsChoice.addActionListener(this);
 	    
        parityLabel = new JLabel("Parity:");

        parityChoice = new JComboBox();
 	    parityChoice.addItem("None");
 	    parityChoice.addItem("Even");
 	    parityChoice.addItem("Odd");
 	    parityChoice.setSelectedItem(parameters.getParityString());
 	    parityChoice.addActionListener(this);

       
        OkButton = new JButton("OK",opportButtonIcon);
        cancelButton = new JButton("Exit",ExitButtonIcon);
        
        getContentPane().add(portNameLabel);
        portNameLabel.reshape(10,10,100,30);

        getContentPane().add(portChoice);
        portChoice.reshape(100,10,100,30);

        getContentPane().add(baudLabel);
        baudLabel.reshape(230,10,100,30);

        getContentPane().add(baudChoice);
        baudChoice.reshape(330,10,100,30);

        
        getContentPane().add(flowControlInLabel);
        flowControlInLabel.reshape(10,50,100,30);

        getContentPane().add(flowChoiceIn);
        flowChoiceIn.reshape(100,50,100,30);

        getContentPane().add(flowControlOutLabel);
        flowControlOutLabel.reshape(230,50,100,30);

        getContentPane().add(flowChoiceOut);
        flowChoiceOut.reshape(330,50,100,30);


 	    getContentPane().add(databitsLabel);
        databitsLabel.reshape(10,90,100,30);


 	    getContentPane().add(databitsChoice);
        databitsChoice.reshape(100,90,100,30);

 	    getContentPane().add(stopbitsLabel);
        stopbitsLabel.reshape(230,90,100,30);


  	    getContentPane().add(stopbitsChoice);
        stopbitsChoice.reshape(330,90,100,30);

 	    getContentPane().add(parityLabel);
        parityLabel.reshape(10,130,100,30);

 	    getContentPane().add(parityChoice);
        parityChoice.reshape(100,130,100,30);
        
        getContentPane().add(OkButton);
        OkButton.reshape(200,200,100,40);
        OkButton.addActionListener(this);
        OkButton.setMnemonic('o');
        OkButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        OkButton.setHorizontalTextPosition(AbstractButton.CENTER);
        
        getContentPane().add(cancelButton);
        cancelButton.reshape(305,200,100,40);
        cancelButton.addActionListener(this);
        cancelButton.setMnemonic('e');
        cancelButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        cancelButton.setHorizontalTextPosition(AbstractButton.CENTER);

        setConfigurationPanel();
                                 
}
/**
* @author Radu
* 
*/ 
	public void setConfigurationPanel() {
	    portChoice.setSelectedItem(parameters.getPortName());
	    baudChoice.setSelectedItem(parameters.getBaudRateString());
	    flowChoiceIn.setSelectedItem(parameters.getFlowControlInString());
	    flowChoiceOut.setSelectedItem(parameters.getFlowControlOutString());
	    databitsChoice.setSelectedItem(parameters.getDatabitsString());
	    stopbitsChoice.setSelectedItem(parameters.getStopbitsString());
	    parityChoice.setSelectedItem(parameters.getParityString());
} 



public void setParameters() {	
     	parameters.setPortName(String.valueOf(portChoice.getSelectedItem()));
     	parameters.setBaudRate(Integer.parseInt(String.valueOf(baudChoice.getSelectedItem())));
     	parameters.setFlowControlIn(String.valueOf(flowChoiceIn.getSelectedItem()));
        parameters.setFlowControlOut(String.valueOf(flowChoiceOut.getSelectedItem()));
        parameters.setDatabits(Integer.parseInt(String.valueOf(databitsChoice.getSelectedItem())));
        parameters.setStopbits(Integer.parseInt(String.valueOf(stopbitsChoice.getSelectedItem())));
        parameters.setParity(String.valueOf(parityChoice.getSelectedItem()));
}

	 void listPortChoices() {
	    CommPortIdentifier portId;

	    Enumeration en = CommPortIdentifier.getPortIdentifiers();

	    // iterate through the ports.
	    while (en.hasMoreElements()) {
	        portId = (CommPortIdentifier) en.nextElement();
	        if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	            portChoice.addItem(portId.getName());
	        }
	    }
	    // portChoice.setSelectedItem(parameters.getPortName());
	}
	  
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Exit")){
	 
	  		dispose();
	  	}
		if(e.getActionCommand().equals("OK")){
			
			parameters.setPortName(String.valueOf(portChoice.getSelectedItem()));
	     	parameters.setBaudRate(Integer.parseInt(String.valueOf(baudChoice.getSelectedItem())));
	     	parameters.setFlowControlIn(String.valueOf(flowChoiceIn.getSelectedItem()));
	        parameters.setFlowControlOut(String.valueOf(flowChoiceOut.getSelectedItem()));
	        parameters.setDatabits(Integer.parseInt(String.valueOf(databitsChoice.getSelectedItem())));
	        parameters.setStopbits(Integer.parseInt(String.valueOf(stopbitsChoice.getSelectedItem())));
	        parameters.setParity(String.valueOf(parityChoice.getSelectedItem()));
			okeyed = true;	  	
			dispose();
		}
	}
	 public void itemStateChanged(ItemEvent e) {
	    
	    if (connection.endOfInput()) {
		
	    	if (e.getItemSelectable() == portChoice) {
		   
		    SerialAlertDialog ad = new SerialAlertDialog( "Port Open!");

		    setConfigurationPanel();
		    return;
		}
		setParameters();
		/*try {
		    //connection.setConnectionParameters();
		} catch (serialConnectionException ex) {
		    serialAlertDialog ad = new serialAlertDialog("Unsupported Configuration!");
		    setConfigurationPanel();
		}*/
	    } else {
	    	setParameters();
	    }
	}
	public boolean isOK()
	    {
	        return okeyed;
	    }
	
	public String getPortName() {
		return parameters.portName;
	    }
	
	public int getBaudRate() {
		return parameters.baudRate;
	    }
	
	public int getFlowControlIn() {
		return parameters.flowControlIn;
	    }
	  
	public int getFlowControlOut() {
		return parameters.flowControlOut;
	    }
	    
	 public int getDatabits() {
			return parameters.databits;
		    }
	    
	 public int getStopbits() {
			return parameters.stopbits;
		    }
	    
	 public int getParity() {
			return parameters.parity;
		    }
	
	}
	  



