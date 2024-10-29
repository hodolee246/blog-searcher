package com.hodolee.example.searcher.dto.kakao;

import java.util.List;

public record KakaoApiResponseDto(
        List<KakaoInfo> documents,
        MetaData meta
) {}