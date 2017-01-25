package perspectives;

import dataModels.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jaroslaw on 21.01.2017.
 */
public class ClientPerspective {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel birthDateLabel;
    private JLabel discountLabel;
    private JLabel idCardLabel;
    private JLabel firstName;
    private JLabel lastName;
    private JLabel birthDate;
    private JLabel discount;
    private JLabel idCard;
    private JLabel ticketID;
    private JButton logoutButton;

    JFrame parent;

    public ClientPerspective(Client client, JFrame parent)
    {
        this.parent = parent;

        firstName.setText(client.getFirstName());
        lastName.setText(client.getLastName());
        birthDate.setText(client.getBirthDate().toString());
        discount.setText(client.getDiscountName() + "("+client.getDiscountValue()+")");
        idCard.setText(client.getIdCard());
        ticketID.setText(client.getId_ticket().toString());
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.dispose();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
