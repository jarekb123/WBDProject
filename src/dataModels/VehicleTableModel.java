package dataModels;

import javax.swing.table.AbstractTableModel;

/**
 * Created by jaroslaw on 16.01.2017.
 */
public class VehicleTableModel extends AbstractTableModel {

    String [] columnNames = {"ID", "Nazwa", "Typ"};


    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
