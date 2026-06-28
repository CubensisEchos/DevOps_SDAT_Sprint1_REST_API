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
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest
{
    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerService passengerService;

    City city;

    Passenger passenger;
    Passenger passenger2;
    Passenger passenger3;

    List<Passenger> passengerList;

    @BeforeEach
    void setup()
    {
        city = new City("Test City 1", "Test State 1", 252423);

        passenger = new Passenger("Test first name 1", "Test last name 1", "111-2222", city);
        passenger2 = new Passenger("Test first name 2", "Test last name 2", "111-3333", city);
        passenger3 = new Passenger("Test first name 3", "Test last name 3", "111-4444", city);

        passengerList = List.of(passenger, passenger2, passenger3);
    }

    @AfterEach
    void tearDown()
    {
        city = null;

        passenger = null;
        passenger2 = null;
        passenger3 = null;

        passengerList = null;
    }

    @Test
    public void returnFullPassengersList()
    {
        Mockito.when(passengerRepository.findAll()).thenReturn(passengerList);
        Iterable<Passenger> expected = passengerService.getAllPassengers();

        Assertions.assertEquals(passengerList, expected);
    }

    @Test
    public void findPassengerById_ReturnsPassengerWhenFound()
    {
        Mockito.when(passengerRepository.findById(1L)).thenReturn(java.util.Optional.of(passenger));
        Optional<Passenger> expected = passengerService.getPassengerById(1L);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(passenger, expected.get());
    }

    @Test
    public void findPassengerById_ReturnsEmptyWhenMissing()
    {
        Mockito.when(passengerRepository.findById(10L)).thenReturn(Optional.empty());
        Optional<Passenger> expected = passengerService.getPassengerById(10L);

        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    public void updatePassenger_ReturnsUpdatedPassenger()
    {
        Passenger updatedPassenger = new Passenger("Test updated first name", "Test updated last name","999-8888",  city);

        Mockito.when(passengerRepository.findById(1L)).thenReturn(java.util.Optional.of(passenger));
        Mockito.when(passengerRepository.save(Mockito.any(Passenger.class))).thenReturn(updatedPassenger);
        Optional<Passenger> expected = passengerService.updatePassenger(1L, updatedPassenger);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals("Test updated first name", expected.get().getFirstName());
        Assertions.assertEquals("Test updated last name", expected.get().getLastName());
        Assertions.assertEquals("999-8888", expected.get().getPhoneNumber());
        Assertions.assertEquals(city, expected.get().getCity());
    }

    @Test
    public void updatePassenger_PassengerNotFound()
    {
        Passenger updatedPassenger = new Passenger("Test updated first name", "Test updated last name","999-8888",  city);

        Mockito.when(passengerRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        Optional<Passenger> expected = passengerService.updatePassenger(1L, updatedPassenger);

        Assertions.assertFalse(expected.isPresent());
    }

    @Test
    public void deletePassengerById_ReturnsTrueWhenDeleted()
    {
        Mockito.when(passengerRepository.existsById(1L)).thenReturn(true);
        boolean deleted = passengerService.deletePassengerById(1L);

        Assertions.assertTrue(deleted);
        Mockito.verify(passengerRepository).deleteById(1L);
    }

    @Test
    public void deletePassenger_ReturnsFalseWhenMissing()
    {
        Mockito.when(passengerRepository.existsById(78L)).thenReturn(false);
        boolean deleted = passengerService.deletePassengerById(78L);

        Assertions.assertFalse(deleted);
        Mockito.verify(passengerRepository, never()).deleteById(anyLong());
    }

    @Test
    public void saveNewPassenger_ReturnsSavedPassenger()
    {
        Mockito.when(passengerRepository.save(passenger)).thenReturn(passenger);
        Passenger savedPassenger = passengerService.createPassenger(passenger);

        Assertions.assertEquals(passenger, savedPassenger);
        Mockito.verify(passengerRepository).save(passenger);
    }

}
