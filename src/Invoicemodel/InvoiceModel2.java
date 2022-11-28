package Invoicemodel;



import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoiceModel2 extends AbstractTableModel {
    private final ArrayList<InvoiceHeade> invoices;
    private final String[] columns = {"No.", "Date", "Customer", "Total"};
    
    public InvoiceModel2(ArrayList<InvoiceHeade> invoices) {
        this.invoices = invoices;
    }
    
    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeade invoice = invoices.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return invoice.getIdNumber();
            case 1: return invoice.getInvoiceDate();
            case 2: return invoice.getCustomerName();
            case 3: return invoice.getInvoiceTotal();
            default : return "";
        }
    }
}