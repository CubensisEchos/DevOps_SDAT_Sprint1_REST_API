package com.keyin.airport;

import com.keyin.city.City;
import com.keyin.city.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService
{
    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private CityRepository cityRepository;

    public Airport createAirport(Airport airport)
    {
        return airportRepository.save(airport);
    }

    public List<Airport> getAllAirports()
    {
        return airportRepository.findAll();
    }

    public java.util.Optional<Airport> getAirportById(Long id)
    {
        return airportRepository.findById(id);
    }

    public java.util.Optional<Airport> updateAirport(Long id, Airport updatedAirport)
    {
        return airportRepository.findById(id)
                .map(existing->
                {
                   existing.setName(updatedAirport.getName());
                   existing.setAirportCode(updatedAirport.getAirportCode());
                   return airportRepository.save(existing);
                });
    }

    public boolean deleteAirport(Long id)
    {
        if (airportRepository.existsById(id))
        {
            airportRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public java.util.Optional<Airport> addAirportToCity(Long cityId, Airport airport)
    {
        return cityRepository.findById(cityId)
                .map(city ->
                {
                    airport.setCity(city);
                    return airportRepository.save(airport);
                });
    }
}
