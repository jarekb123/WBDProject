package dataModels;

import javax.swing.*;
import java.sql.*;

/**
 * Created by jaroslaw on 15.01.2017.
 */
public class Employee {
    private Integer id;
    private String firstName;
    private String lastName;
    private Date dateOfEmployment;
    private Float salary;
    private String bankAccount;
    private String city;
    private String postcode;
    private String street;
    private Integer buildingNumber;
    private Integer flatNumber;
    private Integer userID;

    public Employee(int id, String firstName, String lastName, Date dateOfEmployment, float salary, String bankAccount, String city, String postcode, String street, int buildingNumber, int flatNumber, int userID) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfEmployment = dateOfEmployment;
        this.salary = salary;
        this.bankAccount = bankAccount;
        this.city = city;
        this.postcode = postcode;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.flatNumber = flatNumber;
        this.userID = userID;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfEmployment() {
        return dateOfEmployment;
    }

    public Float getSalary() {
        return salary;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getCity() {
        return city;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public Integer getFlatNumber() {
        return flatNumber;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getStreet() {
        return street;
    }

    public Employee insertToDB(Connection c) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into pracownicy values (seq_pracownicy.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setDate(3, dateOfEmployment);
            ps.setFloat(4, salary);
            ps.setString(5, bankAccount);
            ps.setString(6, city);
            ps.setString(7, postcode);
            ps.setString(8, street);
            ps.setInt(9, buildingNumber);
            ps.setInt(10, flatNumber);
            ps.setInt(11, 1);
            ps.setInt(12, userID);

            ps.execute();
            c.commit();
            ps.close();

            return this;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }
    public static Employee getEmployee(int id, Connection connection)
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
    public static boolean deleteEmployee(int id, Connection c) {
        try {
            Statement s = c.createStatement();
            Savepoint cancelDelete = c.setSavepoint("svpt_Employee");
            String sql = "delete from uzytkownicy " +
                    "where exists ( select pracownicy.imie from pracownicy where pracownicy.id_uzytkownika = uzytkownicy.id_uzytkownika and pracownicy.id_pracownika = "+id+")";
            s.executeUpdate(sql);
            int dialogResult = JOptionPane.showConfirmDialog(null, "UWAGA!\nUsuwając danego pracownika usuwasz jego konto w systemie i powiązaną z tym kontem karte miejską (wraz z historią doładowań)!", "UWAGA", JOptionPane.YES_NO_OPTION);
            if(dialogResult==JOptionPane.YES_OPTION)
                c.commit();
            else
                c.rollback(cancelDelete);
            s.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean updateEmployee(Connection c, int id, String firstName, String lastName, Date dateOfEmployment, Float salary, String bankAccount, String city, String postcode, String street, Integer buildingNumber, Integer flatNumber, Integer permissionsID) {
        try {
            Savepoint svpt = c.setSavepoint("svptUpdateEmployee");
            PreparedStatement ps = c.prepareStatement("update PRACOWNICY set imie=?, nazwisko=?, data_zatrudnienia=?, wynagrodzenie=?, nr_konta=?, miejscowosc=?, kod_pocztowy=?, ulica=?, nr_budynku=?, nr_lokalu=? where id_pracownika="+id);
            ps.setString(1, firstName);
            ps.setString(2,lastName);
            ps.setDate(3,dateOfEmployment);
            ps.setFloat(4,salary);
            ps.setString(5,bankAccount);
            ps.setString(6,city);
            ps.setString(7,postcode);
            ps.setString(8,street);
            ps.setInt(9,buildingNumber);
            ps.setInt(10,flatNumber);

            PreparedStatement ps2 = c.prepareStatement("update uzytkownicy set id_stanowisko=? " +
                    "where exists (select pracownicy.id_uzytkownika from pracownicy where pracownicy.id_uzytkownika=uzytkownicy.id_uzytkownika and pracownicy.id_pracownika = ?)");
            ps2.setInt(1,permissionsID);
            ps2.setInt(2,id);

            if(ps.executeUpdate() == 1) {
                if(ps2.executeUpdate() == 1) {
                    c.commit();
                    return true;
                }
                else {
                    c.rollback(svpt);
                    return false;
                }
            } else return false;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
