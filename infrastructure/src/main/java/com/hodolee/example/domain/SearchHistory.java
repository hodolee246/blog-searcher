package com.hodolee.example.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String searchType;

    private LocalDateTime searchDate;

    @Builder
    public SearchHistory(Long idx, String searchType, LocalDateTime searchDate) {
        this.idx = idx;
        this.searchType = searchType;
        this.searchDate = searchDate;
    }

}
