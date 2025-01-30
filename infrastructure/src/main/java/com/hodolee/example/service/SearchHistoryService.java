package com.hodolee.example.service;

import com.hodolee.example.domain.SearchHistory;
import com.hodolee.example.domain.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final RedissonClient redissonClient;

    @Transactional
    public void saveSearchHistory(String query) {
        RLock lock = redissonClient.getLock("searchHistory:" + query);
        try {
            if (lock.tryLock(10, 5, TimeUnit.SECONDS)) {
                SearchHistory searchHistory = searchHistoryRepository.findByKeyword(query)
                        .orElseGet(() -> SearchHistory.builder()
                                .keyword(query)
                                .count(0)
                                .build());

                searchHistory.incrementCount();
                searchHistoryRepository.save(searchHistory);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public List<String> getTopKeyword(int limit) {
        return searchHistoryRepository.findTopKeyword(limit);
    }

}
