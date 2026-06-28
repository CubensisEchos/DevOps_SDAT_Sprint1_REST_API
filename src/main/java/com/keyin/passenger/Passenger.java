package com.keyin.passenger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Passenger
{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
