package com.hodolee.example.searcher.dto.naver;

public record NaverInfo(
        String title,
        String link,
        String description,
        String bloggername,
        String bloggerlink,
        String postdate
) {}