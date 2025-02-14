package com.hodolee.example.kafka;

import com.hodolee.example.searcher.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final SearchHistoryService searchHistoryService;

    @KafkaListener(topics = "search-history", groupId = "search-history-group")
    public void consumeSearchKeyword(String keyword) {
        log.info("Kafka keyword : {}", keyword);
        searchHistoryService.saveSearchHistory(keyword);
    }

}
