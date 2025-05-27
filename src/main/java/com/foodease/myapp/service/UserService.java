package com.foodease.myapp.service;

import com.foodease.myapp.domain.City;
import com.foodease.myapp.domain.Role;
import com.foodease.myapp.domain.User;
import com.foodease.myapp.domain.UserProfile;
import com.foodease.myapp.repository.CityRepository;
import com.foodease.myapp.repository.RoleRepository;
import com.foodease.myapp.repository.UserRepository;
import com.foodease.myapp.service.dto.request.UserCreationRequest;
import com.foodease.myapp.service.dto.response.UserResponse;
import com.foodease.myapp.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserService {

    UserMapper       mapper;
    UserRepository   userRepo;
    RoleRepository   roleRepo;
    CityRepository   cityRepo;

    @Transactional
    public UserResponse createUser(UserCreationRequest req) {
        User user = mapper.toUser(req);
        UserProfile profile = mapper.toUserProfile(req);

        City city = cityRepo.findById(req.getCityId())
                .orElseThrow(() -> new RuntimeException("City không tồn tại id=" + req.getCityId()));
        profile.setCity(city);
        profile.setUser(user);
        user.setProfile(profile);

        Role userRole = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("User role không tồn tại"));
        user.setRoles(Set.of(userRole));

        User saved = userRepo.save(user);
        return mapper.toUserResponse(saved);
    }
}
