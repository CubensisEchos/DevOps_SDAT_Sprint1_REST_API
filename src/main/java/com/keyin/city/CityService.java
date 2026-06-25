package com.keyin.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService
{
    @Autowired
    private CityRepository cityRepository;

    public City createCity(City city)
    {
        return cityRepository.save(city);
    }

    public java.util.Optional<City> getCityById(Long id)
    {
        return cityRepository.findById(id);
    }

    public java.util.Optional<City> updateCity(Long id, City updatedCity)
    {
        return cityRepository.findById(id)
                .map(existing ->
                {
                   existing.setName(updatedCity.getName());
                   existing.setState(updatedCity.getState());
                   existing.setPopulation(updatedCity.getPopulation());
                   return cityRepository.save(existing);
                });
    }

    public boolean deleteCity(Long id)
    {
        if (cityRepository.existsById(id))
        {
            cityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //seed cities for a base of cities in system
    public List<City> seedCities()
    {
        List<City> cities = List.of(
                new City("Los Angeles", "California", 3860000),
                new City("Miami", "Florida", 487000),
                new City("San Antonio", "Texas", 1570000),
                new City("New York City", "New York", 8478000),
                new City("Phoenix", "Arizona", 1673000),
                new City("Chicago", "Illinois", 2721000)
        );
        return cityRepository.saveAll(cities);
    }

    //add airport to city

    //add citizen to city?

}
