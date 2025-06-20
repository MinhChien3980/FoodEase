package com.foodease.myapp.service;

import com.foodease.myapp.domain.City;
import com.foodease.myapp.domain.Role;
import com.foodease.myapp.domain.User;
import com.foodease.myapp.domain.UserProfile;
import com.foodease.myapp.exception.AppException;
import com.foodease.myapp.exception.ErrorCode;
import com.foodease.myapp.repository.CityRepository;
import com.foodease.myapp.repository.RoleRepository;
import com.foodease.myapp.repository.UserRepository;
import com.foodease.myapp.service.dto.request.RegisterRequest;
import com.foodease.myapp.service.dto.request.UserCreationRequest;
import com.foodease.myapp.service.dto.response.UserResponse;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepo;
    RoleRepository roleRepo;
    CityRepository cityRepo;
    PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setActivated(req.isActivated());
        user.setLangKey("vi");
        user.setLogin(req.getLogin());
        user.setCreatedBy("self");
        user.setCreatedAt(java.time.LocalDateTime.now());

        Role userRole = roleRepo.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("USER role not found"));
        user.setRoles(Set.of(userRole));

        City city = cityRepo.findById(req.getCityId())
                .orElseThrow(() -> new AppException(ErrorCode.CITY_NOT_FOUND));

        UserProfile profile = new UserProfile();
        profile.setFullName(req.getFullName());
        profile.setPhone(req.getPhone());
        profile.setCity(city);
        profile.setUser(user);
        user.setProfile(profile);

        User saved = userRepo.save(user);
        return UserResponse.builder()
                .id(saved.getId())
                .email(saved.getEmail())
                .fullName(saved.getProfile().getFullName())
                .phone(saved.getProfile().getPhone())
                .cityId(saved.getProfile().getCity().getId())
                .build();
    }

    public UserResponse getLoginIdentity(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return getUserResponse(user);
    }

    public UserResponse updateUser(Long userId, UserCreationRequest req) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (req.getEmail() != null && !req.getEmail().equals(user.getEmail()) && userRepo.existsByEmail(req.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        user.setLogin(req.getLogin());
        if (req.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        user.setLangKey(req.getLangKey());

        City city = cityRepo.findById(req.getCityId())
                .orElseThrow(() -> new AppException(ErrorCode.CITY_NOT_FOUND));

        UserProfile profile = user.getProfile();
        profile.setFullName(req.getFullName());
        profile.setPhone(req.getPhone());
        profile.setReferralCode(req.getReferralCode());
        profile.setLatitude(req.getLatitude());
        profile.setLongitude(req.getLongitude());
        profile.setCity(city);

        User updated = userRepo.save(user);
        return getUserResponse(updated);
    }

    public void deleteUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepo.delete(user);
    }
    
    public UserResponse getUserByToken(String token) {
        String email = extractEmailFromToken(token);
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return getUserResponse(user);
    }

    private String extractEmailFromToken(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            JWTClaimsSet claims = jwt.getJWTClaimsSet();

            String subject = claims.getSubject();
            if (subject == null) {
                throw new AppException(ErrorCode.INVALID_REQUEST);
            }
            return subject;
        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
    }

    private UserResponse getUserResponse(User user) {
        UserProfile profile = user.getProfile();
        return UserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .langKey(user.getLangKey())
                .activated(user.getActivated())
                .fullName(profile != null ? profile.getFullName() : null)
                .phone(profile != null ? profile.getPhone() : null)
                .cityId(profile != null && profile.getCity() != null ? profile.getCity().getId() : null)
                .latitude(profile != null ? profile.getLatitude() : null)
                .longitude(profile != null ? profile.getLongitude() : null)
                .imageUrl(profile != null ? profile.getImageUrl() : null)
                .build();
    }

    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(this::getUserResponse)
                .collect(Collectors.toList());
    }


}
