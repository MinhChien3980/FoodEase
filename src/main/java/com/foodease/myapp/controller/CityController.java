package com.foodease.myapp.controller;

import com.foodease.myapp.domain.City;
import com.foodease.myapp.repository.CityRepository;
import com.foodease.myapp.service.CityService;
import com.foodease.myapp.service.dto.request.CityRequest;
import com.foodease.myapp.service.dto.response.ApiResponse;
import com.foodease.myapp.service.dto.response.CityResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
@Tag(name = "City", description = "Operations related to cities")
@CrossOrigin(origins = "http://localhost:3000")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class CityController {
    CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/all")
    public ApiResponse<List<CityResponse>> getAllCities() {
        List<CityResponse> cities = cityService.getAllCities();
        return ApiResponse.<List<CityResponse>>builder()
                .code(200)
                .data(cities)
                .build();
    }

    @GetMapping("/name")
    public ApiResponse<CityResponse> getCityByName(@Valid @RequestParam String name) {
        CityResponse city = cityService.getCityByName(name);
        if (city == null) {
            return ApiResponse.<CityResponse>builder()
                    .code(404)
                    .message("City not found")
                    .build();
        }
        return ApiResponse.<CityResponse>builder()
                .code(200)
                .data(city)
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<CityResponse> createCity(@Valid @RequestBody CityRequest cityRequest) {
        CityResponse city = cityService.createCity(cityRequest.getName());
        if (city == null) {
            return ApiResponse.<CityResponse>builder()
                    .code(400)
                    .message("City already exists")
                    .build();
        }
        return ApiResponse.<CityResponse>builder()
                .code(201)
                .data(city)
                .message("City created successfully")
                .build();
    }

    @PutMapping("/update")
    public ApiResponse<CityResponse> updateCity(@RequestParam Long id ,@Valid @RequestBody CityRequest cityRequest) {
        CityResponse updatedCity = cityService.updateCity(id, cityRequest.getName());
        if (updatedCity == null) {
            return ApiResponse.<CityResponse>builder()
                    .code(404)
                    .message("City not found")
                    .build();
        }
        return ApiResponse.<CityResponse>builder()
                .code(200)
                .data(updatedCity)
                .message("City updated successfully")
                .build();
    }

    @DeleteMapping("/delete")
    public ApiResponse<Void> deleteCity(
            @RequestParam String name) {
        boolean deleted = cityService.deleteCity(name);
        if (!deleted) {
            return ApiResponse.<Void>builder()
                    .code(404)
                    .message("City not found")
                    .build();
        }
        return ApiResponse.<Void>builder()
                .code(204)
                .message("City deleted successfully")
                .build();
    }
}
