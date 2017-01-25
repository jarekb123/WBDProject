package dataModels;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by jaroslaw on 16.01.2017.
 */
public class VehicleTable extends AbstractTableModel {

    String [] columnNames = {"ID", "Nazwa", "Typ", "Nr boczny"};
    Vehicle [] data;

    public boolean getDataFromDB(Connection c, int idPKM)
    {
        try {
            data = Vehicle.getAll(c, idPKM);
            return true;
        } catch (SQLException s) {
            s.printStackTrace();
            return false;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex)
        {
            case 0:
                return data[rowIndex].getId();
            case 1:
                return data[rowIndex].getModel().getName();
            case 2:
                return data[rowIndex].getModel().getType();
            case 3:
                return data[rowIndex].getVehicleNumber();
        }
        return null;
    }

    public Vehicle getRow(int row) {
        return data[row];
    }
}
