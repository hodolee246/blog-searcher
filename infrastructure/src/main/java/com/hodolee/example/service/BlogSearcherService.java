package com.hodolee.example.service;

import com.hodolee.example.searcher.BlogSearcher;
import com.hodolee.example.searcher.dto.ExternalApiResponse;
import com.hodolee.example.searcher.dto.kakao.KakaoApiResponseDto;
import com.hodolee.example.searcher.dto.BlogSearchDto;
import com.hodolee.example.searcher.impl.KakaoSearcher;
import com.hodolee.example.searcher.dto.naver.NaverBlogResponseDto;
import com.hodolee.example.searcher.impl.NaverSearcher;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlogSearcherService {

    private final BlogSearcher kakaoSearcher;
    private final BlogSearcher naverSearcher;
    private final SearchHistoryService searchHistoryService;

    public BlogSearcherService(@Qualifier("kakaoSearcher") BlogSearcher kakaoSearcher,
                               @Qualifier("naverSearcher") BlogSearcher naverSeacher,
                               SearchHistoryService searchHistoryService) {
        this.kakaoSearcher = kakaoSearcher;
        this.naverSearcher = naverSeacher;
        this.searchHistoryService = searchHistoryService;
    }

    @CircuitBreaker(name = "caller", fallbackMethod = "getNaverBlog")
    public ExternalApiResponse getKakaoBlog(String query, String sort, Integer page) {
        searchHistoryService.saveSearchHistory(query);
        return kakaoSearcher.searchBlog(new BlogSearchDto(query, sort, page));
    }

    private ExternalApiResponse getNaverBlog(String query, String sort, Integer page, Throwable t) {
        log.info("Fallback : {}", t.getMessage());
        searchHistoryService.saveSearchHistory(query);
        BlogSearchDto blogSearchDto = new BlogSearchDto(query, sort, page);
        blogSearchDto.convertNaverApi();
        return naverSearcher.searchBlog(blogSearchDto);
    }

}
