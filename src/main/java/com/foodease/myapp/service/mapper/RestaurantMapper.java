package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.Restaurant;
import com.foodease.myapp.service.dto.request.RestaurantRequest;
import com.foodease.myapp.service.dto.response.RestaurantResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(source="ownerId", target="owner.id")
    Restaurant toEntity(RestaurantRequest dto);

    @Mapping(source="owner.id", target="ownerId")
    RestaurantResponse toDto(Restaurant entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="id", ignore=true)
    @Mapping(source="ownerId", target="owner.id")
    void updateFromDto(RestaurantRequest dto, @MappingTarget Restaurant entity);
}
