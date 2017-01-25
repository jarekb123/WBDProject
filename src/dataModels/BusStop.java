package dataModels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jaroslaw on 20.01.2017.
 */
public class BusStop {
    private Integer id;
    private String name;
    private String city;
    private String postcode;
    private String street;
    private Integer buildingNumber;

    private BusStop(Integer id, String name, String city, String postcode, String street, Integer buildingNumber) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.postcode = postcode;
        this.street = street;
        this.buildingNumber = buildingNumber;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getStreet() {
        return street;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public static BusStop get(Connection c, int id) throws SQLException {
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from Przystanki where id_przystanek = "+id);
        rs.next();
        return parseResultSet(rs);
    }

    private static BusStop parseResultSet(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id_przystanek");
        String name = rs.getString("nazwa");
        String city = rs.getString("miejscowosc");
        String postocde = rs.getString("kod_pocztowy");
        String street = rs.getString("ulica");
        Integer buildingNo = rs.getInt("nr_budynku");

        return new BusStop(id,name,city,postocde,street,buildingNo);
    }
}
