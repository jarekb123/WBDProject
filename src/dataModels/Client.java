package dataModels;

import java.lang.reflect.*;
import java.sql.*;
import java.util.Arrays;

/**
 * Created by jaroslaw on 21.01.2017.
 */
public class Client {
    Integer id;
    Integer id_ticket;
    String firstName, lastName;
    Date birthDate;
    String discountName;
    Integer discountValue;
    String idCard;

    public Client(Integer id, Integer id_ticket, String firstName, String lastName, Date birthDate, String discountName, Integer discountValue, String idCard) {
        this.id = id;
        this.id_ticket = id_ticket;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.discountName = discountName;
        this.discountValue = discountValue;
        this.idCard = idCard;
    }

    public Integer getId() {
        return id;
    }

    public Integer getId_ticket() {
        return id_ticket;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getDiscountName() {
        return discountName;
    }

    public Integer getDiscountValue() {
        return discountValue;
    }

    public String getIdCard() {
        return idCard;
    }

    public static Client get(Connection c, User user) throws SQLException {
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select k.id_karta_miejska, u.id_uzytkownika, k.imie, k.nazwisko, k.data_urodzenia, k.nr_dokumentu, z.nazwa, z.procent_znizki from uzytkownicy u, karty_miejskie k, Znizki z " +
                "where k.id_uzytkownika = u.id_uzytkownika and k.id_znizka=z.id_znizka and u.nazwa_uzytkownika = '" + user.getUsername() + "'");
        if (rs.next()) {
            Integer id = rs.getInt(1);
            Integer id_ticket = rs.getInt(2);
            String firstName = rs.getString(3);
            String lastName = rs.getString(4);
            Date birthDate = rs.getDate(5);
            String idCard = rs.getString(6);
            String discountName = rs.getString(7);
            Integer discountVal = rs.getInt(8);

            return new Client(id, id_ticket, firstName, lastName, birthDate, discountName, discountVal, idCard);
        }
        return null;
    }
    public static boolean isExist(Connection c, String username) throws SQLException {
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select count(*) from uzytkownicy where nazwa_uzytkownika = '"+username+"'");
        rs.next();
        int count = rs.getInt(1);
        if(count > 0)
            return true;
        return false;
    }
    public static boolean isTicketRegistered(Connection c, int id_ticket) throws SQLException {
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select count(*) from karty_miejskie where id_karta_miejska = "+id_ticket);
        rs.next();
        int count = rs.getInt(1);
        if(count > 0)
            return true;
        return false;
    }
    public static boolean create(Connection c, int idPKM, String username, String password, Integer id_ticket, String firstName, String lastName, Date birthDate, int discountID, String idCard) throws SQLException {
        Savepoint svpt = c.setSavepoint("createUserSVPT");

        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select seq_uzytkownicy.nextval from dual");
        rs.next();
        int id = rs.getInt(1);
        s.close();

        PreparedStatement ps1 = c.prepareStatement("insert into uzytkownicy values (?, ?, ?, ?, ?)");
        ps1.setInt(1, id);
        ps1.setString(2, username);
        ps1.setString(3,password);
        ps1.setInt(4,idPKM);
        ps1.setInt(5, getPermissionID(c));
        if(ps1.executeUpdate() > 0) {
            PreparedStatement ps2 = c.prepareStatement("insert into karty_miejskie values (?, ?, ?, ?, ?, ?, ?, ?)");
            ps2.setInt(1, id_ticket);
            ps2.setString(2, firstName);
            ps2.setString(3,lastName);
            ps2.setDate(4, birthDate);
            ps2.setInt(5,discountID);
            ps2.setString(6, idCard);
            ps2.setInt(7, idPKM);
            ps2.setInt(8, id);

            if(ps2.executeUpdate() < 0) {
                c.rollback(svpt);
                return false;
            }
        }
        else {
            c.rollback(svpt);
            return false;
        }
        c.commit();

        return true;
    }
    private static int getPermissionID(Connection c) throws SQLException {
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select id_stanowisko from stanowiska where nazwa = 'Klient'");
        rs.next();
        return rs.getInt(1);
    }
}
