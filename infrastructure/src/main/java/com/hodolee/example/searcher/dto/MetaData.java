package com.hodolee.example.searcher.dto;

public record MetaData(
        int totalCount,
        Integer pageableCount,
        Boolean isEnd
) {}
