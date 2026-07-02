package com.keyin.aircraft;

import com.keyin.airport.AirportRepository;
import com.keyin.passenger.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftService
{
    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private PassengerRepository passengerRepository;

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

    public java.util.Optional<Aircraft> addAirportToAircraft(Long aircraftId, Long airportId)
    {
        return aircraftRepository.findById(aircraftId).flatMap(aircraft ->
                airportRepository.findById(airportId)
                        .map(airport ->
                        {
                            aircraft.getAirports().add(airport);
                            return aircraftRepository.save(aircraft);
                        })
        );
    }

    public java.util.Optional<Aircraft> addPassengerToAircraft(Long aircraftId, Long passengerId)
    {
        return aircraftRepository.findById(aircraftId).flatMap(aircraft ->
                passengerRepository.findById(passengerId)
                        .map(passenger ->
                        {
                            aircraft.getPassengers().add(passenger);
                            return aircraftRepository.save(aircraft);
                        })
        );
    }
}
