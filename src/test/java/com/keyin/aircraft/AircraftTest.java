package com.keyin.aircraft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AircraftTest
{
    Aircraft aircraft;
    @BeforeEach
    void setup()
    {
        aircraft = new Aircraft("Commercial", "West Jet", 70);
    }

    @AfterEach
    void tearDown()
    {
        aircraft = null;
    }

    @Test
    void TestCreation()
    {
        Assertions.assertEquals("Commercial", aircraft.getType());
        Assertions.assertEquals("West Jet", aircraft.getAirlineName());
        Assertions.assertEquals(70, aircraft.getNumberOfPassengers());
    }

    @Test
    void TestAircraftSetter()
    {
        aircraft.setType("updated type");
        aircraft.setAirlineName("updated name");
        aircraft.setNumberOfPassengers(50);

        Assertions.assertEquals("updated type", aircraft.getType());
        Assertions.assertEquals("updated name", aircraft.getAirlineName());
        Assertions.assertEquals(50, aircraft.getNumberOfPassengers());
    }
}
