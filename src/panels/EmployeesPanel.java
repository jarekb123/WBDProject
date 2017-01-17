package panels;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import dataModels.*;
import perspectives.AddEmployeeDialog;

/**
 * Created by jaroslaw on 15.01.2017.
 */
public class EmployeesPanel {
    private JPanel employeesListPanel;
    private JTable employeesTable;
    private JPanel employeeDetailPanel;
    private JTextField nameField;
    private JTextField accountField;
    private JTextField salaryField;
    private JTextField surnameField;
    private JTextField dateField;
    private JComboBox permissionsBox;
    private JTextField employeeCityField;
    private JTextField employeeFlatField;
    private JTextField employeePostcodeField;
    private JTextField employeeParcelField;
    private JTextField employeeStreetField;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JButton addBtn;
    private JPanel employeesDetailPanel;
    private JLabel idHeader;

    DataProvider provider;
 //   EmployeeDetailsPanel detailsPanel;


    public EmployeesPanel(DataProvider provider)
    {
        this.provider = provider;
     //   detailsPanel = new EmployeeDetailsPanel(provider);

        EmployeesTableModel employeesTableModel = new EmployeesTableModel();
        employeesTableModel.getDataFromDataProvider(provider);
        employeesTable.setModel(employeesTableModel);

        ListSelectionModel selectionModel = employeesTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!selectionModel.isSelectionEmpty())
                {
                    int row = selectionModel.getMinSelectionIndex();
             //       getEmployee(row);

                }
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog addDialog = new AddEmployeeDialog(provider);
                addDialog.setVisible(true);
            }
        });
    }


    public JPanel getEmployeeDetailPanel(int id) {
     //  getEmployee(id);
        return employeeDetailPanel;
    }

    public JPanel getMainPanel()
    {
        return mainPanel;
    }
    // Employees section

}
