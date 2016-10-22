

package dsp.serial;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class SerialAlertDialog extends JDialog implements ActionListener {

	 public JLabel info1 = new JLabel("Error Opening Port!");
	 public JLabel info = new JLabel(" Error opening port,");
	 public JLabel info2;
	 
	public SerialAlertDialog(String a){
		 		ImageIcon exitButtonIcon1 = new ImageIcon("error.gif");
		        info2 = new JLabel(a);
			    JButton ok = new JButton("Ok",exitButtonIcon1);
			    ok.setActionCommand("ok");
			    ok.setVerticalTextPosition(AbstractButton.BOTTOM);
			    ok.setHorizontalTextPosition(AbstractButton.CENTER);
			    ok.addActionListener(this);
			    getContentPane().setLayout(null);
			    getContentPane().add(info);
			    getContentPane().add(info1);
			    getContentPane().add(info2);
			    getContentPane().add(ok);
			    resize(210,200);
			    setLocation(100,100);
			    setTitle("Alerta");
			    setResizable(false);
			    info.reshape(20,45,400,30);
			    info1.reshape(20,15,400,30);
			    info2.reshape(5,75,400,30);
			    ok.reshape(80,120,50,40);
			    Font font = new Font("TimesRoman", Font.BOLD, 14);
			    info1.setFont(font);
			    info.setFont(font);
			    setVisible(true);
			   

	}

   
    public void actionPerformed(ActionEvent e) {
    			setVisible(false);
    			dispose();
    }
}
