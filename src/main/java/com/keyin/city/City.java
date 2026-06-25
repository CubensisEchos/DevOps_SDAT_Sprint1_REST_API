package com.keyin.city;

import jakarta.persistence.*;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String state;
    private int population;

    @OneToMany
    private List<Airport> airports;

    @OneToMany
    private List<Citizen> citizens;

    public City()
    {

    }

    public City(String name, String state, int population)
    {
        this.name = name;
        this.state = state;
        this.population = population;
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

    public List<Citizen> getCitizens()
    {
        return citizens;
    }

    public void setCitizens(List<Citizen> citizens)
    {
        this.citizens = citizens;
    }
}
