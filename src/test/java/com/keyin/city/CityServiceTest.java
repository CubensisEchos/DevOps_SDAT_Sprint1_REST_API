package com.keyin.city;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest
{
    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    City city;
    City city2;
    City city3;

    List<City> cityList;

    @BeforeEach
    void setup()
    {
        city = new City("Test City 1", "Test State 1", 252423);
        city2 = new City("Test City 2", "Test State 2", 654321);
        city3 = new City("Test City 3", "Test State 3", 123456);

        cityList = List.of(city, city2, city3);
    }

    @AfterEach
    void tearDown()
    {
        city = null;
        city2 = null;
        city3 = null;

        cityList = null;
    }

    @Test
    public void returnFullCitiesList()
    {
        Mockito.when(cityRepository.findAll()).thenReturn(cityList);
        Iterable<City> expected = cityService.getAllCities();

        Assertions.assertEquals(cityList, expected);
    }

    @Test
    public void findCityById_ReturnsCityWhenFound()
    {
        Mockito.when(cityRepository.findById(1L)).thenReturn(java.util.Optional.of(city));
        Optional<City> expected = cityService.getCityById(1L);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(city, expected.get());
    }

    @Test
    public void findCityById_ReturnsEmptyWhenMissing()
    {
        Mockito.when(cityRepository.findById(10L)).thenReturn(Optional.empty());
        Optional<City> expected = cityService.getCityById(10L);

        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    public void updateCity_ReturnsUpdatedCity()
    {
        City updatedCity = new City("Test updated city", "Test updated state", 102030);

        Mockito.when(cityRepository.findById(1L)).thenReturn(java.util.Optional.of(city));
        Mockito.when(cityRepository.save(Mockito.any(City.class))).thenReturn(updatedCity);
        Optional<City> expected = cityService.updateCity(1L, updatedCity);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals("Test updated city", expected.get().getName());
        Assertions.assertEquals("Test updated state", expected.get().getState());
        Assertions.assertEquals(102030, expected.get().getPopulation());
    }

    @Test
    public void updateCity_CityNotFound()
    {
        City updatedCity = new City("Test updated city", "Test updated state", 102030);
        Mockito.when(cityRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        Optional<City> expected = cityService.updateCity(1L, updatedCity);

        Assertions.assertFalse(expected.isPresent());
    }

    @Test
    public void deleteCityById_ReturnsTrueWhenDeleted()
    {
        Mockito.when(cityRepository.existsById(1L)).thenReturn(true);
        boolean deleted = cityService.deleteCityById(1L);

        Assertions.assertTrue(deleted);
        Mockito.verify(cityRepository).deleteById(1L);
    }

    @Test
    public void deleteCity_ReturnsFalseWhenMissing()
    {
        Mockito.when(cityRepository.existsById(78L)).thenReturn(false);
        boolean deleted = cityService.deleteCityById(78L);

        Assertions.assertFalse(deleted);
        Mockito.verify(cityRepository, never()).deleteById(anyLong());
    }

    @Test
    public void saveNewCity_ReturnsSavedCity()
    {
        Mockito.when(cityRepository.save(city)).thenReturn(city);
        City savedCity = cityService.createCity(city);

        Assertions.assertEquals(city, savedCity);
        Mockito.verify(cityRepository).save(city);
    }
}
