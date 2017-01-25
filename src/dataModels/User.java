package dataModels;

import javax.jws.soap.SOAPBinding;
import java.sql.*;

/**
 * Created by jaroslaw on 18.01.2017.
 */
public class User {
    private int id;
    private String username;
    private int permissionsID;
    private int pkmID;

    private User(int id, String username, int permissionsID, int pkmID) {
        this.username = username;
        this.id = id;
        this.permissionsID = permissionsID;
        this.pkmID = pkmID;
    }

    public User(String username, int permissionsID, int pkmID) {
        this.username = username;
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
    public static User insertToDB(Connection c, String username, String password, int pkmID, int permissionsID) throws SQLException {

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

            ps.executeUpdate();
            ps.close();
            return new User(id, username, permissionsID, pkmID);
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
          //  String password = rs.getString("haslo");
            int pkmID = rs.getInt("id_pkm");
            int permissionsID = rs.getInt("id_stanowisko");

            return new User(id, username, permissionsID, pkmID);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static User login(Connection c, String username, String password) throws SQLException {
        PreparedStatement ps = c.prepareStatement("select id_uzytkownika, nazwa_uzytkownika, id_stanowisko, id_PKM from uzytkownicy WHERE nazwa_uzytkownika = ? and haslo = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return new User(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
        return null;
    }
    public String getPermissionName(Connection c) throws SQLException {
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select s.nazwa from Stanowiska s, Uzytkownicy u where u.id_stanowisko = s.id_stanowisko and u.id_uzytkownika = "+id);
        rs.next();
        return rs.getString(1);
    }
}
