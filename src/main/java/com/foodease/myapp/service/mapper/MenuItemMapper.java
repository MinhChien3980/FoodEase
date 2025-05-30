package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.MenuItem;
import com.foodease.myapp.service.dto.request.MenuItemRequest;
import com.foodease.myapp.service.dto.response.MenuItemResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    @Mapping(source="restaurantId", target="restaurant.id")
    @Mapping(source="categoryId",   target="category.id")
    MenuItem toEntity(MenuItemRequest dto);

    @Mapping(source="restaurant.id", target="restaurantId")
    @Mapping(source="category.id",   target="categoryId")
    MenuItemResponse toDto(MenuItem entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="id", ignore=true)
    @Mapping(source="restaurantId", target="restaurant.id")
    @Mapping(source="categoryId",   target="category.id")
    void updateFromDto(MenuItemRequest dto, @MappingTarget MenuItem entity);
}
