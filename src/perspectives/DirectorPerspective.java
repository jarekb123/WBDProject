package perspectives;

import dataModels.DataProvider;
import dataModels.User;
import panels.CompanyInfo;
import panels.EmployeesPanel;
import panels.TicketMachinesPanel;
import panels.VehiclesPanel;

import javax.swing.*;

/**
 * Created by jaroslaw on 15.01.2017.
 */
public class DirectorPerspective {
    private JTabbedPane tabbedPane;
    public JPanel mainPanel;

    public DirectorPerspective(DataProvider provider, User user)
    {
        DataProvider provider1 = provider;
        User user1 = user;

        JPanel companyInfoPanel = new CompanyInfo(provider).getMainPanel();
        tabbedPane.addTab("Dane przedsiębiorstwa", companyInfoPanel);

        JPanel employeesPanel = new EmployeesPanel(provider).getMainPanel();
        tabbedPane.addTab("Pracownicy", employeesPanel);

        JPanel ticketMachinesPanel = new TicketMachinesPanel(provider.getConnection()).getMainPanel();
        tabbedPane.addTab("Biletomaty", ticketMachinesPanel);

        JPanel vehiclesPanel = new VehiclesPanel(provider.getConnection(), 1, false).getMainPanel();
        tabbedPane.addTab("Pojazdy", vehiclesPanel);

    }


}



