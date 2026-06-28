package com.keyin.city;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CityTest
{
    City city;

    @BeforeEach
    void setup()
    {
        city = new City("Test City 1", "Test State 1", 99999);
    }

    @AfterEach
    void tearDown()
    {
        city = null;
    }

    @Test
    void TestCreation()
    {
        Assertions.assertEquals("Test City 1", city.getName());
        Assertions.assertEquals("Test State 1", city.getState());
        Assertions.assertEquals(99999, city.getPopulation());
    }

    @Test
    void TestCitySetter()
    {
        city.setName("Tested City");
        city.setState("Tested State");
        city.setPopulation(11001);

        Assertions.assertEquals("Tested City", city.getName());
        Assertions.assertEquals("Tested State", city.getState());
        Assertions.assertEquals(11001, city.getPopulation());
    }
}
