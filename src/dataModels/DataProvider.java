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

        EmployeeSimple[] toReturn = employeeList.toArray(new EmployeeSimple[employeeList.size()]);
        return toReturn;

    }

    public Employee getEmployee(int id)
    {
        try {
            Statement statement = connection.createStatement();

            String sql = "select p.ID_PRACOWNIKA, p.imie, p.nazwisko, p.DATA_ZATRUDNIENIA, p.WYNAGRODZENIE, p.NR_KONTA, " +
                    "p.MIEJSCOWOSC, p.KOD_POCZTOWY, p.ULICA, p.NR_BUDYNKU, p.NR_LOKALU, p.ID_UZYTKOWNIKA" +
                    " from PRACOWNICY p, Stanowiska s, Uzytkownicy u where p.ID_PRACOWNIKA = " + (int)(id) +" and p.ID_UZYTKOWNIKA = u.ID_UZYTKOWNIKA and u.ID_STANOWISKO = s.ID_STANOWISKO";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();

            Employee employee = new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4),
                    resultSet.getFloat(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8),
                    resultSet.getString(9), resultSet.getInt(10), resultSet.getInt(11), resultSet.getInt(12));

            return employee;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    return null;
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

            String [] companyInfo = {resultSet.getString("nazwa"),resultSet.getString("kapital_zakladowy"),
                    resultSet.getString("miejscowosc"),resultSet.getString("kod_pocztowy"),
                    resultSet.getString("ulica"), resultSet.getString("nr_budynku"), resultSet.getString("nr_lokalu")};
            return companyInfo;
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
