package com.hodolee.example.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "search-history";

    public void sendSearchKeyword(String keyword) {
        kafkaTemplate.send(TOPIC, keyword);
    }

}
