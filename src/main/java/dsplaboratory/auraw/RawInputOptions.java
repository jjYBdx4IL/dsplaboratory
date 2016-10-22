/**
 * Write a description of class RawInputOptions here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
package dsplaboratory.auraw;

import java.awt.*;

import javax.swing.*;

public class RawInputOptions extends JPanel {

    ButtonGroup group;
    JComboBox OctetPerEsantion;

    public RawInputOptions() {
        setPreferredSize(new Dimension(150, 150));
        setLayout(new BorderLayout());
        JLabel eticheta1 = new JLabel("  Raw File Reading Properties ");
        add(eticheta1, BorderLayout.NORTH);
//-----------------------------------------------------------------------------
        JPanel PanouCentral = new JPanel();
        PanouCentral.setLayout(null);
        //radio buttons
        JRadioButton OctetCuSemn = new JRadioButton("Signed byte");
        OctetCuSemn.setSelected(true);
        OctetCuSemn.setBounds(0, 10, 100, 20);
        JRadioButton OctetFaraSemn = new JRadioButton("Unsigned byte");
        OctetFaraSemn.setBounds(0, 30, 120, 20);
        // Group the radio buttons.
        group = new ButtonGroup();
        group.add(OctetCuSemn);
        group.add(OctetFaraSemn);
        PanouCentral.add(OctetCuSemn);
        PanouCentral.add(OctetFaraSemn);
//-----------------------------------------------------------------------------
        JLabel eticheta2 = new JLabel("Bytes/Sample");
        eticheta2.setBounds(5, 60, 75, 20);
        String[] Strings = {"1", "2"};
        OctetPerEsantion = new JComboBox<String>(Strings);
        OctetPerEsantion.setSelectedIndex(0);
        OctetPerEsantion.setBounds(85, 60, 30, 20);
        PanouCentral.add(eticheta2);
        PanouCentral.add(OctetPerEsantion);
//-----------------------------------------------------------------------------
        add(PanouCentral, BorderLayout.CENTER);
    }
}
