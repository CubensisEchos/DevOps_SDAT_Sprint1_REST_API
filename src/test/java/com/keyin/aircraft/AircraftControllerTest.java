package com.keyin.aircraft;

import com.keyin.airport.Airport;
import com.keyin.passenger.Passenger;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AircraftControllerTest
{
    @Mock
    private AircraftService  aircraftService;

    @InjectMocks
    private AircraftController aircraftController;

    @Test
    public void getAllAircraft_ReturnsOkWhenFound()
    {
        List<Aircraft> aircraft = List.of(
                new Aircraft("Test type 1", "Test name 1", 43),
                new Aircraft("Test type 2", "Test name 2", 23)
        );

        when(aircraftService.getAllAircraft()).thenReturn(aircraft);
        ResponseEntity<List<Aircraft>> response = aircraftController.getAllAircraft();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(aircraft, response.getBody());
    }

    @Test
    public void getAircraftById_ReturnsOkWhenFound()
    {
        Aircraft aircraft = new Aircraft();
        aircraft.setId(32L);

        Mockito.when(aircraftService.getAircraftById(32L)).thenReturn(Optional.of(aircraft));
        ResponseEntity<Aircraft> response = aircraftController.getAircraftById(32L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(32L, response.getBody().getId());
    }

    @Test
    public void getAircraftById_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(aircraftService.getAircraftById(18L)).thenReturn(Optional.empty());
        ResponseEntity<Aircraft> response = aircraftController.getAircraftById(18L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void updateAircraft_ReturnsOkWhenUpdated()
    {
        Aircraft updatedAircraft = new Aircraft();
        updatedAircraft.setId(25L);
        updatedAircraft.setType("Test update type");
        updatedAircraft.setAirlineName("Test update name");
        updatedAircraft.setNumberOfPassengers(34);

        Mockito.when(aircraftService.updateAircraft(eq(25L), any(Aircraft.class))).thenReturn(Optional.of(updatedAircraft));
        ResponseEntity<Aircraft> response = aircraftController.updateAircraft(25L, new Aircraft());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(25L, response.getBody().getId());
        Assertions.assertEquals("Test update type", response.getBody().getType());
        Assertions.assertEquals("Test update name", response.getBody().getAirlineName());
        Assertions.assertEquals(34,response.getBody().getNumberOfPassengers());
    }

    @Test
    public void updateAircraft_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(aircraftService.updateAircraft(eq(36L), any(Aircraft.class))).thenReturn(Optional.empty());
        ResponseEntity<Aircraft> response = aircraftController.updateAircraft(36L, new Aircraft());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteAircraft_ReturnsNothingWhenDeleted()
    {
        Mockito.when(aircraftService.deleteAircraftById(75L)).thenReturn(true);
        ResponseEntity<Void> response = aircraftController.deleteAircraftById(75L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteAircraft_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(aircraftService.deleteAircraftById(31L)).thenReturn(false);
        ResponseEntity<Void> response = aircraftController.deleteAircraftById(31L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void createAircraftsDelegatesAndReturns()
    {
        Aircraft createdAircraft = new Aircraft();
        createdAircraft.setId(80L);
        createdAircraft.setType("Test type");

        Mockito.when(aircraftService.saveNewAircraft(any(Aircraft.class))).thenReturn(createdAircraft);
        Aircraft response = aircraftController.saveNewAircraft(new Aircraft());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(80L, response.getId());
        Assertions.assertEquals("Test type", response.getType());
        verify(aircraftService).saveNewAircraft(any(Aircraft.class));
    }

    @Test
    public void addPassengerToAircraft_ReturnsOkWhenAdded()
    {
        Long aircraftId = 1L;
        Long passengerId = 2L;

        Passenger passenger = new Passenger();
        passenger.setId(passengerId);
        passenger.setFirstName("John");
        passenger.setLastName("Smith");

        Aircraft aircraft = new Aircraft();
        aircraft.setId(aircraftId);

        Mockito.when(aircraftService.addPassengerToAircraft(eq(aircraftId), eq(passengerId))).thenReturn(Optional.of(aircraft));
        ResponseEntity<Aircraft> response = aircraftController.addPassengerToAircraft(aircraftId, passengerId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(aircraftId, response.getBody().getId());
        verify(aircraftService).addPassengerToAircraft(eq(aircraftId), eq(passengerId));
    }

    @Test
    public void addPassengerToAircraft_ReturnsNotFoundWhenMissing()
    {
        Long aircraftId = 1L;
        Long passengerId = 60L;

        Mockito.when(aircraftService.addPassengerToAircraft(eq(aircraftId), eq(passengerId))).thenReturn(Optional.empty());
        ResponseEntity<Aircraft> response = aircraftController.addPassengerToAircraft(aircraftId, passengerId);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(aircraftService).addPassengerToAircraft(eq(aircraftId), eq(passengerId));
    }

    @Test
    public void addAirportToAircraft_ReturnsOkWhenAdded()
    {
        Long aircraftId = 1L;
        Long airportId = 5L;

        Airport airport = new Airport();
        airport.setId(airportId);
        airport.setName("Test Airport");

        Aircraft aircraft = new Aircraft();
        aircraft.setId(aircraftId);

        Mockito.when(aircraftService.addAirportToAircraft(eq(aircraftId), eq(airportId))).thenReturn(Optional.of(aircraft));
        ResponseEntity<Aircraft> response = aircraftController.addAirportToAircraft(aircraftId, airportId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(aircraftId, response.getBody().getId());
        verify(aircraftService).addAirportToAircraft(eq(aircraftId), eq(airportId));
    }
    @Test
    public void addAirportToAircraft_ReturnsNotFoundWhenMissing()
    {
        Long aircraftId = 1L;
        Long airportId = 99L;

        Mockito.when(aircraftService.addAirportToAircraft(eq(aircraftId), eq(airportId))).thenReturn(Optional.empty());
        ResponseEntity<Aircraft> response = aircraftController.addAirportToAircraft(aircraftId, airportId);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(aircraftService).addAirportToAircraft(eq(aircraftId), eq(airportId));
    }
}
