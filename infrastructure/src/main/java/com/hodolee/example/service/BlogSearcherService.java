package com.hodolee.example.service;

import com.hodolee.example.searcher.BlogSearcher;
import com.hodolee.example.searcher.dto.BlogSearchDto;
import com.hodolee.example.searcher.dto.ExternalApiResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class BlogSearcherService {

    private final BlogSearcher kakaoSearcher;
    private final BlogSearcher naverSearcher;
    private final SearchHistoryService searchHistoryService;
    private final CacheManager cacheManager;

    public BlogSearcherService(@Qualifier("kakaoSearcher") BlogSearcher kakaoSearcher,
                               @Qualifier("naverSearcher") BlogSearcher naverSeacher,
                               SearchHistoryService searchHistoryService,
                               CacheManager cacheManager) {
        this.kakaoSearcher = kakaoSearcher;
        this.naverSearcher = naverSeacher;
        this.searchHistoryService = searchHistoryService;
        this.cacheManager = cacheManager;
    }

    @CircuitBreaker(name = "caller", fallbackMethod = "getNaverBlog")
    public ExternalApiResponse getKakaoBlog(String query, String sort, Integer page) {
        ExternalApiResponse cachedResponse = getCachedResponse("kakao", query, sort, page);
        if (cachedResponse != null) {
            log.info("getKakaoBlog() / query: {}", query);
            return cachedResponse;
        }

        searchHistoryService.saveSearchHistory(query);
        log.info("kakao searcher: {} / {} / {}", query, sort, page);
        ExternalApiResponse response = kakaoSearcher.searchBlog(new BlogSearchDto(query, sort, page));
        saveToCache("blogSearchCache:kakao", query, response);
        return response;
    }

    private ExternalApiResponse getNaverBlog(String query, String sort, Integer page, Throwable t) {
        log.info("circuit break / message : {}", t.getMessage());
        ExternalApiResponse cachedResponse = getCachedResponse("naver", query, sort, page);
        if (cachedResponse != null) {
            log.info("getNaverBlog() / query: {}", query);
            return cachedResponse;
        }

        searchHistoryService.saveSearchHistory(query);
        BlogSearchDto blogSearchDto = new BlogSearchDto(query, sort, page);
        blogSearchDto.convertNaverApi();
        log.info("naver searcher: {}", blogSearchDto);
        ExternalApiResponse response = naverSearcher.searchBlog(blogSearchDto);
        saveToCache("blogSearchCache:naver", query, response);
        return response;
    }

    private ExternalApiResponse getCachedResponse(String blog, String query, String sort, Integer page) {
        if ("kakao".equals(blog)) {
            ExternalApiResponse kakaoCache = cacheManager.getCache("blogSearchCache:kakao")
                    .get(query, ExternalApiResponse.class);
            if (kakaoCache != null) {
                CompletableFuture.runAsync(() -> {
                    ExternalApiResponse updatedResponse = kakaoSearcher.searchBlog(new BlogSearchDto(query, sort, page));
                    saveToCache("blogSearchCache:kakao", query, updatedResponse);
                });
                return kakaoCache;
            }
        } else {
            ExternalApiResponse naverCache = cacheManager.getCache("blogSearchCache:naver")
                    .get(query, ExternalApiResponse.class);
            if (naverCache != null) {
                CompletableFuture.runAsync(() -> {
                    ExternalApiResponse updatedResponse = kakaoSearcher.searchBlog(new BlogSearchDto(query, sort, page));
                    saveToCache("blogSearchCache:naver", query, updatedResponse);
                });
                return naverCache;
            }
        }
        return null;
    }

    private void saveToCache(String cacheName, String query, ExternalApiResponse response) {
        Objects.requireNonNull(cacheManager.getCache(cacheName)).put(query, response);
    }

}
