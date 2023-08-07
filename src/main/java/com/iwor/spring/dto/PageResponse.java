package com.iwor.spring.dto;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
public class PageResponse<T> {

    List<T> content;
    Metadata metadata;

    public static <T> PageResponse<T> of(Page<T> page) {
        var metadata = new Metadata(page.getNumber(), page.getTotalPages(), page.getTotalElements());
        return new PageResponse<>(page.getContent(), metadata);
    }

    public static <T> PageResponse<T> of(List<T> page, int currentPage, int totalPages, long totalElements) {
        var metadata = new Metadata(currentPage, totalPages, totalElements);
        return new PageResponse<>(page, metadata);
    }

    @Value
    public static class Metadata {
        int page;
        int size;
        long totalElements;
    }
}
