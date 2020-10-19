package org.example;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class TableData extends AbstractTableModel implements Serializable{

    private static final long serialVersionUID = 1L;

    protected ArrayList<TableModelListener> tableModelListeners = new ArrayList<>();

    protected ArrayList<String> headers;
    protected ArrayList<Row> rows;
    
    protected int column;
    protected int row;
    
    public void addRow(Row row)
    {
        for(String str : headers) {
            if(!row.containsHeader(str)) {
                throw new IllegalArgumentException("Headers in row doesn't match target table.");
            }
        }
        rows.add(row);
    }

    public void addColumn(String header)
    {
        headers.add(header);
        for(Row row : rows) {
            row.setValue(header, "");
        }
    }

    public void removeColumn(String header)
    {
        headers.remove(header);
        for(Row row : rows) {
            row.removeValue(header);
        }
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return headers.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        String retVal = "";
        try {
            retVal = headers.get(columnIndex);
        } catch (IndexOutOfBoundsException e) {}

        return retVal;
    }

    //?
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Row row = rows.get(rowIndex);
        return row.getValue(headers.get(columnIndex));
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Row row = rows.get(rowIndex);
        row.setValue(headers.get(columnIndex), aValue.toString());
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        tableModelListeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        tableModelListeners.remove(l);
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    public void setRows(ArrayList<Row> rows) {
        this.rows = rows;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public ArrayList<Row> getRows() {
        return rows;
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }


    public void deleteLastRow() {
        if(rows.isEmpty())
            throw new IllegalStateException("Rows is empty.");
        rows.remove(rows.size() - 1);
    }
}