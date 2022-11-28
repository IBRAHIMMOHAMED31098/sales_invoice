package Invoicemodel;

import java.util.ArrayList;

public class InvoiceHeade {
    private int id_Number;
    private String invoice_Date;
    private String invoice_Customer_Name;
    private ArrayList<InvoiceLine> lines;
    
    public InvoiceHeade() {
    }

    public InvoiceHeade(int num, String date, String customer) {
        this.id_Number = num;
        this.invoice_Date = date;
        this.invoice_Customer_Name = customer;
    }

    public double getInvoiceTotal() {
        double total = 0.0;
        for (InvoiceLine line : getLines()) {
            total += line.getLineTotal();
        }
        return total;
    }
    
    public ArrayList<InvoiceLine> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public String getCustomerName() {
        return invoice_Customer_Name;
    }

    public void setCustomerName(String customer) {
        this.invoice_Customer_Name = customer;
    }

    public int getIdNumber() {
        return id_Number;
    }

    public void setIdNumber(int num) {
        this.id_Number = num;
    }

    public String getInvoiceDate() {
        return invoice_Date;
    }

    public void setInvoiceDate(String date) {
        this.invoice_Date = date;
    }

    @Override
    public String toString() {
        return "Invoice{" + "num=" + id_Number + ", date=" + invoice_Date + ", customer=" + invoice_Customer_Name + '}';
    }
        
        public String getAsCSV() {
        return id_Number + "," + invoice_Date + "," + invoice_Customer_Name;
    }
    }
    
   