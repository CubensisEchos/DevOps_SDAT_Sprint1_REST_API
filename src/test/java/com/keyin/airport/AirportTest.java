package com.keyin.airport;

import com.keyin.city.City;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AirportTest
{
    Airport airport;

    @BeforeEach
    void setup()
    {
        City city = new City("Test City 1", "Test State 1", 1000);
        airport = new Airport("Test airport 1", "Test code 1", city);
    }

    @AfterEach
    void tearDown()
    {
        airport = null;
    }

    @Test
    void TestCreation()
    {
        Assertions.assertEquals("Test airport 1", airport.getName());
        Assertions.assertEquals("Test code 1", airport.getAirportCode());
    }

    @Test
    void TestAirportSetter()
    {
        airport.setName("Tested airport");
        airport.setAirportCode("Tested code");

        Assertions.assertEquals("Tested airport", airport.getName());
        Assertions.assertEquals("Tested code", airport.getAirportCode());
    }
}
