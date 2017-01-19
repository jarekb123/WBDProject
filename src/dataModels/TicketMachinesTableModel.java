package dataModels;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jaroslaw on 19.01.2017.
 */
public class TicketMachinesTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID", "Producent", "Nr ser.", "Stan", "Przystanek", "Pojazd"};
    private TicketMachine[] data;

    public void getDataFromDB(Connection c) {
        try {
            data = TicketMachine.getAllFromDB(c, 1);
        } catch (SQLException s)
        {
            s.printStackTrace();
        }
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
                return data[rowIndex].getManufacturer();
            case 2:
                return data[rowIndex].getSerialNo();
            case 3:
                if(data[rowIndex].getState() == true) {
                    return "ON";
                } else return "OFF";
            case 4:
                if(data[rowIndex].getIdStop() != 0)
                    return "TAK";
                else return "NIE";
            case 5:
                if(data[rowIndex].getIdVehicle() != 0)
                    return "TAK";
                else return "NIE";
        }
        return null;
    }
    public boolean isInVehicle(int rowIndex) {
        return data[rowIndex].getState();
    }
}
