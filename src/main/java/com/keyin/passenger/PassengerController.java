package com.keyin.passenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PassengerController
{
    @Autowired
    private PassengerService passengerService;

    @PostMapping("/passengers")
    public Passenger addNewPassenger(@RequestBody Passenger passenger)
    {
        return passengerService.createPassenger(passenger);
    }

    @GetMapping("/passengers")
    public ResponseEntity<List<Passenger>> getAllPassengers()
    {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @GetMapping("/passengers/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id)
    {
        return passengerService.getPassengerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/passengers/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody Passenger passenger)
    {
        return passengerService.updatePassenger(id, passenger)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/passengers/{id}")
    public ResponseEntity<Void> deletePassengerById(@PathVariable Long id)
    {
        boolean deleted = passengerService.deletePassengerById(id);
        if (deleted)
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cities/{cityId}/passengers")
    public ResponseEntity<Passenger> addPassengerToCity(@PathVariable Long cityId, @RequestBody Passenger passenger)
    {
        return passengerService.addPassengerToCity(cityId, passenger)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
