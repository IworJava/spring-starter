package com.iwor.spring.mapper;

import com.iwor.spring.dto.PageResponse;

public interface Mapper<F, T> {

    T map(F object);

    default T map(F fromObject, T toObject) {
        return toObject;
    }

    default PageResponse<T> map(PageResponse<F> from) {
        return new PageResponse<>(
                from.getContent().stream()
                        .map(this::map)
                        .toList(),
                from.getMetadata()
        );
    }
}
