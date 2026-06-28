package com.keyin.passenger;

import com.keyin.airport.Airport;
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
public class PassengerControllerTest
{
    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private PassengerController passengerController;

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
    public void getAllPassengers_ReturnsOkWhenFound()
    {
        List<Passenger> passengers = List.of(
                new Passenger("Test first name 1", "Test last name 1", "123-4567", city),
                new Passenger("Test first name 2", "Test last name 2", "192-3847", city)
        );

        when(passengerService.getAllPassengers()).thenReturn(passengers);
        ResponseEntity<List<Passenger>> response = passengerController.getAllPassengers();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(passengers, response.getBody());
    }

    @Test
    public void getPassengerById_ReturnsOkWhenFound()
    {
        Passenger passenger = new Passenger();
        passenger.setId(32L);

        Mockito.when(passengerService.getPassengerById(32L)).thenReturn(Optional.of(passenger));
        ResponseEntity<Passenger> response = passengerController.getPassengerById(32L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(32L, response.getBody().getId());
    }

    @Test
    public void getPassengerById_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(passengerService.getPassengerById(18L)).thenReturn(Optional.empty());
        ResponseEntity<Passenger> response = passengerController.getPassengerById(18L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void updatePassenger_ReturnsOkWhenUpdated()
    {
        Passenger updatedPassenger = new Passenger();
        updatedPassenger.setId(25L);
        updatedPassenger.setFirstName("Test update first name");
        updatedPassenger.setLastName("Test update last name");
        updatedPassenger.setPhoneNumber("325-7465");
        updatedPassenger.setCity(city);

        Mockito.when(passengerService.updatePassenger(eq(25L), any(Passenger.class))).thenReturn(Optional.of(updatedPassenger));
        ResponseEntity<Passenger> response = passengerController.updatePassenger(25L, new Passenger());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(25L, response.getBody().getId());
    }

    @Test
    public void updatePassenger_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(passengerService.updatePassenger(eq(36L), any(Passenger.class))).thenReturn(Optional.empty());
        ResponseEntity<Passenger> response = passengerController.updatePassenger(36L, new Passenger());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deletePassenger_ReturnsNothingWhenDeleted()
    {
        Mockito.when(passengerService.deletePassengerById(75L)).thenReturn(true);
        ResponseEntity<Void> response = passengerController.deletePassengerById(75L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deletePassenger_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(passengerService.deletePassengerById(31L)).thenReturn(false);
        ResponseEntity<Void> response = passengerController.deletePassengerById(31L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void createPassengerDelegatesAndReturns()
    {
        Passenger createdPassenger = new Passenger();
        createdPassenger.setId(80L);
        createdPassenger.setFirstName("Test first name");

        Mockito.when(passengerService.createPassenger(any(Passenger.class))).thenReturn(createdPassenger);
        Passenger response = passengerController.addNewPassenger(new Passenger());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(80L, response.getId());
        Assertions.assertEquals("Test first name", response.getFirstName());
        verify(passengerService).createPassenger(any(Passenger.class));
    }
}
