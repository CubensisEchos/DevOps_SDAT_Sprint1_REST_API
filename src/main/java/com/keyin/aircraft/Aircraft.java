package com.keyin.aircraft;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Aircraft
{
    @Id
    @GeneratedValue
    private Long id;
    private String model;
}
