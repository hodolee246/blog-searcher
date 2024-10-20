package com.hodolee.example.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String keyword;

    private int count;

    public void incrementCount() {
        this.count = this.count + 1;
    }

    @Builder
    public SearchHistory(Long idx, String keyword, int count) {
        this.idx = idx;
        this.keyword = keyword;
        this.count = count;
    }

}
