package com.keyin.aircraft;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.keyin.airport.Airport;
import com.keyin.passenger.Passenger;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Aircraft
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String airlineName;
    private int numberOfPassengers;

    @ManyToMany
    @JoinTable(
            name = "aircraft_passengers",
            joinColumns = @JoinColumn(name = "aircraft_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
    @JsonManagedReference("aircraft-passengers")
    private List<Passenger> passengers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "aircraft_airports",
            joinColumns = @JoinColumn(name = "aircraft_id"),
            inverseJoinColumns = @JoinColumn(name = "airport_id")
    )
    @JsonManagedReference("aircraft-airports")
    private List<Airport> airports = new ArrayList<>();

    public Aircraft()
    {

    }

    public Aircraft(String type, String airlineName, int numberOfPassengers)
    {
        this.type = type;
        this.airlineName = airlineName;
        this.numberOfPassengers = numberOfPassengers;
    }

    public Aircraft(Long id, String type, String airlineName, int numberOfPassengers, List<Passenger> passengers, List<Airport> airports)
    {
        this.id = id;
        this.type = type;
        this.airlineName = airlineName;
        this.numberOfPassengers = numberOfPassengers;
        this.passengers = passengers;
        this.airports = airports;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getAirlineName()
    {
        return airlineName;
    }

    public void setAirlineName(String airlineName)
    {
        this.airlineName = airlineName;
    }

    public int getNumberOfPassengers()
    {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers)
    {
        this.numberOfPassengers = numberOfPassengers;
    }

    public List<Passenger> getPassengers()
    {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers)
    {
        this.passengers = passengers;
    }

    public List<Airport> getAirports()
    {
        return airports;
    }

    public void setAirports(List<Airport> airports)
    {
        this.airports = airports;
    }
}
