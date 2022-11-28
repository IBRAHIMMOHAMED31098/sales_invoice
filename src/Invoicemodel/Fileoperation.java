
package Invoicemodel;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Fileoperation {
    
    private ArrayList<InvoiceHeade> invoiceHeaders;
      
    public ArrayList<InvoiceHeade> read(){
        
        
        JFileChooser filechooser = new JFileChooser();

        try {
            JOptionPane.showMessageDialog(null, "Select Invoice Header File",
                    "Invoice Header", JOptionPane.INFORMATION_MESSAGE);
            int res = filechooser.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION) {
                File headerFile = filechooser.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                System.out.println("Invoices have been read");
                ArrayList<InvoiceHeade> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    try {
                        String[] headerParts = headerLine.split(",");
                        int invoiceNumber = Integer.parseInt(headerParts[0]);
                        String invoice_Date = headerParts[1];
                        String customer_Name = headerParts[2];

                        InvoiceHeade invoice = new InvoiceHeade(invoiceNumber, invoice_Date, customer_Name);
                        invoicesArray.add(invoice);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                System.out.println("Check point");
                JOptionPane.showMessageDialog(null, "Select Invoice Line File",
                        "Invoice Line", JOptionPane.INFORMATION_MESSAGE);
                res = filechooser.showOpenDialog(null);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File lineFile = filechooser.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    System.out.println("Lines have been read");
                    for (String lineLine : lineLines) {
                        try {
                            String lineParts[] = lineLine.split(",");
                            int invoice_Num = Integer.parseInt(lineParts[0]);
                            String item_Name = lineParts[1];
                            double item_Price = Double.parseDouble(lineParts[2]);
                            int count = Integer.parseInt(lineParts[3]);
                            InvoiceHeade inv = null;
                            for (InvoiceHeade invoice : invoicesArray) {
                                if (invoice.getIdNumber() == invoice_Num) {
                                    inv = invoice;
                                    break;
                                }
                            }

                            InvoiceLine line = new InvoiceLine(item_Name, item_Price, count, inv);
                            inv.getLines().add(line);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                           }
                        }
                    
                    System.out.println("Check point");
                    
                  }
              
                this.invoiceHeaders = invoicesArray;  
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
        return invoiceHeaders;
    }
    
    
    
    
    
    public void write(ArrayList<InvoiceHeade> invoices)
    {
        for(InvoiceHeade inv : invoices)
      {
          int invoice_Id = inv.getIdNumber();
          String invoice_date = inv.getInvoiceDate();
          String customer = inv.getCustomerName();
          System.out.println("\n Invice " + invoice_Id + "\n {\n " + invoice_date + "," + customer);
          ArrayList<InvoiceLine> lines = inv.getLines();
          for(InvoiceLine line : lines)
          {
              System.out.println( line.getLineItem() + "," + line.getLinePrice() + "," + line.getLineCount());
          }
          
          System.out.println(" } \n");
      }
        
    }
    
    
    
    
    
     public static void main(String[] args)
   {
       Fileoperation fo = new Fileoperation();
       ArrayList<InvoiceHeade> invoices = fo.read();
       fo.write(invoices);
              
   }
    
     
}
