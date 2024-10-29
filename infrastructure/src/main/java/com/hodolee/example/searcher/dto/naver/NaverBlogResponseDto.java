package com.hodolee.example.searcher.dto.naver;

import java.util.List;

public record NaverBlogResponseDto(
        String lastBuildDate,
        int total,
        int start,
        int display,
        List<NaverInfo> items
) {}