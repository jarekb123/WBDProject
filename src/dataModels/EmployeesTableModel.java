package dataModels;

import javax.swing.table.AbstractTableModel;

/**
 * Created by jaroslaw on 15.01.2017.
 */
public class EmployeesTableModel extends AbstractTableModel {



    private String[] columnNames = {"ID", "Imię", "Nazwisko", "Typ uprawnień"};
    private EmployeeSimple[] data;

    public void getDataFromDataProvider(DataProvider provider)
    {
        data = provider.getEmployeesList();
    }
    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex)
        {
            case 0:
                return data[rowIndex].getId();
            case 1:
                return data[rowIndex].getName();
            case 2:
                return data[rowIndex].getSurname();
            case 3:
                return data[rowIndex].getAuthorityType();
        }
        return null;
    }
}
