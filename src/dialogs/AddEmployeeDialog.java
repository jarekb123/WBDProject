package dialogs;

import dataModels.DataProvider;
import panels.EmployeesPanel;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AddEmployeeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel mainPanel;
    private JPanel employeeDetailPanel;
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

    public AddEmployeeDialog(DataProvider provider) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Dodaj nowego pracownika");
        pack();

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

        try {
            AddUserDialog addUserDialog = new AddUserDialog(provider);
            provider.getConnection().setAutoCommit(false);
            String userID = addUserDialog.showDialog();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
