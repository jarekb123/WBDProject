package dataModels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by jaroslaw on 21.01.2017.
 */
public class Vehicle {
    private Integer id;
    private String registrationNumber;
    private Model model;
    private String serialNumber;
    private Integer productionYear;
    private String vehicleNumber;

    public Vehicle(Integer id, String registrationNumber, Model model, String serialNumber, Integer productionYear, String vehicleNumber) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.serialNumber = serialNumber;
        this.productionYear = productionYear;
        this.vehicleNumber = vehicleNumber;
    }

    public Integer getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Model getModel() {
        return model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public static Vehicle get(Connection c, int id) throws SQLException {
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select p.id_pojazd, p.numer_rejestracyjny, p.numer_seryjny, p.rok_produkcji, p.nr_boczny, " +
                "m.* from Pojazdy p, Modele_pojazdu m where p.id_modelu_pojazdu = m.id_modelu_pojazdu and p.id_pojazd = "+id);
        rs.next();
        return parseResultSet(rs);
    }
    private static Vehicle parseResultSet(ResultSet rs) throws SQLException {
        Integer id = rs.getInt(1);
        String registrationNumber = rs.getString(2);
        String serialNo = rs.getString(3);
        Integer productionYear = rs.getInt(4);
        String vehicleNo = rs.getString(5);
        Model model = Model.parseResultSet(rs);

        return new Vehicle(id,registrationNumber,model,serialNo,productionYear,vehicleNo);
    }
    public static Vehicle[] getAll(Connection c, int idPKM) throws SQLException {

        ArrayList<Vehicle> vehicles = new ArrayList<>();

        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select p.id_pojazd, p.numer_rejestracyjny, p.numer_seryjny, p.rok_produkcji, p.nr_boczny, " +
                "m.* from Pojazdy p, Modele_pojazdu m where p.id_modelu_pojazdu = m.id_modelu_pojazdu and p.id_PKM = "+idPKM+" order by p.id_pojazd");
        while(rs.next()) {
            vehicles.add(parseResultSet(rs));
        }
        return vehicles.toArray(new Vehicle[vehicles.size()]);
    }
}
