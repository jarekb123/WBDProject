package panels;

import dataModels.Vehicle;
import dataModels.VehicleTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.Connection;

/**
 * Created by jaroslaw on 15.01.2017.
 */
public class VehiclesPanel {
    private JPanel mainPanel;
    private JTable table1;
    private JPanel vehicleDetailPanel;
    private JPanel vehicleDetailLeftPanel;
    private JLabel registrationNumberLabel;
    private JLabel serialLabel;
    private JLabel yearLabel;
    private JLabel vehicleNumberLabel;
    private JPanel vehicleDetailRightPanel;
    private JTextField registrationNumberField;
    private JTextField vehicleNumberField;
    private JTextField serialNumberField;
    private JTextField yearField;
    private JPanel vehicleDetailLeftPanel2;
    private JLabel producerLabel;
    private JLabel nameLabel;
    private JLabel typeLabel;
    private JLabel seatsLabel;
    private JLabel standingPlacesLabel;
    private JPanel vehicleDetailRightPanel2;
    private JLabel producerData;
    private JLabel standingPlacesData;
    private JLabel nameData;
    private JLabel seatsData;
    private JLabel vehicleTypeData;

    private Connection c;
    private int idPKN;

    public VehiclesPanel(Connection c, int idPKM, boolean editable) {
        this.c = c;
        this.idPKN = idPKM;

        if(!editable)
            disableEdit();

        VehicleTable vehicleTableModel = new VehicleTable();
        vehicleTableModel.getDataFromDB(c, idPKN);
        table1.setModel(vehicleTableModel);

        ListSelectionModel selectionModel = table1.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!selectionModel.isSelectionEmpty())
                {
                    int row = selectionModel.getMinSelectionIndex();
                    showDetails(vehicleTableModel.getRow(row));

                }
            }
        });

    }

    private void disableEdit()
    {
        registrationNumberField.setEditable(false);
        serialNumberField.setEditable(false);
        yearField.setEditable(false);
        vehicleNumberField.setEditable(false);
    }

    private void showDetails(Vehicle v)
    {
        // vehicle
         registrationNumberField.setText(v.getRegistrationNumber());
         serialNumberField.setText(v.getSerialNumber());
         yearField.setText(v.getProductionYear().toString());
         vehicleNumberField.setText(v.getVehicleNumber());

         // model details
        producerData.setText(v.getModel().getManufacturer());
        nameData.setText(v.getModel().getName());
        vehicleTypeData.setText(v.getModel().getType());
        seatsData.setText(v.getModel().getSeatsNumber().toString());
        standingPlacesData.setText(v.getModel().getStandingPlacesNumber().toString());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }}
