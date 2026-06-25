package com.keyin.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CityController
{
    @Autowired
    private CityService cityService;

    @PostMapping("/city")
    public City createCity(@RequestBody City city)
    {
        return cityService.createCity(city);
    }

    @GetMapping("/city/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id)
    {
        return cityService.getCityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/city/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City city)
    {
        return cityService.updateCity(id, city)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id)
    {
        boolean deleted = cityService.deleteCity(id);
        if (deleted)
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/seed")
    public ResponseEntity<List<City>> seedCities()
    {
        return new ResponseEntity<>(cityService.seedCities(), HttpStatus.OK);
    }
}
