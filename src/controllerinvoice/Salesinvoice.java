package controllerinvoice;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import Invoicemodel.InvoiceHeade;
import Invoicemodel.InvoiceModel2;
import Invoicemodel.InvoiceLine;
import Invoicemodel.InvoiceTable;
import java.awt.HeadlessException;
import sales.view.Invoicehead2;
import sales.view.JFrame;
import sales.view.InvoiceLine2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Salesinvoice implements ActionListener, ListSelectionListener {

    private final JFrame Frame;
    private Invoicehead2 invoice_Dialog;
    private InvoiceLine2 line_Dialog;

    public Salesinvoice(JFrame frame) {
        this.Frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action: " + actionCommand);
        switch (actionCommand) {
            case "Load File":
                loadFile();
                break;
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Create New Item":
                createNewItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "createInvoiceCancel":
                createInvoiceCancel();
                break;
            case "createInvoiceAdd":
                createInvoiceOK();
                break;
            case "createLineAdd":
                createLineOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
            case "Save File":
                saveFile();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = Frame.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1) {
            System.out.println("You have selected row: " + selectedIndex);
            InvoiceHeade currentInvoice = Frame.getInvoices().get(selectedIndex);
            Frame.getInvoiceNumLabel().setText("" + currentInvoice.getIdNumber());
            Frame.getInvoiceDateLabel1().setText("" + currentInvoice.getInvoiceDate());
            Frame.getCustomerNameLabel().setText(currentInvoice.getCustomerName());
            Frame.getInvoiceTotalLabel().setText("" + currentInvoice.getInvoiceTotal());
            InvoiceTable linesTableModel = new InvoiceTable(currentInvoice.getLines());
            Frame.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }
    }

    private void loadFile() {
        JFileChooser fc = new JFileChooser();

        try {
            JOptionPane.showMessageDialog(Frame, "Select Invoice Header File",
                    "Information Message", JOptionPane.INFORMATION_MESSAGE);
            int result = fc.showOpenDialog(Frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                System.out.println("Invoices have been read");
                ArrayList<InvoiceHeade> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    try {
                        String[] headerParts = headerLine.split(",");
                        int invoiceNum = Integer.parseInt(headerParts[0]);
                        String invoiceDate = headerParts[1];
                        String customerName = headerParts[2];

                        InvoiceHeade invoice = new InvoiceHeade(invoiceNum, invoiceDate, customerName);
                        invoicesArray.add(invoice);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Frame, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                System.out.println("Check point");
                JOptionPane.showMessageDialog(Frame, "Select Invoice Line File",
                        "Information Message", JOptionPane.INFORMATION_MESSAGE);
                result = fc.showOpenDialog(Frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    System.out.println("Lines have been read");
                    for (String lineLine : lineLines) {
                        try {
                            String lineParts[] = lineLine.split(",");
                            int invoiceNum = Integer.parseInt(lineParts[0]);
                            String itemName = lineParts[1];
                            double itemPrice = Double.parseDouble(lineParts[2]);
                            int count = Integer.parseInt(lineParts[3]);
                            InvoiceHeade inv = null;
                            for (InvoiceHeade invoice : invoicesArray) {
                                if (invoice.getIdNumber() == invoiceNum) {
                                    inv = invoice;
                                    break;
                                }
                            }

                            InvoiceLine line = new InvoiceLine(itemName, itemPrice, count, inv);
                            inv.getLines().add(line);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(Frame, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                    
                        }
                    }
                    System.out.println("Check point");
                }
                Frame.setInvoices(invoicesArray);
                InvoiceModel2 invoicesTableModel = new InvoiceModel2(invoicesArray);
                Frame.setInvoicesTableModel(invoicesTableModel);
                Frame.getInvoiceTable().setModel(invoicesTableModel);
                Frame.getInvoicesTableModel().fireTableDataChanged();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(Frame, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFile() {
        ArrayList<InvoiceHeade> invoices = Frame.getInvoices();
        String headers = "";
        String lines = "";
        for (InvoiceHeade invoice : invoices) {
            String invCSV = invoice.getAsCSV();
            headers += invCSV;
            headers += "\n";

            for (InvoiceLine line : invoice.getLines()) {
                String lineCSV = line.getAsCSV();
                lines += lineCSV;
                lines += "\n";
            }
        }
        System.out.println("Check point");
        
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(Frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                try (FileWriter hfw = new FileWriter(headerFile)) {
                    hfw.write(headers);
                    hfw.flush();
                }
                result = fc.showSaveDialog(Frame);
                JOptionPane.showMessageDialog(Frame, "File saved successfully",
           "Information Message", JOptionPane.INFORMATION_MESSAGE);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    try (FileWriter lfw = new FileWriter(lineFile)) {
                        lfw.write(lines);
                        lfw.flush();
                    }
                }
            }
        } catch (HeadlessException | IOException ex) {
            

        }
    }

    private void createNewInvoice() {
        invoice_Dialog = new Invoicehead2(Frame);
        invoice_Dialog.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedRow = Frame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            Frame.getInvoices().remove(selectedRow);
            Frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createNewItem() {
        line_Dialog = new InvoiceLine2(Frame);
        line_Dialog.setVisible(true);
    }

    private void deleteItem() {
        int selectedRow = Frame.getLineTable().getSelectedRow();

        if (selectedRow != -1) {
            InvoiceTable linesTableModel = (InvoiceTable) Frame.getLineTable().getModel();
            linesTableModel.getLines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            Frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createInvoiceCancel() {
        invoice_Dialog.setVisible(false);
        invoice_Dialog.dispose();
        invoice_Dialog = null;
    }

    private void createInvoiceOK() {
        String date = invoice_Dialog.getInvDateField().getText();
        String customer = invoice_Dialog.getCustNameField().getText();
        int num = Frame.getNextInvoiceNum();
        try {
            String[] dateParts = date.split("-");  // 
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(Frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(Frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    InvoiceHeade invoice = new InvoiceHeade(num, date, customer);
                    Frame.getInvoices().add(invoice);
                    Frame.getInvoicesTableModel().fireTableDataChanged();
                    invoice_Dialog.setVisible(false);
                    invoice_Dialog.dispose();
                    invoice_Dialog = null;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(Frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void createLineOK() {
        String item = line_Dialog.getItemNameField().getText();
        String countStr = line_Dialog.getItemCountField().getText();
        String priceStr = line_Dialog.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = Frame.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            InvoiceHeade invoice = Frame.getInvoices().get(selectedInvoice);
            InvoiceLine line = new InvoiceLine(item, price, count, invoice);
            invoice.getLines().add(line);
            InvoiceTable linesTableModel = (InvoiceTable) Frame.getLineTable().getModel();
            linesTableModel.fireTableDataChanged();
            Frame.getInvoicesTableModel().fireTableDataChanged();
        }
        line_Dialog.setVisible(false);
        line_Dialog.dispose();
        line_Dialog = null;
    }

    private void createLineCancel() {
        line_Dialog.setVisible(false);
        line_Dialog.dispose();
        line_Dialog = null;
    }

}
