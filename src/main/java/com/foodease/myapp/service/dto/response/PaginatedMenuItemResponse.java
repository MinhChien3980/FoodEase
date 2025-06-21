package com.foodease.myapp.service.dto.response;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedMenuItemResponse {
    private List<MenuItemResponse> data;
    private Pagination pagination;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pagination {
        private Integer currentPage;
        private Integer totalPages;
        private Long totalItems;
        private Integer itemsPerPage;
    }
} 