package com.hodolee.example.searcher.dto.kakao;

import lombok.Builder;

import java.util.List;

@Builder
public record KakaoApiResponseDto(
        List<KakaoInfo> documents,
        MetaData meta
) {}