package dataModels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by jaroslaw on 19.01.2017.
 */
public class TicketMachine {
    private Integer id;
    private String manufacturer, serialNo;
    private boolean state;
    private Integer idVehicle;
    private Integer idStop;

    public TicketMachine(Integer id, String manufacturer, String serialNo, boolean state, Integer idVehicle, Integer idStop) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.serialNo = serialNo;
        this.state = state;
        this.idVehicle = idVehicle;
        this.idStop = idStop;
    }

    public Integer getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public boolean getState() {
        return state;
    }

    public Integer getIdVehicle() {
        return idVehicle;
    }

    public Integer getIdStop() {
        return idStop;
    }

    public static TicketMachine[] getAllFromDB(Connection c, Integer idPKM) throws SQLException {
        ArrayList<TicketMachine> list = new ArrayList<>();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from Biletomaty where id_pkm = "+idPKM);

        while(rs.next()) {
            list.add(parseResultSet(rs));
        }

        return list.toArray(new TicketMachine[list.size()]);

    }
    private static TicketMachine parseResultSet(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id_biletomat");
        String manufacturer = rs.getString("producent");
        String serialNo = rs.getString("numer_seryjny");
        boolean state = rs.getBoolean("stan_urzadzenia");
        Integer idVehicle = rs.getInt("id_pojazd");
        Integer idStop = rs.getInt("id_przystanek");

        return new TicketMachine(id, manufacturer, serialNo, state, idVehicle, idStop);
    }

    public static TicketMachine get(Connection c, Integer id) throws SQLException {
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from Biletomaty where id_biletomat = "+id);
        rs.next();
        return parseResultSet(rs);
    }
}
