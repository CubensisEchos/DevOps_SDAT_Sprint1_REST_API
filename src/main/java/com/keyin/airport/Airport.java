package com.keyin.airport;

import com.keyin.aircraft.Aircraft;
import com.keyin.city.City;
import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;

@Entity
public class Airport
{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String airportCode;

    @ManyToOne
    @JoinColumn(name = "city")
    private City city;

    @ManyToMany
    private List<Aircraft> aircrafts = new ArrayList<>();

    public Airport()
    {

    }

    public Airport(String name, String airportCode, City city)
    {
        this.name = name;
        this.airportCode = airportCode;
        this.city = city;
    }

    public Airport(Long id, String name, String airportCode, City city) {
        this.id = id;
        this.name = name;
        this.airportCode = airportCode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void setAircrafts(List<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }
}
