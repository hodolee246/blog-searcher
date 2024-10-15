package com.hodolee.example.service;

import com.hodolee.example.domain.SearchHistory;
import com.hodolee.example.domain.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    @Transactional
    public void saveSearchHistory(String query) {
        SearchHistory searchHistory = searchHistoryRepository.findByKeyword(query)
                .orElseGet(() -> SearchHistory.builder()
                        .keyword(query)
                        .count(0)
                        .build());

        searchHistory.incrementCount();
        searchHistoryRepository.save(searchHistory);
    }

}
