package com.hodolee.example.searcher.dto.kakao;

import lombok.Builder;

@Builder
public record KakaoInfo(
        String title,
        String contents,
        String url,
        String blogname,
        String thumbnail,
        String datetime
) {}