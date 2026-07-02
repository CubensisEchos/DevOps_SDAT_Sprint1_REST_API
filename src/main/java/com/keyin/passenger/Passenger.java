package com.keyin.passenger;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.keyin.aircraft.Aircraft;
import com.keyin.city.City;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Passenger
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonBackReference
    private City city;

    @ManyToMany(mappedBy = "passengers")
    @JsonBackReference("passengers-aircraft")
    private List<Aircraft> aircraftList = new ArrayList<>();

    public Passenger()
    {

    }

    public Passenger(String firstName, String lastName, String phoneNumber, City city)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public City getCity()
    {
        return city;
    }

    public void setCity(City city)
    {
        this.city = city;
    }

    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }
}
