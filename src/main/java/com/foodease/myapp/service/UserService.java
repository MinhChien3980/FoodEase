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
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Encoder;
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
    PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(UserCreationRequest req) throws BadRequestException {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email already in use");
        }

        User user = new User();
        user.setLogin(req.getLogin());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setActivated(false);
        user.setLangKey("en");
        user.setCreatedBy("self");

        Role userRole = roleRepo.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("USER role not found"));
        user.setRoles(Set.of(userRole));

        City city = cityRepo.findById(req.getCityId())
                .orElseThrow(() -> new BadRequestException("City not found: " + req.getCityId()));

        UserProfile profile = new UserProfile();
        profile.setFullName(req.getFullName());
        profile.setPhone(req.getPhone());
        profile.setReferralCode(req.getReferralCode());
        profile.setLatitude(req.getLatitude());
        profile.setLongitude(req.getLongitude());
        profile.setCity(city);
        profile.setUser(user);
        user.setProfile(profile);

        User saved = userRepo.save(user);
        return UserResponse.builder()
                .id(saved.getId())
                .login(saved.getLogin())
                .email(saved.getEmail())
                .fullName(saved.getProfile().getFullName())
                .phone(saved.getProfile().getPhone())
                .cityId(saved.getProfile().getCity().getId())
                .build();
    }
}
