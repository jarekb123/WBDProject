package perspectives;

import dataModels.DataProvider;
import panels.CompanyInfo;
import panels.EmployeesPanel;
import panels.TicketMachinesPanel;

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

    private DataProvider provider;

    public DirectorPerspective(DataProvider provider)
    {
        this.provider = provider;

        companyInfoPanel = new CompanyInfo(provider).getMainPanel();
        tabbedPane.addTab("Dane przedsiębiorstwa",companyInfoPanel);

        employeesPanel = new EmployeesPanel(provider).getMainPanel();
        tabbedPane.addTab("Pracownicy", employeesPanel);

        ticketMachinesPanel = new TicketMachinesPanel(provider.getConnection()).getMainPanel();
        tabbedPane.addTab("Biletomaty", ticketMachinesPanel);
    }


}



