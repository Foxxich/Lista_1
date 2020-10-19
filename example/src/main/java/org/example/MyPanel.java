package org.example;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class MyPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private TableData tableData = new ChangeableTableData();
    private FileOperations fileOperations = new FileOperations();
    private Row rows = new Row();

    private ArrayList<TableData> tables = new ArrayList<>();
    private JScrollPane jScrollPane = new JScrollPane();
    private JTable jTable = null;

    private JButton addColumnButton, removeColumnButton, addRowButton, removeRowButton, save, load;

    private JTextField addColumnName, removeColumnName;

    public MyPanel() {
        jScrollPane.setSize( this.getWidth(),
                this.getHeight() - 100);
        add(jScrollPane, BorderLayout.CENTER);

        addColumnName = new JTextField();
        addColumnName.setBounds(0,860,105,30);
        add(addColumnName);
        addColumnButton = new JButton("Add column");
        addColumnButton.setBounds(0,900,105,30);
        addColumnButton.addActionListener(e->{
            String header1= addColumnName.getText();
            tableData.addColumn(header1);
            refreshTable();
        });
        add(addColumnButton, BorderLayout.SOUTH);

        removeColumnName = new JTextField();
        removeColumnName.setBounds(115,860,115,30);
        add(removeColumnName);
        removeColumnButton = new JButton("Delete column");
        removeColumnButton.setBounds(115,900,115,30);
        removeColumnButton.addActionListener(e->{
            String header2= removeColumnName.getText();
            tableData.removeColumn(header2);
            refreshTable();
        });
        add(removeColumnButton, BorderLayout.SOUTH);
        
        addRowButton =new JButton("Add row");
        addRowButton.setBounds(240,900,115,30);
        addRowButton.addActionListener(e->{

            ArrayList<String> headers = tableData.getHeaders();
            Row row = new Row(headers);
        	tableData.addRow(row);
        	refreshTable();
        });
        add(addRowButton);

        removeRowButton =new JButton("Delete row");
        removeRowButton.setBounds(365,900,115,30);
        removeRowButton.addActionListener(e->{
            try {
                tableData.deleteLastRow();
            } catch (IllegalStateException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage());
            }
            refreshTable();
        });
        add(removeRowButton);
        
        save=new JButton("Load");
        save.setBounds(490,900,115,30);
        save.addActionListener(e->{
            JFileChooser fileChooser = new JFileChooser(Paths.get(".").toAbsolutePath().toString());
            fileChooser.setDialogTitle("Select a table");
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Binary files", "bin");
            fileChooser.addChoosableFileFilter(filter);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION)
            {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    tableData = fileOperations.load(filePath);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(this, ioException.getMessage());
                }
            }
            refreshTable();
        });
        add(save);
        
        load=new JButton("Save");
        load.setBounds(615,900,115,30);
        load.addActionListener(e->{
            JFileChooser fileChooser = new JFileChooser(Paths.get(".").toAbsolutePath().toString());
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {

                String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                if (!fileChooser.getSelectedFile().getName().endsWith(".bin"))
                    filePath += ".bin";

                try {
                    fileOperations.save(tableData, filePath);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(this, ioException.getMessage());
                }
            }
            refreshTable();
        });
        add(load);
        

        setExampleTable();

        setTable();

    }

    private void refreshTable() {
        this.remove(jScrollPane);

        jTable = new JTable(tableData);

        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setMinimumSize(this.getSize());
        jTable.setFillsViewportHeight(true);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private void setExampleTable() {
        String[] columnNames = {"Column 1", "Column 2"};
        ArrayList<String> headers = new ArrayList<>(Arrays.asList(columnNames));
        tableData.setHeaders(headers);
        ArrayList<Row> rows = new ArrayList<>();
        Row row = new Row();
        row.setValue("Column 1", "Row 1.1");
        row.setValue("Column 2", "Row 1.2");

        rows.add(row);

        row = new Row();
        row.setValue("Column 1", "Row 2.1");
        row.setValue("Column 2", "Row 2.2");

        rows.add(row);
        tableData.setRows(rows);
    }

    private void setTable() {
        jTable = new JTable(tableData);

        jScrollPane = new JScrollPane(jTable);
        jScrollPane.setMinimumSize(this.getSize());
        jTable.setFillsViewportHeight(true);
        this.setLayout(new BorderLayout());
        this.add(jScrollPane, BorderLayout.CENTER);
    }

    public ArrayList<TableData> getTables() {
        return tables;
    }

    public void setTables(ArrayList<TableData> tables) {
        this.tables = tables;
        setTable();
    }

}