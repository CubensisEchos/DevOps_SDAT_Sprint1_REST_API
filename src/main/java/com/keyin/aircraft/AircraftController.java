package com.keyin.aircraft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
public class AircraftController
{
    @Autowired
    private AircraftService aircraftService;

    @PostMapping("/aircraft")
    public Aircraft saveNewAircraft(@RequestBody Aircraft aircraft)
    {
        return aircraftService.saveNewAircraft(aircraft);
    }

    @GetMapping("/aircraft")
    public ResponseEntity<List<Aircraft>> getAllAircraft()
    {
        return ResponseEntity.ok(aircraftService.getAllAircraft());
    }

    @GetMapping("/aircraft/{id}")
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable Long id)
    {
        return aircraftService.getAircraftById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/aircraft/{id}")
    public ResponseEntity<Aircraft> updateAircraft(@PathVariable Long id, @RequestBody Aircraft aircraft)
    {
        return aircraftService.updateAircraft(id, aircraft)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/aircraft/{id}")
    public ResponseEntity<Void> deleteAircraftById(@PathVariable Long id)
    {
        boolean deleted = aircraftService.deleteAircraftById(id);
        if (deleted)
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/aircraft/{aircraftId}/airports/{airportId}")
    public ResponseEntity<Aircraft> addAirportToAircraft(@PathVariable Long aircraftId, @PathVariable Long airportId)
    {
        return aircraftService.addAirportToAircraft(aircraftId, airportId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/aircraft/{aircraftId}/passengers/{passengerId}")
    public ResponseEntity<Aircraft> addPassengerToAircraft(@PathVariable Long aircraftId, @PathVariable Long passengerId)
    {
        return aircraftService.addPassengerToAircraft(aircraftId, passengerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
