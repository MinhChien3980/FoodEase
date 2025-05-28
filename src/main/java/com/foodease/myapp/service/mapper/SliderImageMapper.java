package com.foodease.myapp.service.mapper;

import com.foodease.myapp.domain.SliderImage;
import com.foodease.myapp.service.dto.request.SliderImageRequest;
import com.foodease.myapp.service.dto.response.SliderImageResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SliderImageMapper {
    SliderImage toEntity(SliderImageRequest req);

    SliderImageResponse toDto(SliderImage entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(SliderImageRequest req, @MappingTarget SliderImage entity);

}
