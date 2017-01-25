package dataModels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by jaroslaw on 16.01.2017.
 */
public class Model {
    private Integer id;
    private Integer seatsNumber;
    private Integer standingPlacesNumber;
    private String name;
    private String manufacturer;
    private String type;

    public Model(Integer id, Integer seatsNumber, Integer standingPlacesNumber, String name, String manufacturer, String type) {
        this.id = id;
        this.seatsNumber = seatsNumber;
        this.standingPlacesNumber = standingPlacesNumber;
        this.name = name;
        this.manufacturer = manufacturer;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSeatsNumber() {
        return seatsNumber;
    }

    public Integer getStandingPlacesNumber() {
        return standingPlacesNumber;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getType() {
        return type;
    }

    public static Model get(Connection c, int id) throws SQLException {
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from modele_pojazdu where id_modelu_pojazdu = "+id);
        rs.next();
        return parseResultSet(rs);
    }

    public static Model parseResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("Id_modelu_pojazdu");
        int seatsNo = rs.getInt("ilosc_miejsc_siedzacych");
        int standingsNo = rs.getInt("ilosc_miejsc_stojacych");
        String manufacturer = rs.getString("producent");
        String type = rs.getString("typ_pojazdu");
        String name = rs.getString("nazwa");

        return new Model(id, seatsNo, standingsNo, name, manufacturer, type);
    }

    public static Model[] getAll(Connection c) throws SQLException {
        ArrayList<Model> models = new ArrayList<>();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from modele_pojazdu");
        while (rs.next())
            models.add(parseResultSet(rs));

        return models.toArray(new Model[models.size()]);
    }
}
