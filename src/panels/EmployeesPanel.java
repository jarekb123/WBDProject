package panels;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import dataModels.*;
import dialogs.AddEmployeeDialog;

/**
 * Created by jaroslaw on 15.01.2017.
 */
public class EmployeesPanel {
    private JPanel employeesListPanel;
    private JTable employeesTable;
    private JPanel employeeDetailPanel;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JButton addBtn;
    private JLabel idLbl;
    private JLabel id;
    private JLabel firstNameLbl;
    private JTextField bankAccountTF;
    private JLabel lastNameLbl;
    private JLabel dateOfEmploymentLbl;
    private JLabel salaryLbl;
    private JLabel bankAccountLbl;
    private JTextField salaryTF;
    private JTextField dateOfEmploymentTF;
    private JTextField lastNameTF;
    private JTextField firstNameTF;
    private JLabel addressTitle;
    private JLabel cityLbl;
    private JLabel postcodeLbl;
    private JLabel streetLbl;
    private JLabel buildingNumberLbl;
    private JLabel flatNumberLbl;
    private JTextField cityTF;
    private JTextField postcodeTF;
    private JTextField streetTF;
    private JTextField buildingNumberTF;
    private JTextField flatNumberTF;
    private JLabel permissionsLbl;
    private JComboBox permissionsCBox;

    DataProvider provider;

    Integer selectedEmployeeID;
 //   EmployeeDetailsPanel detailsPanel;


    public EmployeesPanel(DataProvider provider)
    {
        this.provider = provider;
     //   detailsPanel = new EmployeeDetailsPanel(provider);

        loadEmployeeTable(provider);

        ListSelectionModel selectionModel = employeesTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!selectionModel.isSelectionEmpty())
                {
                    int row = selectionModel.getMinSelectionIndex();
                    getEmployeeDetails((int)employeesTable.getValueAt(row, 0));

                }
            }
        });


        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog addDialog = new AddEmployeeDialog(provider.getConnection());
                addDialog.setVisible(true);
                loadEmployeeTable(provider);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee.deleteEmployee(selectedEmployeeID, provider.getConnection());
                loadEmployeeTable(provider);
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee(selectedEmployeeID);
            }
        });
    }

    private void loadEmployeeTable(DataProvider provider) {
        EmployeesTableModel employeesTableModel = new EmployeesTableModel();
        employeesTableModel.getDataFromDataProvider(provider);
        employeesTable.setModel(employeesTableModel);
    }


    public JPanel getEmployeeDetailPanel(int id) {
     //  getEmployee(id);
        return employeeDetailPanel;
    }

    private void getEmployeeDetails(int id)
    {
        Employee e = Employee.getEmployee(id, provider.getConnection());
        selectedEmployeeID = id;
        this.id.setText(Integer.toString(id));
        firstNameTF.setText(e.getFirstName());
        lastNameTF.setText(e.getLastName());
        bankAccountTF.setText(e.getBankAccount());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateOfEmploymentTF.setText(dateFormat.format(e.getDateOfEmployment()));
        salaryTF.setText(e.getSalary().toString());
        cityTF.setText(e.getCity());
        postcodeTF.setText(e.getPostcode());
        streetTF.setText(e.getStreet());
        buildingNumberTF.setText(e.getBuildingNumber().toString());
        flatNumberTF.setText(e.getFlatNumber().toString());

        int permissionID = User.getByID(e.getUserID(), provider.getConnection()).getPermissionsID();
        permissionsCBox.setSelectedIndex(permissionID-1);
    }
    private void updateEmployee(int id)
    {
        try {
            String firstName = firstNameTF.getText();
            String lastName = lastNameTF.getText();
            Date dateOfEmployment = Date.valueOf(dateOfEmploymentTF.getText());
            Float salary = Float.parseFloat(salaryTF.getText());
            String bankAcc = bankAccountTF.getText();
            String city = cityTF.getText();
            String postoode = postcodeTF.getText();
            String street = streetTF.getText();
            Integer buldingNo = Integer.parseInt(buildingNumberTF.getText());
            Integer flatNo = Integer.parseInt(flatNumberTF.getText());
            Integer permissionsID = permissionsCBox.getSelectedIndex() + 1;

            Employee.updateEmployee(provider.getConnection(), id, firstName, lastName, dateOfEmployment, salary, bankAcc, city, postoode, street, buldingNo, flatNo, permissionsID);
            JOptionPane.showMessageDialog(null, "Aktualizacja danych została wykonana poprawnie!", "OK!", JOptionPane.INFORMATION_MESSAGE);
            loadEmployeeTable(provider);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Aktualizacja danych NIE została wykonana poprawnie!", "Błąd!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getMainPanel()
    {
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        permissionsCBox = new JComboBox(provider.getPermissionTypes(provider.getConnection()));
    }
    // Employees section

}
