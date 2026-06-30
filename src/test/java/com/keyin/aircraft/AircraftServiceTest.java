package com.keyin.aircraft;

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
public class AircraftServiceTest
{
    @Mock
    private AircraftRepository aircraftRepository;

    @InjectMocks
    private AircraftService aircraftService;

    Aircraft aircraft;
    Aircraft aircraft2;
    Aircraft aircraft3;

    List<Aircraft>  aircraftList;

    @BeforeEach
    void setup()
    {
        aircraft = new Aircraft("Test type 1", "Test name 1", 40);
        aircraft2 = new Aircraft("Test type 2", "Test name 2", 62);
        aircraft3 = new Aircraft("Test type 3", "Test name 3", 57);

        aircraftList = List.of(aircraft, aircraft2, aircraft3);
    }

    @AfterEach
    void tearDown()
    {
        aircraft = null;
        aircraft2 = null;
        aircraft3 = null;

        aircraftList = null;
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
}
