package com.foodease.myapp.service;

import com.foodease.myapp.domain.Address;
import com.foodease.myapp.domain.City;
import com.foodease.myapp.domain.User;
import com.foodease.myapp.repository.AddressRepository;
import com.foodease.myapp.repository.CityRepository;
import com.foodease.myapp.repository.UserRepository;
import com.foodease.myapp.service.dto.request.AddressRequest;
import com.foodease.myapp.service.dto.response.AddressResponse;
import com.foodease.myapp.service.mapper.AddressMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AddressService {
    AddressRepository repo;
    UserRepository userRepo;
    CityRepository cityRepo;
    AddressMapper mapper;

    @Transactional(readOnly = true)
    public List<AddressResponse> getAllAddresses() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressesByUserId(Long userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AddressResponse createAddress(AddressRequest dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + dto.getUserId()));

        City city = null;
        if (dto.getCityId() != null) {
            city = cityRepo.findById(dto.getCityId())
                    .orElseThrow(() -> new EntityNotFoundException("City not found: " + dto.getCityId()));
        }

        Address address = new Address();
        address.setUser(user);
        address.setCity(city);
        mapper.updateFromDto(dto, address);

        return mapper.toDto(repo.save(address));
    }

    @Transactional
    public AddressResponse updateAddress(Long id, AddressRequest dto) {
        Address address = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found: " + id));

        City city = null;
        if (dto.getCityId() != null) {
            city = cityRepo.findById(dto.getCityId())
                    .orElseThrow(() -> new EntityNotFoundException("City not found: " + dto.getCityId()));
        }

        address.setCity(city);
        mapper.updateFromDto(dto, address);

        return mapper.toDto(repo.save(address));
    }

    @Transactional
    public boolean deleteAddress(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
} 