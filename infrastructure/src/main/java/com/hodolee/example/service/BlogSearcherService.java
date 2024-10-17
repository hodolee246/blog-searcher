package com.hodolee.example.service;

import com.hodolee.example.searcher.KakaoBlogSearcher;
import com.hodolee.example.searcher.NaverBlogSearcher;
import com.hodolee.example.searcher.dto.BlogSearchDto;
import com.hodolee.example.searcher.dto.ExternalApiResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlogSearcherService {

    private final KakaoBlogSearcher kakaoBlogSearcher;
//    private final NaverBlogSearcher naverBlogSearcher;
    private final SearchHistoryService searchHistoryService;

//    @CircuitBreaker(name = "caller", fallbackMethod = "getNaverBlog")
    public ExternalApiResponseDto getKakaoBlog(String query, String sort, Integer page) {
        searchHistoryService.saveSearchHistory(query);
        return kakaoBlogSearcher.searchBlog(query, sort, page);
    }

//    private ExternalApiResponseDto getNaverBlog(String query, String sort, Integer page, Throwable t) {
//        log.info("Fallback : {}", t.getMessage());
//        if ("accuracy".equals(sort)) {
//            sort = "sim";
//        } else {
//            sort = "date";
//        }
//        searchHistoryService.saveSearchHistory(query);
//        return naverBlogSearcher.searchBlog(new BlogSearchDto(query, sort, page));
//    }

}
