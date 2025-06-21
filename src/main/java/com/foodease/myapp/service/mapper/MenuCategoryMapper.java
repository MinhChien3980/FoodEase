package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.MenuCategory;
import com.foodease.myapp.service.dto.request.MenuCategoryRequest;
import com.foodease.myapp.service.dto.response.MenuCategoryResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MenuCategoryMapper {
    @Mapping(source="restaurantId", target="restaurant.id")
    MenuCategory toEntity(MenuCategoryRequest dto);

    @Mapping(source="restaurant.id", target="restaurantId")
    MenuCategoryResponse toDto(MenuCategory entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="id", ignore=true)
    @Mapping(source="restaurantId", target="restaurant.id")
    void updateFromDto(MenuCategoryRequest dto, @MappingTarget MenuCategory entity);

}
