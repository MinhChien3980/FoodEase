package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.Address;
import com.foodease.myapp.service.dto.request.AddressRequest;
import com.foodease.myapp.service.dto.response.AddressResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    AddressResponse toDto(Address entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateFromDto(AddressRequest dto, @MappingTarget Address entity);
} 