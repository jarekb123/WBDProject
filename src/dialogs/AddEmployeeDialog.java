package dialogs;

import dataModels.DataProvider;
import dataModels.Employee;
import dataModels.User;
import panels.EmployeesPanel;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEmployeeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel mainPanel;
    private JPanel employeeDetailPanel;
    private JLabel addressTitle;
    private JLabel cityLbl;
    private JLabel flatNumberLbl;
    private JLabel postcodeLbl;
    private JLabel streetLbl;
    private JLabel buildingNumberLbl;
    private JTextField cityTF;
    private JTextField flatNumberTF;
    private JTextField buildingNumberTF;
    private JTextField streetTF;
    private JTextField postcodeTF;
    private JLabel firstNameLbl;
    private JLabel bankAccountLbl;
    private JLabel salaryLbl;
    private JLabel dateOfEmploymentLbl;
    private JLabel lastNameLbl;
    private JTextField bankAccountTF;
    private JTextField dateOfEmploymentTF;
    private JTextField lastNameTF;
    private JTextField firstNameTF;
    private JTextField salaryTF;
    private JLabel personalDataLbl;
    private JLabel usernameLbl;
    private JLabel permissionsLbl;
    private JLabel usernameData;
    private JLabel permissionsData;
    private JLabel userDataLbl;

    private  int userID;
    private Connection connection;

    public AddEmployeeDialog(Connection c) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Dodaj nowego pracownika");
        pack();

        this.connection = c;

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

      //  JOptionPane.showMessageDialog(null, "Aby dodać pracownika musisz najpierw dodać jego konto użytkownika", "UWAGA!!!", JOptionPane.INFORMATION_MESSAGE);

            AddUserDialog addUserDialog = new AddUserDialog(c);
            User user = addUserDialog.showDialog();
            userID = user.getId();
            usernameData.setText(user.getUsername());
            permissionsData.setText(DataProvider.getPermissionType(user.getPermissionsID(), c));


    }

    private void onOK() {
        // add your code here
         Date date = null;
        try {
            Employee employee = new Employee(0, firstNameTF.getText(), lastNameTF.getText(), java.sql.Date.valueOf(dateOfEmploymentTF.getText()), Float.parseFloat(salaryTF.getText()), bankAccountTF.getText(), cityTF.getText(), postcodeTF.getText(),
                    streetTF.getText(), Integer.parseInt(buildingNumberTF.getText()), Integer.parseInt(flatNumberTF.getText()), userID);
            employee.insertToDB(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
