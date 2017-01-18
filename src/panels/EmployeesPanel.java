package panels;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                    getEmployeeDetails(row+1);

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

    private void getEmployeeDetails(int id)
    {
        Employee e = provider.getEmployee(id);
        selectedEmployeeID = id;
        this.id.setText(Integer.toString(id));
        firstNameTF.setText(e.getName());
        lastNameTF.setText(e.getSurname());
        bankAccountTF.setText(e.getAccountNumber());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateOfEmploymentTF.setText(dateFormat.format(e.getDateOfEmployment()));
        salaryTF.setText(e.getSalary().toString());
        cityTF.setText(e.getCity());
        postcodeTF.setText(e.getPostcode());
        streetTF.setText(e.getStreet());
        buildingNumberTF.setText(e.getParcelNumber().toString());
        flatNumberTF.setText(e.getFlatNumber().toString());

        permissionsCBox.setSelectedIndex(e.getAuthorityType());
    }

    public JPanel getMainPanel()
    {
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        permissionsCBox = new JComboBox(provider.getPermissionTypes());
    }
    // Employees section

}
