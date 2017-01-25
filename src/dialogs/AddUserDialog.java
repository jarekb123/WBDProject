package dialogs;

import dataModels.DataProvider;
import dataModels.User;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField usernameTF;
    private JPasswordField passwordTF;
    private JLabel usernameLbl;
    private JLabel passwordLbl;
    private JComboBox permissionCBox;
    private JLabel permissionsLbl;

    User user;
    Connection dbConnection;

    public AddUserDialog(Connection c) {
        this.dbConnection = c;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Dodaj nowego użytkownika");
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
    }

    private void onOK() {
        // add your code here
            String password = String.copyValueOf(passwordTF.getPassword());
            String username = usernameTF.getText();
            int permissionsID = permissionCBox.getSelectedIndex()+1;
            int pkmID = 1;
            try {
                this.user = User.insertToDB(dbConnection, username, password, pkmID, permissionsID);
            } catch (SQLException e) {
                try {
                    if(dbConnection!=null)
                    {
                        dbConnection.rollback();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }


            dispose();

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    public User showDialog()
    {
        setVisible(true);
        return user;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        permissionCBox = new JComboBox(DataProvider.getPermissionTypes(dbConnection));
    }
}
