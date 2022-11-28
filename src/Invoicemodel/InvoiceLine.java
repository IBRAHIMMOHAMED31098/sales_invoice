package Invoicemodel;


public class InvoiceLine {
    private String line_Item;
    private double line_Price;
    private int line_Count;
    private InvoiceHeade invoice;

    public InvoiceLine() {
    }

    public InvoiceLine(String item, double price, int count, InvoiceHeade invoice) {
        this.line_Item = item;
        this.line_Price = price;
        this.line_Count = count;
        this.invoice = invoice;
    }

    public double getLineTotal() {
        return line_Price * line_Count;
    }
    
    public int getLineCount() {
        return line_Count;
    }

    public void setLineCount(int count) {
        this.line_Count = count;
    }

    public String getLineItem() {
        return line_Item;
    }

    public void setLineItem(String item) {
        this.line_Item = item;
    }

    public double getLinePrice() {
        return line_Price;
    }

    public void setLinePrice(double price) {
        this.line_Price = price;
    }

    @Override
    public String toString() {
        return "Line{" + "num=" + invoice.getIdNumber() + ", item=" + line_Item + ", price=" + line_Price + ", count=" + line_Count + '}';
    }

    public InvoiceHeade getInvoice() {
        return invoice;
    }
    public String getAsCSV() {
        return invoice.getIdNumber() + "," + line_Item + "," + line_Price + "," + line_Count;
    }
    
    
    
}