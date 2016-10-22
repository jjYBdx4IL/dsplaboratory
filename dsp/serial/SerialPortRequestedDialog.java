

package dsp.serial;
import java.awt.*;
import java.awt.event.*;



public class SerialPortRequestedDialog extends Dialog implements ActionListener {
    
    private SerialInputDialog parent;

   
    public SerialPortRequestedDialog(SerialInputDialog parent) {
	super(parent, "Port Requested!", true);
	this.parent = parent;

	String lineOne = "Your port has been requested";
	String lineTwo = "by an other application.";
	String lineThree = "Do you want to give up your port?";
	Panel labelPanel = new Panel();
	labelPanel.setLayout(new GridLayout(3, 1));
	labelPanel.add(new Label(lineOne, Label.CENTER));
	labelPanel.add(new Label(lineTwo, Label.CENTER));
	labelPanel.add(new Label(lineThree, Label.CENTER));
	add(labelPanel, "Center");

	Panel buttonPanel = new Panel();
	Button yesButton = new Button("Yes");
	yesButton.addActionListener(this);
	buttonPanel.add(yesButton);
	Button noButton = new Button("No");
	noButton.addActionListener(this);
	buttonPanel.add(noButton);
	add(buttonPanel, "South");

	FontMetrics fm = getFontMetrics(getFont());
	int width = Math.max(fm.stringWidth(lineOne), 
		 Math.max(fm.stringWidth(lineTwo), fm.stringWidth(lineThree)));

	setSize(width + 40, 150);
	setLocation(parent.getLocationOnScreen().x + 30, 
		    parent.getLocationOnScreen().y + 30);
	setVisible(true);
    }

   
    public void actionPerformed(ActionEvent e) {
	String cmd = e.getActionCommand();

	    if (cmd.equals("Yes")) {
		//parent.portClosed();
	    }
			
	    setVisible(false);
	    dispose();
    }
}
