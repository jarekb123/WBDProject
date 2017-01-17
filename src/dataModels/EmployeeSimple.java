package dataModels;

public class EmployeeSimple {
    private int id;
    private String name;
    private String surname;
    private String authorityType;

    public EmployeeSimple(int id, String name, String surname, String authorityType) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.authorityType = authorityType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAuthorityType() {
        return authorityType;
    }
}