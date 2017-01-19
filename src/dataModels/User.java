package dataModels;

import javax.jws.soap.SOAPBinding;
import java.sql.*;

/**
 * Created by jaroslaw on 18.01.2017.
 */
public class User {
    int id;
    String username, password;
    int permissionsID, pkmID;

    public User(int id, String username, String password, int permissionsID, int pkmID) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.permissionsID = permissionsID;
        this.pkmID = pkmID;
    }

    public User(String username, String password, int permissionsID, int pkmID) {
        this.username = username;
        this.password = password;
        this.permissionsID = permissionsID;
        this.pkmID = pkmID;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public int getPermissionsID() {
        return permissionsID;
    }

    /**
     * Tworzy nowego uzytkownika i dodaje go do bazy danych
     * @param c Polaczenie z baza danych
     * @return Zwraca instancje nowego uzytkownika
     */
    public User insertToDB(Connection c) throws SQLException {

            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select seq_uzytkownicy.nextval from dual");
            rs.next();
            int id = rs.getInt(1);
            s.close();

            PreparedStatement ps = c.prepareStatement("insert into uzytkownicy values(?, ?, ?, ?, ?)");

            ps.setInt(1, id);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setInt(4, pkmID);
            ps.setInt(5, permissionsID);

            ps.execute();
            ps.close();

            this.id = id;
            return this;
    }

    /**
     * Tworzy instancje uzytkownika pobierajac jego dane z bazy danych
     * @param id ID uzytkownika w bazie
     * @param c Połączenie z bazą danych
     * @return Instancje uzytkownika o podanym id
     */
    public static User getByID(int id, Connection c)
    {
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from uzytkownicy where id_uzytkownika = "+id);
            rs.next();
            String username = rs.getString("nazwa_uzytkownika");
            String password = rs.getString("haslo");
            int pkmID = rs.getInt("id_pkm");
            int permissionsID = rs.getInt("id_stanowisko");

            return new User(id, username, password, permissionsID, pkmID);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
