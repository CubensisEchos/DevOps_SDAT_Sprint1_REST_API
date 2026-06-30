package com.keyin.aircraft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftService
{
    @Autowired
    private AircraftRepository aircraftRepository;

    public Aircraft saveNewAircraft(Aircraft aircraft)
    {
        return aircraftRepository.save(aircraft);
    }

    public List<Aircraft> getAllAircraft()
    {
        return aircraftRepository.findAll();
    }

    public java.util.Optional<Aircraft> getAircraftById(Long id)
    {
        return aircraftRepository.findById(id);
    }

    public java.util.Optional<Aircraft> updateAircraft(Long id, Aircraft updatedAircraft)
    {
        return aircraftRepository.findById(id)
                .map(existing->
                {
                    existing.setType(updatedAircraft.getType());
                    existing.setAirlineName(updatedAircraft.getAirlineName());
                    existing.setNumberOfPassengers(updatedAircraft.getNumberOfPassengers());
                    return  aircraftRepository.save(existing);
                });
    }

    public boolean deleteAircraftById(Long id)
    {
        if (aircraftRepository.existsById(id))
        {
            aircraftRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
