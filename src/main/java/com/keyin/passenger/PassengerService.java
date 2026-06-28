package com.keyin.passenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService
{
    @Autowired
    private PassengerRepository passengerRepository;

    public Passenger createPassenger(Passenger passenger)
    {
        return passengerRepository.save(passenger);
    }

    public List<Passenger> getAllPassengers()
    {
        return passengerRepository.findAll();
    }

    public java.util.Optional<Passenger> getPassengerById(Long id)
    {
        return passengerRepository.findById(id);
    }

    public java.util.Optional<Passenger> updatePassenger(Long id, Passenger updatedPassenger)
    {
        return passengerRepository.findById(id)
                .map(existing ->
                {
                        existing.setFirstName(updatedPassenger.getFirstName());
                        existing.setLastName(updatedPassenger.getLastName());
                        existing.setPhoneNumber(updatedPassenger.getPhoneNumber());
                        return passengerRepository.save(existing);
                });
    }

    public boolean deletePassengerById(Long id)
    {
        if (passengerRepository.existsById(id))
        {
            passengerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //connect to aircraft

    //connect to city
}
