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

    @PostMapping("/cities")
    public City createCity(@RequestBody City city)
    {
        return cityService.createCity(city);
    }

   @GetMapping("/cities")
   public ResponseEntity<List<City>> getAllCities()
   {
       return ResponseEntity.ok(cityService.getAllCities());
   }

    @GetMapping("/cities/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id)
    {
        return cityService.getCityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City city)
    {
        return cityService.updateCity(id, city)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id)
    {
        boolean deleted = cityService.deleteCityById(id);
        if (deleted)
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
