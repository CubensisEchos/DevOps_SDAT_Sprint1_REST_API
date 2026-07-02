package com.keyin.aircraft;

import com.keyin.airport.Airport;
import com.keyin.airport.AirportRepository;
import com.keyin.passenger.Passenger;
import com.keyin.passenger.PassengerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class AircraftServiceTest
{
    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AircraftService aircraftService;

    Aircraft aircraft;
    Aircraft aircraft2;
    Aircraft aircraft3;

    List<Aircraft>  aircraftList;

    Passenger passenger;
    Airport airport;

    @BeforeEach
    void setup()
    {
        aircraft = new Aircraft("Test type 1", "Test name 1", 40);
        aircraft2 = new Aircraft("Test type 2", "Test name 2", 62);
        aircraft3 = new Aircraft("Test type 3", "Test name 3", 57);

        aircraftList = List.of(aircraft, aircraft2, aircraft3);

        passenger = new Passenger("John", "Smith", "555-1234", null);
        airport = new Airport("Test airport", "Test code", null);
    }

    @AfterEach
    void tearDown()
    {
        aircraft = null;
        aircraft2 = null;
        aircraft3 = null;

        aircraftList = null;
        passenger = null;
        airport = null;
    }

    @Test
    public void returnFullAircraftList()
    {
        Mockito.when(aircraftRepository.findAll()).thenReturn(aircraftList);
        Iterable<Aircraft> expected = aircraftService.getAllAircraft();

        Assertions.assertEquals(aircraftList, expected);
    }

    @Test
    public void findAircraftById_ReturnsAircraftWhenFound()
    {
        Mockito.when(aircraftRepository.findById(1L)).thenReturn(java.util.Optional.of(aircraft));
        Optional<Aircraft> expected = aircraftService.getAircraftById(1L);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(aircraft, expected.get());
    }

    @Test
    public void findAircraftById_ReturnsEmptyWhenMissing()
    {
        Mockito.when(aircraftRepository.findById(10L)).thenReturn(Optional.empty());
        Optional<Aircraft> expected = aircraftService.getAircraftById(10L);

        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    public void updateAircraft_ReturnsUpdatedAircraft()
    {
        Aircraft updatedAircraft = new Aircraft("Test updated type", "Test updated name",53);

        Mockito.when(aircraftRepository.findById(1L)).thenReturn(java.util.Optional.of(aircraft));
        Mockito.when(aircraftRepository.save(Mockito.any(Aircraft.class))).thenReturn(updatedAircraft);
        Optional<Aircraft> expected = aircraftService.updateAircraft(1L, updatedAircraft);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals("Test updated type", expected.get().getType());
        Assertions.assertEquals("Test updated name", expected.get().getAirlineName());
        Assertions.assertEquals(53, expected.get().getNumberOfPassengers());

    }

    @Test
    public void updateAircraft_PassengerNotFound()
    {
        Aircraft updatedAircraft = new Aircraft("Test updated type", "Test updated name",53);

        Mockito.when(aircraftRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        Optional<Aircraft> expected = aircraftService.updateAircraft(1L, updatedAircraft);

        Assertions.assertFalse(expected.isPresent());
    }

    @Test
    public void deleteAircraftById_ReturnsTrueWhenDeleted()
    {
        Mockito.when(aircraftRepository.existsById(1L)).thenReturn(true);
        boolean deleted = aircraftService.deleteAircraftById(1L);

        Assertions.assertTrue(deleted);
        Mockito.verify(aircraftRepository).deleteById(1L);
    }

    @Test
    public void deleteAircraft_ReturnsFalseWhenMissing()
    {
        Mockito.when(aircraftRepository.existsById(78L)).thenReturn(false);
        boolean deleted = aircraftService.deleteAircraftById(78L);

        Assertions.assertFalse(deleted);
        Mockito.verify(aircraftRepository, never()).deleteById(anyLong());
    }

    @Test
    public void saveNewAircraft_ReturnsSavedAircraft()
    {
        Mockito.when(aircraftRepository.save(aircraft)).thenReturn(aircraft);
        Aircraft savedAircraft = aircraftService.saveNewAircraft(aircraft);

        Assertions.assertEquals(aircraft, savedAircraft);
        Mockito.verify(aircraftRepository).save(aircraft);
    }

    @Test
    public void addPassengerToAircraft_ReturnsAircraftWithPassenger()
    {
        Long aircraftId = 1L;
        Long passengerId = 2L;

        aircraft.setPassengers(new ArrayList<>());

        Mockito.when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(aircraft));
        Mockito.when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(passenger));
        Mockito.when(aircraftRepository.save(Mockito.any(Aircraft.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Aircraft> expected = aircraftService.addPassengerToAircraft(aircraftId, passengerId);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(1, expected.get().getPassengers().size());
        Assertions.assertEquals(passenger, expected.get().getPassengers().get(0));
        Mockito.verify(aircraftRepository).findById(aircraftId);
        Mockito.verify(passengerRepository).findById(passengerId);
        Mockito.verify(aircraftRepository).save(aircraft);
    }

    @Test
    public void addAirportToAircraft_ReturnsAircraftWithAirport()
    {
        Long aircraftId = 1L;
        Long airportId = 5L;

        aircraft.setAirports(new java.util.ArrayList<>());

        Mockito.when(aircraftRepository.findById(aircraftId)).thenReturn(Optional.of(aircraft));
        Mockito.when(airportRepository.findById(airportId)).thenReturn(Optional.of(airport));
        Mockito.when(aircraftRepository.save(Mockito.any(Aircraft.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Aircraft> expected = aircraftService.addAirportToAircraft(aircraftId, airportId);


        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(1, expected.get().getAirports().size());
        Assertions.assertEquals(airport, expected.get().getAirports().get(0));
        Mockito.verify(aircraftRepository).findById(aircraftId);
        Mockito.verify(airportRepository).findById(airportId);
        Mockito.verify(aircraftRepository).save(aircraft);
    }

}
