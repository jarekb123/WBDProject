package panels;

import javax.swing.*;
import dataModels.DataProvider;

/**
 * Created by jaroslaw on 15.01.2017.
 */
public class CompanyInfo {
    private JPanel leftPanel;
    private JLabel companyNameLabel;
    private JLabel shareCapitalLabel;
    private JLabel cityLabel;
    private JLabel postcodeLabel;
    private JLabel streetLabel;
    private JLabel parcelNumberLabel;
    private JLabel flatNumberLabel;
    private JPanel rightPanel;
    private JLabel companyNameData;
    private JLabel shareCapitalData;
    private JLabel cityData;
    private JLabel postCodeData;
    private JLabel streetData;
    private JLabel parcelNumberData;
    private JLabel flatNumberData;
    private JPanel mainPanel;

    private DataProvider provider;

    public CompanyInfo(DataProvider provider)
    {
        this.provider = provider;
        getCompanyInfo();
    }
    public JPanel getMainPanel()
    {
        return mainPanel;
    }
    // Company section
    private void getCompanyInfo()
    {
        String [] conpanyInfo = provider.getCompanyInformation(1);
        companyNameData.setText(conpanyInfo[0]);
        shareCapitalData.setText(conpanyInfo[1]);
        cityData.setText(conpanyInfo[2]);
        postCodeData.setText(conpanyInfo[3]);
        streetData.setText(conpanyInfo[4]);
        parcelNumberData.setText(conpanyInfo[5]);
        flatNumberData.setText(conpanyInfo[6]);
    }
}
