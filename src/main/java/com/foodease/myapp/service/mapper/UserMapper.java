package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.Role;
import com.foodease.myapp.domain.User;
import com.foodease.myapp.domain.UserProfile;
import com.foodease.myapp.service.dto.request.UserCreationRequest;
import com.foodease.myapp.service.dto.response.UserResponse;
import com.foodease.myapp.util.StatusUtils;
import org.mapstruct.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        imports = LocalDateTime.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserMapper {

    /** DTO → new User entity **/
    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "profile",     ignore = true)
    @Mapping(target = "activated",   constant = "false")
    @Mapping(target = "langKey",     constant = "en")
    @Mapping(target = "createdBy",   ignore = true)
    @Mapping(target = "createdAt",   expression = "java(LocalDateTime.now())")
    @Mapping(target = "roles",       ignore = true)
    User toUser(UserCreationRequest request);

    /** DTO → update existing User **/
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "profile",     ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password",    source = "password")
    @Mapping(target = "activated",   ignore = true)
    @Mapping(target = "langKey",     ignore = true)
    @Mapping(target = "createdBy",   ignore = true)
    @Mapping(target = "createdAt",   ignore = true)
    @Mapping(target = "roles",       ignore = true)
    void updateUser(UserCreationRequest request, @MappingTarget User user);

    /** DTO → new UserProfile entity **/
    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "user",        ignore = true)
    // drop the createdAt mapping entirely—there is no such field
    @Mapping(target = "imageUrl",    ignore = true)
    @Mapping(target = "city",        ignore = true)
    UserProfile toUserProfile(UserCreationRequest request);

    /** User entity → flat Response DTO (pull up profile fields) **/
    @Mapping(source = "profile.fullName",     target = "fullName")
    @Mapping(source = "profile.phone",        target = "phone")
    @Mapping(source = "profile.imageUrl",     target = "imageUrl")
    @Mapping(source = "profile.referralCode", target = "referralCode")
    @Mapping(source = "profile.city.id",      target = "cityId")
    @Mapping(source = "profile.latitude",     target = "latitude")
    @Mapping(source = "profile.longitude",    target = "longitude")
    UserResponse toUserResponse(User user);

    default Set<String> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
