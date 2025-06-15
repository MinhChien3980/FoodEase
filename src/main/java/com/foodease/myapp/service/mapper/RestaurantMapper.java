package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.Restaurant;
import com.foodease.myapp.service.dto.request.RestaurantRequest;
import com.foodease.myapp.service.dto.response.RestaurantResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    Restaurant toEntity(RestaurantRequest dto);

    RestaurantResponse toDto(Restaurant entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="id", ignore=true)
    void updateFromDto(RestaurantRequest dto, @MappingTarget Restaurant entity);
}
