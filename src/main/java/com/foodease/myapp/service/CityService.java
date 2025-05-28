package com.foodease.myapp.service;

import com.foodease.myapp.domain.City;
import com.foodease.myapp.repository.CityRepository;
import com.foodease.myapp.service.dto.response.CityResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CityService {

    CityRepository cityRepository;

    public List<CityResponse> getAllCities() {
        List<CityResponse> cityResponses = new ArrayList<>();
        cityRepository.findAll().forEach(city -> {
            CityResponse response = CityResponse.builder()
                    .name(city.getName())
                    .build();
            cityResponses.add(response);
        });
        return cityResponses;
    }

    public CityResponse getCityByName(String name) {
        return cityRepository.findByName(name)
                .map(city -> CityResponse.builder()
                        .name(city.getName())
                        .build())
                .orElse(null);
    }

    public CityResponse createCity(String name) {
        if (cityRepository.existsByName(name)) {
            throw new IllegalStateException("City already exists: " + name);
        }

        City city = new City();
        city.setName(name);
        City saved = cityRepository.save(city);

        return CityResponse.builder()
                .name(saved.getName())
                .build();
    }

    public CityResponse updateCity(Long id, String name) {
        Object cityOptional = cityRepository.findById(id);
        if (cityRepository.existsByName(name)) {
            return null;
        }
        City city = (City) cityOptional;
        city.setName(name);
        cityRepository.save(city);
        return CityResponse.builder()
                .name(city.getName())
                .build();
    }

    public boolean deleteCity(String name) {
        var cityOptional = cityRepository.findByName(name);
        if (cityOptional.isEmpty()) {
            return false; // City not found
        }
        cityRepository.delete(cityOptional.get());
        return true; // City deleted successfully
    }

}
