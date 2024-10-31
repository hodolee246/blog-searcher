package com.hodolee.example.searcher.dto.naver;

import lombok.Builder;

import java.util.List;

@Builder
public record NaverBlogResponseDto(
        String lastBuildDate,
        int total,
        int start,
        int display,
        List<NaverInfo> items
) {}