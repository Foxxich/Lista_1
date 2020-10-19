package org.example;
public class ChangeableTableData extends TableData {

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

}
