package panels;

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
    private JLabel idLabel;
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
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public JPanel getMainPanel() { return mainPanel;}

}
