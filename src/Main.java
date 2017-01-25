import dataModels.DataProvider;
import dialogs.AddClientDialog;
import perspectives.DirectorPerspective;
import perspectives.LoginForm;

import javax.swing.*;

/**
 * Created by jaroslaw on 15.01.2017.
 */
public class Main {
    public static void main(String args[])
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        DataProvider dataProvider = new DataProvider();

        JFrame frame = new JFrame("Przedsiębiorstwo KM");
        frame.setContentPane(new LoginForm(dataProvider, frame).mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
//
//        AddClientDialog addClientDialog = new AddClientDialog(dataProvider.getConnection());
//        addClientDialog.pack();
//        addClientDialog.setVisible(true);
    }
}
