package dialogs;

import dataModels.Client;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.nimbus.State;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class AddClientDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel mainPanel;
    private JTextField username;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField birthDate;
    private JTextField idCard;
    private JPasswordField password;
    private JComboBox comboBox1;
    private JLabel title;
    private JLabel error;
    private JTextField ticketID;

    Connection c;
    ArrayList<String> errors;
    int id_PKM = 1;

    public AddClientDialog(Connection c) {
        this.c = c;
        this.id_PKM = 1;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

        username.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkUsername();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                checkUsername();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                checkUsername();

            }
        });
        ticketID.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkTicketID();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkTicketID();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkTicketID();
            }
        });
        pack();
    }

    private void checkTicketID() {
        try {
            try {
                if (Client.isTicketRegistered(c, Integer.parseInt(ticketID.getText()))) {
                    error.setText("Ta karta jest już zarejestrowana");
                } else {
                    error.setText("");
                }
            } catch (NumberFormatException nfe)
            {
                error.setText("Niepoprawny format numeru karty miejskiej");
            }
        } catch (SQLException s)
        {
            s.printStackTrace();
        }
    }

    private void checkUsername() {
        try {
            if (Client.isExist(c, username.getText())) {
                error.setText("Użytkownik o podanej nazwie już istnieje");
            }
            else
            {
                error.setText("");
            }
        } catch (SQLException s)
        {
            s.printStackTrace();
        }
    }

    private void onOK() {

        // add your code here
        if(error.getText().isEmpty()) {
            try {
                Client.create(c, 1, username.getText(), String.copyValueOf(password.getPassword()), Integer.parseInt(ticketID.getText()),
                        firstName.getText(), lastName.getText(), Date.valueOf(birthDate.getText()), comboBox1.getSelectedIndex()+1, idCard.getText());
                dispose();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Rejestracja się nie powiodła.", "Błąd rejestracji", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    private String[] getDiscounts() {
        try {
            ArrayList<String> discounts = new ArrayList<>();
        Statement s = c.createStatement();

            ResultSet rs = s.executeQuery("select nazwa, procent_znizki from znizki where id_pkm = "+id_PKM);
            while (rs.next())
            {
                discounts.add(rs.getString(1) + " (" + rs.getInt(2) + "%)");
            }
            return discounts.toArray(new String[discounts.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createUIComponents() {
        comboBox1 = new JComboBox(getDiscounts());
    }
}
