package com.hodolee.example.service;

import com.hodolee.example.searcher.BlogSearcher;
import com.hodolee.example.searcher.dto.ExternalApiResponseDto;
import com.hodolee.example.searcher.dto.BlogSearchDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class BlogSearcherService {

    private final BlogSearcher kakaoSearcher;
    private final BlogSearcher naverSearcher;

    public BlogSearcherService(@Qualifier("kakaoSearcher") BlogSearcher kakaoSearcher,
                               @Qualifier("naverSearcher") BlogSearcher naverSeacher) {
        this.kakaoSearcher = kakaoSearcher;
        this.naverSearcher = naverSeacher;
    }

    @CircuitBreaker(name = "caller", fallbackMethod = "getNaverBlog")
    public ExternalApiResponseDto getKakaoBlog(String query, String sort, Integer page) {
        return kakaoSearcher.searchBlog(new BlogSearchDto(query, sort, page));
    }

    private ExternalApiResponseDto getNaverBlog(String query, String sort, Integer page, Throwable t) {
        log.info("Fallback : {}", t.getMessage());
        if ("accuracy".equals(sort)) {
            sort = "sim";
        } else {
            sort = "date";
        }
        return naverSearcher.searchBlog(new BlogSearchDto(query, sort, page));
    }

}
