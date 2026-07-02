package com.keyin.airport;

import com.keyin.city.City;
import com.keyin.city.CityRepository;
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
public class AirportServiceTest
{
    @Mock
    private AirportRepository airportRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private AirportService airportService;

    City city;

    Airport airport;
    Airport airport2;
    Airport airport3;

    List<Airport> airportList;

    @BeforeEach
    void setup()
    {
        city = new City("Test City 1", "Test State 1", 252423);

        airport = new Airport("Test airport 1", "Test code 1", city);
        airport2 = new Airport("Test airport 2", "Test code 2", city);
        airport3 = new Airport("Test airport 3", "Test code 3", city);

        airportList = List.of(airport, airport2, airport3);
    }

    @AfterEach
    void tearDown()
    {
        city = null;

        airport = null;
        airport2 = null;
        airport3 = null;

        airportList = null;
    }

    @Test
    public void returnFullAirportsList()
    {
        Mockito.when(airportRepository.findAll()).thenReturn(airportList);
        Iterable<Airport> expected = airportService.getAllAirports();

        Assertions.assertEquals(airportList, expected);
    }

    @Test
    public void findAirportById_ReturnsAirportWhenFound()
    {
        Mockito.when(airportRepository.findById(1L)).thenReturn(java.util.Optional.of(airport));
        Optional<Airport> expected = airportService.getAirportById(1L);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(airport, expected.get());
    }

    @Test
    public void findAirportById_ReturnsEmptyWhenMissing()
    {
        Mockito.when(airportRepository.findById(10L)).thenReturn(Optional.empty());
        Optional<Airport> expected = airportService.getAirportById(10L);

        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    public void updateAirport_ReturnsUpdatedAirport()
    {
        Airport updatedAirport = new Airport("Test updated airport", "Test updated code", city);

        Mockito.when(airportRepository.findById(1L)).thenReturn(java.util.Optional.of(airport));
        Mockito.when(airportRepository.save(Mockito.any(Airport.class))).thenReturn(updatedAirport);
        Optional<Airport> expected = airportService.updateAirport(1L, updatedAirport);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals("Test updated airport", expected.get().getName());
        Assertions.assertEquals("Test updated code", expected.get().getAirportCode());
        Assertions.assertEquals(city, expected.get().getCity());
    }

    @Test
    public void updateAirport_AirportNotFound()
    {
        Airport updatedAirport = new Airport("Test updated airport", "Test updated code", city);
        Mockito.when(airportRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        Optional<Airport> expected = airportService.updateAirport(1L, updatedAirport);

        Assertions.assertFalse(expected.isPresent());
    }

    @Test
    public void deleteAirportById_ReturnsTrueWhenDeleted()
    {
        Mockito.when(airportRepository.existsById(1L)).thenReturn(true);
        boolean deleted = airportService.deleteAirport(1L);

        Assertions.assertTrue(deleted);
        Mockito.verify(airportRepository).deleteById(1L);
    }

    @Test
    public void deleteAirport_ReturnsFalseWhenMissing()
    {
        Mockito.when(airportRepository.existsById(78L)).thenReturn(false);
        boolean deleted = airportService.deleteAirport(78L);

        Assertions.assertFalse(deleted);
        Mockito.verify(airportRepository, never()).deleteById(anyLong());
    }

    @Test
    public void saveNewAirport_ReturnsSavedAirport()
    {
        Mockito.when(airportRepository.save(airport)).thenReturn(airport);
        Airport savedAirport = airportService.createAirport(airport);

        Assertions.assertEquals(airport, savedAirport);
        Mockito.verify(airportRepository).save(airport);
    }

    @Test
    public void addAirportToCity_ReturnsAirportAndCity()
    {
        Long cityId = 1L;

        Mockito.when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));
        Mockito.when(airportRepository.save(Mockito.any(Airport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Airport> expected = airportService.addAirportToCity(cityId, airport);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(city, expected.get().getCity());
        Mockito.verify(cityRepository).findById(cityId);
        Mockito.verify(airportRepository).save(airport);
    }
}
