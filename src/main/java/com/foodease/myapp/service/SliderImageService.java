package com.foodease.myapp.service;

import com.foodease.myapp.domain.SliderImage;
import com.foodease.myapp.repository.SliderImageRepository;
import com.foodease.myapp.service.dto.request.SliderImageRequest;
import com.foodease.myapp.service.dto.response.SliderImageResponse;
import com.foodease.myapp.service.mapper.SliderImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SliderImageService {
    SliderImageRepository repo;
    SliderImageMapper mapper;

    @Transactional(readOnly = true)
    public List<SliderImageResponse> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SliderImageResponse create(SliderImageRequest req) {
        SliderImage entity = mapper.toEntity(req);
        SliderImage saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional
    public SliderImageResponse update(Long id, SliderImageRequest req) {
        SliderImage existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Slider image not found: " + id));
        mapper.updateEntity(req, existing);
        SliderImage saved = repo.save(existing);
        return mapper.toDto(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Slider image not found: " + id);
        }
        repo.deleteById(id);
    }
}

