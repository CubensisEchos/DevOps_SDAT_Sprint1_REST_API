package com.keyin.airport;

import com.keyin.city.City;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AirportControllerTest
{
    @Mock
    private AirportService airportService;

    @InjectMocks
    private AirportController airportController;


    private City city;

    @BeforeEach
    void setup()
    {
        city = new City("Test City", "Test State", 8576309);
    }

    @AfterEach
    void tearDown()
    {
        city = null;
    }

    @Test
    public void getAllAirports_ReturnsOkWhenFound()
    {
        List<Airport> airports = List.of(
                new Airport("Test airport", "Test code", city),
                new Airport("Test airport", "Test code", city)
        );

        when(airportService.getAllAirports()).thenReturn(airports);
        ResponseEntity<List<Airport>> response = airportController.getAllAirports();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(airports, response.getBody());
    }

    @Test
    public void getAirportById_ReturnsOkWhenFound()
    {
        Airport airport = new Airport();
        airport.setId(12L);

        Mockito.when(airportService.getAirportById(12L)).thenReturn(Optional.of(airport));
        ResponseEntity<Airport> response = airportController.getAirportById(12L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(12L, response.getBody().getId());
    }

    @Test
    public void getAirportById_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(airportService.getAirportById(22L)).thenReturn(Optional.empty());
        ResponseEntity<Airport> response = airportController.getAirportById(22L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void updateAirport_ReturnsOkWhenUpdated()
    {
        Airport updatedAirport = new Airport();
        updatedAirport.setId(15L);
        updatedAirport.setName("Test update airport");
        updatedAirport.setAirportCode("Test update code");
        updatedAirport.setCity(city);

        Mockito.when(airportService.updateAirport(eq(15L), any(Airport.class))).thenReturn(Optional.of(updatedAirport));
        ResponseEntity<Airport> response = airportController.updateAirport(15L, new Airport());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(15L, response.getBody().getId());
    }

    @Test
    public void updateAirport_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(airportService.updateAirport(eq(36L), any(Airport.class))).thenReturn(Optional.empty());
        ResponseEntity<Airport> response = airportController.updateAirport(36L, new Airport());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteAirport_ReturnsNothingWhenDeleted()
    {
        Mockito.when(airportService.deleteAirport(45L)).thenReturn(true);
        ResponseEntity<Void> response = airportController.deleteAirport(45L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteAirport_ReturnsNoFoundWhenMissing()
    {
        Mockito.when(airportService.deleteAirport(13L)).thenReturn(false);
        ResponseEntity<Void> response = airportController.deleteAirport(13L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void createAirportDelegatesAndReturns()
    {
        Airport createdAirport = new Airport();
        createdAirport.setId(30L);
        createdAirport.setName("Test Airport");

        Mockito.when(airportService.createAirport(any(Airport.class))).thenReturn(createdAirport);
        Airport response = airportController.createAirport(new Airport());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(30L, response.getId());
        verify(airportService).createAirport(any(Airport.class));
    }
}
