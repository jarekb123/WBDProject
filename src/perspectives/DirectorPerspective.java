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
    private JPanel companyInfoPanel;
    private JPanel employeesPanel;
    private JPanel ticketMachinesPanel;
    private JPanel vehiclesPanel;

    private DataProvider provider;
    private User user;
    public DirectorPerspective(DataProvider provider, User user)
    {
        this.provider = provider;
        this.user = user;

        companyInfoPanel = new CompanyInfo(provider).getMainPanel();
        tabbedPane.addTab("Dane przedsiębiorstwa",companyInfoPanel);

        employeesPanel = new EmployeesPanel(provider).getMainPanel();
        tabbedPane.addTab("Pracownicy", employeesPanel);

        ticketMachinesPanel = new TicketMachinesPanel(provider.getConnection()).getMainPanel();
        tabbedPane.addTab("Biletomaty", ticketMachinesPanel);

        vehiclesPanel = new VehiclesPanel(provider.getConnection(), 1, false).getMainPanel();
        tabbedPane.addTab("Pojazdy", vehiclesPanel);

    }


}



