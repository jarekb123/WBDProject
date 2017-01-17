package dataModels;

import java.sql.Date;

/**
 * Created by jaroslaw on 15.01.2017.
 */
public class Employee {
    private Integer id;
    private String name;
    private String surname;
    private Date dateOfEmployment;
    private Float salary;
    private String accountNumber;
    private String city;
    private String postcode;
    private String street;
    private Integer parcelNumber;
    private Integer flatNumber;
    private Integer authorityType;

    public Employee(int id, String name, String surname, Date dateOfEmployment, float salary, String accountNumber, String city, String postcode, String street, int parcelNumber, int flatNumber, int authorityType) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfEmployment = dateOfEmployment;
        this.salary = salary;
        this.accountNumber = accountNumber;
        this.city = city;
        this.postcode = postcode;
        this.street = street;
        this.parcelNumber = parcelNumber;
        this.flatNumber = flatNumber;
        this.authorityType = authorityType;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getDateOfEmployment() {
        return dateOfEmployment;
    }

    public Float getSalary() {
        return salary;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCity() {
        return city;
    }

    public Integer getParcelNumber() {
        return parcelNumber;
    }

    public Integer getFlatNumber() {
        return flatNumber;
    }

    public Integer getAuthorityType() {
        return authorityType;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getStreet() {
        return street;
    }
}
