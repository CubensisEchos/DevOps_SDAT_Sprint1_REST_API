package com.keyin.aircraft;

import com.keyin.passenger.Passenger;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Aircraft
{
    @Id
    @GeneratedValue
    private Long id;
    private String model;

    @ManyToMany
    private List<Passenger> passengers = new ArrayList<>();
}
