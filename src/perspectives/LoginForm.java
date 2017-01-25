package perspectives;

import dataModels.Client;
import dataModels.DataProvider;
import dataModels.User;
import dialogs.AddClientDialog;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;

/**
 * Created by jaroslaw on 15.01.2017.
 */
public class LoginForm {
    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton cancelButton;
    private JLabel titleLabel;
    private JButton addButton;
    private JLabel errorLabel;
    private JPanel northPanel;
    public JPanel mainPanel;
    private JPanel southPanel;

    private DataProvider dp;
    private JFrame parent;

    public LoginForm(DataProvider dp, JFrame parent)
    {
        this.dp = dp;
        this.parent = parent;

        loginButton.addActionListener(e -> login());
        addButton.addActionListener(e -> addNewClient());
    }
    private void login() {
        try {
            User user = User.login(dp.getConnection(), usernameField.getText(), String.copyValueOf(passwordField1.getPassword()));
            if(user != null) {
                String permissionName = user.getPermissionName(dp.getConnection());
                if(permissionName.equals("Dyrektor")) {
                    parent.setContentPane(new DirectorPerspective(dp, user).mainPanel);
                    parent.pack();
                }
                else if(permissionName.equals("Klient")) {
                    parent.setContentPane(new ClientPerspective(Client.get(dp.getConnection(), user), parent).getMainPanel());
                    parent.pack();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void addNewClient() {
        AddClientDialog addClientDialog = new AddClientDialog(dp.getConnection());
        addClientDialog.setVisible(true);
    }
}
