package com.foodease.myapp.config;

import com.foodease.myapp.constant.PredefinedRole;
import com.foodease.myapp.domain.City;
import com.foodease.myapp.domain.Role;
import com.foodease.myapp.domain.User;
import com.foodease.myapp.repository.CityRepository;
import com.foodease.myapp.repository.RoleRepository;
import com.foodease.myapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner init(UserRepository userRepo, RoleRepository roleRepo, CityRepository cityRepo) {
        return args -> {
            String admin = "admin";
            if (userRepo.findByEmail(admin).isEmpty()) {
                // Check if USER role exists
                Role userRole = roleRepo.findByName(PredefinedRole.USER_ROLE)
                        .orElseGet(() -> roleRepo.save(new Role(null, PredefinedRole.USER_ROLE, "User role", null)));
                
                // Check if ADMIN role exists
                Role adminRole = roleRepo.findByName(PredefinedRole.ADMIN_ROLE)
                        .orElseGet(() -> roleRepo.save(new Role(null, PredefinedRole.ADMIN_ROLE, "Admin role", null)));

                // Check if city exists
                City city = cityRepo.findByName("Hà Nội")
                        .orElseGet(() -> {
                            City newCity = City.builder()
                                    .name("Hà Nội")
                                    .build();
                            return cityRepo.save(newCity);
                        });

                User u = User.builder()
                        .email(admin)
                        .login("admin")
                        .password(passwordEncoder.encode(admin))
                        .roles(Set.of(adminRole))
                        .langKey("en")
                        .activated(true)
                        .createdBy("system")
                        .build();
                userRepo.save(u);
                log.warn("Tạo tài khoản admin/admin, hãy đổi mật khẩu ngay!");
            }
            log.info("Khởi tạo application hoàn tất.");
        };
    }
}
