package com.hodolee.example.searcher.service;

import com.hodolee.example.kafka.KafkaProducerService;
import com.hodolee.example.searcher.BlogSearcher;
import com.hodolee.example.searcher.dto.BlogSearchDto;
import com.hodolee.example.searcher.dto.ExternalApiResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class BlogSearcherService {

    private final BlogSearcher kakaoSearcher;
    private final BlogSearcher naverSearcher;
    private final SearchHistoryService searchHistoryService;
    private final CacheManager cacheManager;
    private final RedissonClient redissonClient;
    private final KafkaProducerService kafkaProducerService;

    public BlogSearcherService(@Qualifier("kakaoSearcher") BlogSearcher kakaoSearcher,
                               @Qualifier("naverSearcher") BlogSearcher naverSeacher,
                               SearchHistoryService searchHistoryService,
                               CacheManager cacheManager,
                               RedissonClient redissonClient, KafkaProducerService kafkaProducerService) {
        this.kakaoSearcher = kakaoSearcher;
        this.naverSearcher = naverSeacher;
        this.searchHistoryService = searchHistoryService;
        this.cacheManager = cacheManager;
        this.redissonClient = redissonClient;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Scheduled(fixedRate = 60000)
    public void preCache() {
        List<String> popularKeywords = searchHistoryService.getTopKeyword(10);

        for (String query : popularKeywords) {
            CompletableFuture.runAsync(() -> {
                ExternalApiResponse response = kakaoSearcher.searchBlog(new BlogSearchDto(query, "accuracy", 1));
                saveToCache("blogSearchCache:kakao", query, response);
            });
        }
    }

    @CircuitBreaker(name = "caller", fallbackMethod = "getNaverBlog")
    public ExternalApiResponse getKakaoBlog(String query, String sort, Integer page) {
        String lockKey = "blogSearchLock:" + query;
        RLock lock = redissonClient.getLock(lockKey);
        kafkaProducerService.sendSearchKeyword(query);
        try {
            if (lock.tryLock(5, 10, TimeUnit.SECONDS)) {
                ExternalApiResponse cachedResponse = getCachedResponse("kakao", query, sort, page);
                if (cachedResponse != null) {
                    return cachedResponse;
                }

                ExternalApiResponse response = kakaoSearcher.searchBlog(new BlogSearchDto(query, sort, page));
                saveToCache("blogSearchCache:kakao", query, response);
                return response;
            } else {
                // 락을 획득하지 못한 경우 (다른 요청이 이미 동일한 검색어를 요청한 경우)
                log.info("already query in cache : {}", query);
                return getCachedResponse("kakao", query, sort, page);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("redis Lock error", e);
        } finally {
            // 현재 스레드가 락을 가지고 있을경우만 해제
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    private ExternalApiResponse getNaverBlog(String query, String sort, Integer page, Throwable t) {
        log.info("circuit break message : {}", t.getMessage());
        ExternalApiResponse cachedResponse = getCachedResponse("naver", query, sort, page);
        if (cachedResponse != null) {
            log.info("getNaverBlog() query: {}", query);
            return cachedResponse;
        }

        kafkaProducerService.sendSearchKeyword(query);
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
