package com.keyin.airport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService
{
    @Autowired
    private  AirportRepository airportRepository;

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

    //add cities?

    //add aircafts?
}
