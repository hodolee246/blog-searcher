package com.hodolee.example.searcher.dto;

import com.hodolee.example.searcher.dto.kakao.KakaoApiResponseDto;
import com.hodolee.example.searcher.dto.naver.NaverBlogResponseDto;
import lombok.Builder;

@Builder
public record ExternalApiResponse(
        KakaoApiResponseDto kakaoApiResponseDto,
        NaverBlogResponseDto naverBlogResponseDto
) {}
