package com.keyin.airport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AirportController
{
    @Autowired
    private AirportService airportService;

    @PostMapping("/airport")
    public Airport createAirport(@RequestBody Airport airport)
    {
        return airportService.createAirport(airport);
    }

    @GetMapping("/airport")
    public ResponseEntity<List<Airport>> getAllAirports()
    {
        return ResponseEntity.ok(airportService.getAllAirports());
    }

    @GetMapping("/airport/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id)
    {
        return airportService.getAirportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/airport/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody Airport airport)
    {
        return airportService.updateAirport(id, airport)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id)
    {
        boolean deleted = airportService.deleteAirport(id);
        if (deleted)
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //add seed controller
}
