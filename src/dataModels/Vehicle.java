package dataModels;

/**
 * Created by jaroslaw on 16.01.2017.
 */
public class Vehicle {
    private Integer id;
    private Integer seatsNumber;
    private Integer standingPlacesNumber;
    private String name;
    private String producer;

    public Vehicle(Integer id, Integer seatsNumber, Integer standingPlacesNumber, String name, String producer) {
        this.id = id;
        this.seatsNumber = seatsNumber;
        this.standingPlacesNumber = standingPlacesNumber;
        this.name = name;
        this.producer = producer;
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

    public String getProducer() {
        return producer;
    }
}
