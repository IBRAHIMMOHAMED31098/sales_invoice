package sales.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoiceLine2 extends JDialog{
    private final JTextField itemNameField;
    private final JTextField itemCountField;
    private final JTextField itemPriceField;
    private final JLabel itemNameLbl;
    private final JLabel itemCountLbl;
    private final JLabel itemPriceLbl;
    private final JButton addBton;
    private final JButton cancelBton;
    
    public InvoiceLine2(JFrame frame) {
        itemNameField = new JTextField(20);
        itemNameLbl = new JLabel("Name");
        
        itemCountField = new JTextField(20);
        itemCountLbl = new JLabel("Count");
        
        itemPriceField = new JTextField(20);
        itemPriceLbl = new JLabel("Price");
        
        addBton = new JButton("Add");
        cancelBton = new JButton("Cancel");
        
        addBton.setActionCommand("createLineAdd");
        cancelBton.setActionCommand("createLineCancel");
        
        addBton.addActionListener(frame.getController());
        cancelBton.addActionListener(frame.getController());
        setLayout(new GridLayout(4, 2));
        
        add(itemNameLbl);
        add(itemNameField);
        add(itemCountLbl);
        add(itemCountField);
        add(itemPriceLbl);
        add(itemPriceField);
        add(addBton);
        add(cancelBton);
        
        pack();
    }

    public JTextField getItemNameField() {
        return itemNameField;
    }

    public JTextField getItemCountField() {
        return itemCountField;
    }

    public JTextField getItemPriceField() {
        return itemPriceField;
    }
}
