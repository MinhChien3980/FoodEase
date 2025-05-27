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
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserService {

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

    public UserResponse getLoginIdentity(Long userId) throws BadRequestException {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found: " + userId));

        return UserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .langKey(user.getLangKey())
                .activated(user.getActivated())
                .fullName(user.getProfile().getFullName())
                .phone(user.getProfile().getPhone())
                .cityId(user.getProfile().getCity().getId())
                .latitude(user.getProfile().getLatitude())
                .longitude(user.getProfile().getLongitude())
                .imageUrl(user.getProfile().getImageUrl())
                .build();
    }

    public UserResponse updateUser(Long userId, UserCreationRequest req) throws BadRequestException {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found: " + userId));

        if (req.getEmail() != null && !req.getEmail().equals(user.getEmail()) && userRepo.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email already in use");
        }

        user.setLogin(req.getLogin());
        if (req.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        user.setLangKey(req.getLangKey());

        City city = cityRepo.findById(req.getCityId())
                .orElseThrow(() -> new BadRequestException("City not found: " + req.getCityId()));

        UserProfile profile = user.getProfile();
        profile.setFullName(req.getFullName());
        profile.setPhone(req.getPhone());
        profile.setReferralCode(req.getReferralCode());
        profile.setLatitude(req.getLatitude());
        profile.setLongitude(req.getLongitude());
        profile.setCity(city);

        User updated = userRepo.save(user);
        return UserResponse.builder()
                .id(updated.getId())
                .login(updated.getLogin())
                .email(updated.getEmail())
                .langKey(updated.getLangKey())
                .activated(updated.getActivated())
                .fullName(updated.getProfile().getFullName())
                .phone(updated.getProfile().getPhone())
                .cityId(updated.getProfile().getCity().getId())
                .latitude(updated.getProfile().getLatitude())
                .longitude(updated.getProfile().getLongitude())
                .imageUrl(updated.getProfile().getImageUrl())
                .build();
    }

    public void deleteUser(Long userId) throws BadRequestException {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found: " + userId));
        userRepo.delete(user);
    }
}
