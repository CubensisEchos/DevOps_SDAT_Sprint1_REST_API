package com.keyin.city;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityControllerTest
{
    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    @Test
    public void getAllCites_ReturnsOkWhenFound()
    {
        List<City> cities = List.of(
                new City("Test city", "Test state", 1965555),
                new City("Test city", "Test state", 8576309)
        );

        when(cityService.getAllCities()).thenReturn(cities);
        ResponseEntity<List<City>> response = cityController.getAllCities();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(cities, response.getBody());
    }

    @Test
    public void getCityById_ReturnsOkWhenFound()
    {
        City city = new City();
        city.setId(12L);

        Mockito.when(cityService.getCityById(12L)).thenReturn(Optional.of(city));
        ResponseEntity<City> response = cityController.getCityById(12L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(12L, response.getBody().getId());
    }

    @Test
    public void getCityById_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(cityService.getCityById(22L)).thenReturn(Optional.empty());
        ResponseEntity<City> response = cityController.getCityById(22L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    //save

    @Test
    public void updateCity_ReturnsOkWhenUpdated()
    {
        City updatedCity = new City();
        updatedCity.setId(15L);
        updatedCity.setName("Test update city");
        updatedCity.setState("Test update state");
        updatedCity.setPopulation(1965555);

        Mockito.when(cityService.updateCity(eq(15L), any(City.class))).thenReturn(Optional.of(updatedCity));
        ResponseEntity<City> response = cityController.updateCity(15L, new City());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(15L, response.getBody().getId());
    }

    @Test
    public void updateCity_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(cityService.updateCity(eq(36L), any(City.class))).thenReturn(Optional.empty());
        ResponseEntity<City> response = cityController.updateCity(36L, new City());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteCity_ReturnsNothingWhenDeleted()
    {
        Mockito.when(cityService.deleteCityById(45L)).thenReturn(true);
        ResponseEntity<Void> response = cityController.deleteCity(45L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteCity_ReturnsNoFoundWhenMissing()
    {
        Mockito.when(cityService.deleteCityById(13L)).thenReturn(false);
        ResponseEntity<Void> response = cityController.deleteCity(13L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void createCityDelegatesAndReturns()
    {
       City createdCity = new City();
       createdCity.setId(30L);
       createdCity.setName("Test City");

       Mockito.when(cityService.createCity(any(City.class))).thenReturn(createdCity);
       City response = cityController.createCity(new City());

       Assertions.assertNotNull(response);
       Assertions.assertEquals(30L, response.getId());
       verify(cityService).createCity(any(City.class));
    }
}

