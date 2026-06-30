package com.keyin.city;

import com.keyin.airport.Airport;
import com.keyin.passenger.Passenger;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String state;
    private int population;

    @OneToMany(mappedBy = "city")
    private List<Airport> airports = new ArrayList<>();

    @OneToMany(mappedBy = "city")
    private List<Passenger> passengers = new ArrayList<>();

    public City()
    {

    }

    public City(String name, String state, int population)
    {
        this.name = name;
        this.state = state;
        this.population = population;
    }

    public City(Long id, String name, String state, int population, List<Airport> airports, List<Passenger> passengers) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.population = population;
        this.airports = airports;
        this.passengers = passengers;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation(int population)
    {
        this.population = population;
    }

    public List<Airport> getAirports()
    {
        return airports;
    }

    public void setAirports(List<Airport> airports)
    {
        this.airports = airports;
    }

    public List<Passenger> getPassengers()
    {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers)
    {
        this.passengers = passengers;
    }
}
