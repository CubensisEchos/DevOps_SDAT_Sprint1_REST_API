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

    public List<City> getAllCities()
    {
        return cityRepository.findAll();
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

    public boolean deleteCityById(Long id)
    {
        if (cityRepository.existsById(id))
        {
            cityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
