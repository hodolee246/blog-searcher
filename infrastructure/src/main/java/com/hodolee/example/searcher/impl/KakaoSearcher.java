package com.hodolee.example.searcher.impl;

import com.hodolee.example.searcher.BlogSearcher;
import com.hodolee.example.searcher.dto.BlogSearchDto;
import com.hodolee.example.searcher.dto.ExternalApiResponse;
import com.hodolee.example.searcher.dto.kakao.KakaoApiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class KakaoSearcher implements BlogSearcher {

    @Value("${blog.search.kakao.apiUri}")
    private String apiUri;

    @Value("${blog.search.kakao.apiKey}")
    private String apiKey;

    public ExternalApiResponse searchBlog(final BlogSearchDto blogSearchDto) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(apiUri)
                .queryParam("query", blogSearchDto.getQuery())
                .queryParam("sort", blogSearchDto.getSort())
                .queryParam("page", blogSearchDto.getPage())
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<KakaoApiResponseDto> apiResponse = restTemplate.exchange(
                    uriComponents.encode().toUri(),
                    HttpMethod.GET,
                    entity,
                    KakaoApiResponseDto.class);

            return ExternalApiResponse.builder()
                    .kakaoApiResponseDto(apiResponse.getBody())
                    .build();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("API Parse Error", e);
        }
    }
}
