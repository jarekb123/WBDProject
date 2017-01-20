package panels;

import dataModels.BusStop;
import dataModels.TicketMachine;
import dataModels.TicketMachinesTableModel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by jaroslaw on 19.01.2017.
 */
public class TicketMachinesPanel {
    private JPanel mainPanel;
    private JTable tableList;
    private JPanel bottomPanel;
    private JLabel ticketMacineDetails;
    private JLabel vehicleORstopLbl;
    private JLabel idLbl;
    private JLabel idTM;
    private JLabel manufacturerLbl;
    private JTextField manufacturerTF;
    private JLabel serialNoLbl;
    private JLabel IDLabel;
    private JLabel a1Label;
    private JLabel a2Label;
    private JLabel a3Label;
    private JLabel a4Label;
    private JLabel a5Label;
    private JLabel id2;
    private JLabel b1Label;
    private JLabel b2Label;
    private JLabel b3Label;
    private JLabel b4Label;
    private JLabel b5Label;
    private JTextField serialNoTF;
    private JRadioButton stateON;
    private JRadioButton stateOFF;
    private JLabel stateLbl;

    boolean tmState;

    private ButtonGroup stateButtonGroup;

    Connection c;

    public TicketMachinesPanel(Connection c) {
        this.c = c;
        TicketMachinesTableModel model = new TicketMachinesTableModel();
        model.getDataFromDB(c);
        tableList.setModel(model);

        tmState = false;

        stateButtonGroup = new ButtonGroup();
        stateButtonGroup.add(stateON);
        stateButtonGroup.add(stateOFF);

        stateOFF.addActionListener(e -> tmState=false);
        stateON.addActionListener(e -> tmState=true);

        ListSelectionModel selectionModel = tableList.getSelectionModel();
        selectionModel.addListSelectionListener(e -> getDetails(selectionModel.getMinSelectionIndex() + 1));
    }

    private void getDetails(int id) {
        try {
            TicketMachine tm = TicketMachine.get(c, id);
            idTM.setText(tm.getId().toString());
            manufacturerTF.setText(tm.getManufacturer());
            serialNoTF.setText(tm.getSerialNo());
            if(tm.getState())
                stateON.setSelected(true);
            else stateOFF.setSelected(true);

            if(tm.getIdBusStop() != 0)
                getBusStopDetails(tm.getIdBusStop());

        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    private void getBusStopDetails(int id) {
        try {
            // GUI setup
            a1Label.setText("Nazwa:");
            a2Label.setText("Miejscowość: ");
            a3Label.setText("Kod pocztowy: ");
            a4Label.setText("Ulica: ");
            a5Label.setText("Nr budynku: ");

            BusStop busStop = BusStop.get(c, id);
            id2.setText(busStop.getId().toString());
            b1Label.setText(busStop.getName());
            b2Label.setText(busStop.getCity());
            b3Label.setText(busStop.getPostcode());
            b4Label.setText(busStop.getStreet());
            b5Label.setText(busStop.getBuildingNumber().toString());
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public JPanel getMainPanel() { return mainPanel;}

}
