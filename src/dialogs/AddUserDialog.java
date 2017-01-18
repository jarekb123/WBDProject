package dialogs;

import dataModels.DataProvider;

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

    String username;
    int pkmID = 1;
    DataProvider dataProvider;

    public AddUserDialog(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
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
        try {
            Connection c = dataProvider.getConnection();
            String password = passwordTF.getPassword().toString();
            username = usernameTF.getText();
            int permissionsID = permissionCBox.getSelectedIndex();
            PreparedStatement statement = c.prepareStatement("INSERT INTO uzytkownicy (seq_uzytkownicy.nextval, '?', '?', ?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3,pkmID);
            statement.setInt(4, permissionsID);
            statement.execute();
            dispose();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    public String showDialog()
    {
        setVisible(true);
        return username;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        permissionCBox = new JComboBox(dataProvider.getPermissionTypes());
    }
}
