package dataModels;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by jaroslaw on 15.01.2017.
 */
public class DataProvider {
    private Connection connection;
    public DataProvider()
    {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "WBD", "wbd123");
            connection.setAutoCommit(false);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public EmployeeSimple[] getEmployeesList()
    {
        ArrayList<EmployeeSimple> employeeList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select p.id_pracownika, p.imie, p.nazwisko, s.nazwa from PRACOWNICY p, UZYTKOWNICY u, STANOWISKA s " +
                    "where p.id_uzytkownika = u.ID_UZYTKOWNIKA and s.ID_STANOWISKO = u.ID_STANOWISKO  order by p.id_pracownika");

            while(resultSet.next())
            {
                EmployeeSimple employeeSimple = new EmployeeSimple(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4));
                employeeList.add(employeeSimple);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeList.toArray(new EmployeeSimple[employeeList.size()]);

    }


    public static String[] getPermissionTypes(Connection c) {
        try {
            Statement statement = c.createStatement();

            String sql = "select nazwa from Stanowiska";
            ArrayList<String> permissionList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
                permissionList.add(resultSet.getString(1));

            return permissionList.toArray(new String[permissionList.size()]);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static String getPermissionType(int id, Connection c)
    {
        try {
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("select nazwa from stanowiska where id_stanowisko = "+id);
            rs.next();
            return rs.getString(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public  String [] getCompanyInformation(int id)
    {
        try {
            Statement statement = connection.createStatement();

            String sql = "select * FROM przedsiebiorstwo_km where id_pkm = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();

            return new String[]{resultSet.getString("nazwa"),resultSet.getString("kapital_zakladowy"),
                    resultSet.getString("miejscowosc"),resultSet.getString("kod_pocztowy"),
                    resultSet.getString("ulica"), resultSet.getString("nr_budynku"), resultSet.getString("nr_lokalu")};
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnection() {
        return connection;
    }
}
