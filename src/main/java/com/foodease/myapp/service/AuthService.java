package com.foodease.myapp.service;

import com.foodease.myapp.constant.PredefinedRole;
import com.foodease.myapp.domain.Role;
import com.foodease.myapp.domain.User;
import com.foodease.myapp.repository.InvalidatedTokenRepository;
import com.foodease.myapp.repository.UserRepository;
import com.foodease.myapp.service.dto.request.AuthRequest;
import com.foodease.myapp.service.dto.request.IntrospectRequest;
import com.foodease.myapp.service.dto.response.AuthResponse;
import com.foodease.myapp.service.dto.response.IntrospectResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {

    private final UserRepository               userRepository;
    private final InvalidatedTokenRepository   invalidatedTokenRepository;
    private final PasswordEncoder              passwordEncoder;
    private final JwtEncoder                   jwtEncoder;
    private final JwtDecoder                   jwtDecoder;
    private final Duration                     accessTokenValidity;
    private final Duration                     refreshTokenValidity;

    public AuthService(
            UserRepository userRepository,
            InvalidatedTokenRepository invalidatedTokenRepository,
            PasswordEncoder passwordEncoder,
            JwtEncoder jwtEncoder,
            JwtDecoder jwtDecoder,
            @Value("${jwt.access-token-expiry}")  Duration accessTokenValidity,
            @Value("${jwt.refresh-token-expiry}") Duration refreshTokenValidity
    ) {
        this.userRepository             = userRepository;
        this.invalidatedTokenRepository = invalidatedTokenRepository;
        this.passwordEncoder            = passwordEncoder;
        this.jwtEncoder                 = jwtEncoder;
        this.jwtDecoder                 = jwtDecoder;
        this.accessTokenValidity        = accessTokenValidity;
        this.refreshTokenValidity       = refreshTokenValidity;
    }

    /** Đăng nhập, trả về access token */
    public AuthResponse authenticate(AuthRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Sai thông tin đăng nhập");
        }

        String token = generateJwt(user, accessTokenValidity);
        return AuthResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    /** Introspect: check xem token còn hợp lệ không */
    public IntrospectResponse introspect(IntrospectRequest req) {
        try {
            validateJwt(req.getToken(), accessTokenValidity);
            return new IntrospectResponse(true);
        } catch (JwtException ex) {
            return new IntrospectResponse(false);
        }
    }

    private String generateJwt(User user, Duration validity) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("foodease-api")
                .issuedAt(now)
                .expiresAt(now.plus(validity))
                .id(UUID.randomUUID().toString())
                .subject(user.getEmail())
                .claim("roles",
                        user.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.toList()))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    /** Validate token, kiểm tra expiry và blacklist */
    private Jwt validateJwt(String token, Duration maxAge) {
        Jwt jwt = jwtDecoder.decode(token);

        if (jwt.getExpiresAt().isBefore(Instant.now())) {
            throw new JwtException("Token đã hết hạn");
        }
        if (invalidatedTokenRepository.existsByToken(jwt.getId())) {
            throw new JwtException("Token đã bị thu hồi");
        }
        return jwt;
    }

//    register
    public AuthResponse register(AuthRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email đã được sử dụng");
        }

        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRoles(Set.of(
                new Role(null, PredefinedRole.USER_ROLE, "User role", null)
        ));

        userRepository.save(user);

        String token = generateJwt(user, accessTokenValidity);
        return AuthResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }
}
