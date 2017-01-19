package dataModels;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

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

    public Employee insertToDB(Connection c) throws SQLException {
            PreparedStatement ps = c.prepareStatement("insert into pracownicy values (seq_pracownicy.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setDate(3, dateOfEmployment);
            ps.setFloat(4, salary);
            ps.setString(5, bankAccount);
            ps.setString(6,city);
            ps.setString(7, postcode);
            ps.setString(8, street);
            ps.setInt(9, buildingNumber);
            ps.setInt(10,flatNumber);
            ps.setInt(11,1);
            ps.setInt(12, userID);

            ps.execute();
            ps.close();

            return this;
    }
}
