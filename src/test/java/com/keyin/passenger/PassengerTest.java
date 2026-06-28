package com.keyin.passenger;

import com.keyin.city.City;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassengerTest
{
    Passenger passenger;

    @BeforeEach
    void setup()
    {
        City city = new City("Test City 1", "Test State 1", 1000);
        passenger = new Passenger("First name test","Last name test", "857-2345", city);
    }

    @AfterEach
    void tearDown()
    {
        passenger = null;
    }

    @Test
    void TestCreation()
    {
        Assertions.assertEquals("First name test", passenger.getFirstName());
        Assertions.assertEquals("Last name test", passenger.getLastName());
    }

    @Test
    void TestPassengerSetter()
    {
        passenger.setFirstName("Tested first name");
        passenger.setLastName("Tested last name");

        Assertions.assertEquals("Tested first name", passenger.getFirstName());
        Assertions.assertEquals("Tested last name", passenger.getLastName());
    }
}
